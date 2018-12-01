package com.gopher.system.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.gopher.system.constant.CodeAndMsg;
import com.gopher.system.constant.State;
import com.gopher.system.dao.mysql.OrderDAO;
import com.gopher.system.exception.BusinessRuntimeException;
import com.gopher.system.model.Customer;
import com.gopher.system.model.CustomerUser;
import com.gopher.system.model.Order;
import com.gopher.system.model.OrderCommodity;
import com.gopher.system.model.User;
import com.gopher.system.model.vo.request.OrderPageRequst;
import com.gopher.system.model.vo.request.OrderRequst;
import com.gopher.system.model.vo.response.CommodityResponse;
import com.gopher.system.model.vo.response.CustomerCommodityGroupResponse;
import com.gopher.system.model.vo.response.OrderDetailResponse;
import com.gopher.system.model.vo.response.OrderPageResponse;
import com.gopher.system.service.CommodityService;
import com.gopher.system.service.CustomerCommodityGroupService;
import com.gopher.system.service.CustomerPriceService;
import com.gopher.system.service.CustomerService;
import com.gopher.system.service.CustomerUserService;
import com.gopher.system.service.OrderCommodityService;
import com.gopher.system.service.OrderService;
import com.gopher.system.service.UserService;
import com.gopher.system.util.DateUtils;
import com.gopher.system.util.MathUtils;
import com.gopher.system.util.Page;

@Service
public class OrderServiceImpl implements OrderService {

	private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private UserService userService;
	@Autowired
	private OrderCommodityService orderCommodityService;
	@Autowired
	private CommodityService commodtityService;
	@Autowired
	private CustomerUserService customerUserService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerPriceService customerPriceService;

	@Transactional
	@Override
	public void insert(OrderRequst orderRequst) {
		if (null == orderRequst) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		String commodityListJson = orderRequst.getCommodityListJson();
		if (!StringUtils.hasText(commodityListJson)) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		List<OrderCommodity> list = JSON.parseArray(commodityListJson, OrderCommodity.class);
		if (list == null || list.isEmpty()) {
			throw new BusinessRuntimeException("请选择商品");
		}
		Order order = new Order();
		order.setNumber(System.currentTimeMillis() + "");
		final int userId = userService.getCurrentUserId();
		order.setCreateUser(userId);
		order.setUpdateUser(userId);
		order.setRemark(orderRequst.getRemark());
		CustomerUser cu = customerUserService.get(userId);
		if (null == cu) {
			throw new BusinessRuntimeException("根据用户ID找不到对应的客户");
		}
		/**
		 * 当前订单的客户ID
		 */
		order.setCustomerId(cu.getCustomerId());
		orderDAO.insert(order);
		LOG.info("新增订单成功：订单ID：{}", order.getId());
		// TODO 1 生成订单号,保存订单
		// 2添加此次订单的商品到order_commodity表
		for (OrderCommodity orderCommodity : list) {
			orderCommodity.setOrderId(order.getId());
			orderCommodityService.insert(orderCommodity);
		}

	}

	@Override
	public List<Order> getOrderListByCurrentUser() {
		Order order = new Order();
		CustomerUser cu = customerUserService.get(userService.getCurrentUserId());
		if (null == cu) {
			throw new BusinessRuntimeException("根据用户ID找不到对应的客户");
		}
		order.setCustomerId(cu.getCustomerId());
		return orderDAO.findList(order);
	}

	private String getUserName(int userId) {
		User user = userService.getUerById(userId);
		String userName = "";
		if (null != user) {
			userName = user.getName();
		}
		return userName;
	}

	@Autowired
	private CustomerCommodityGroupService customerCommodityGroupService;

	@Override
	public OrderDetailResponse getOrderDetail(int id) {
		if (id <= 0) {
			throw new BusinessRuntimeException("无效的ID");
		}
		Order order = this.getOrder(id);
		OrderDetailResponse result = null;
		if (null != order) {
			result = new OrderDetailResponse();
			result.setId(order.getId());
			result.setCreateTime(order.getCreateTime().getTime());
			result.setUpdateTime(order.getUpdateTime().getTime());
			result.setUpdateUser(this.getUserName(order.getUpdateUser()));
			result.setCreateUser(this.getUserName(order.getCreateUser()));
			result.setRemark(order.getRemark());
			result.setNumber(order.getNumber());
			final int customerId = order.getCustomerId();
			/**
			 * 当前客户使用的定价号
			 */
			// TODO
			result.setPriceNumber(customerPriceService.getPriceNumberByCustomerId(customerId));
			Customer customer = customerService.findById(customerId);
			if (null != customer) {
				result.setCompany(customer.getName());
				result.setPhone(customer.getMobilePhone());
			}
			result.setNumber(order.getNumber());
			List<OrderCommodity> list = orderCommodityService.findList(id);
			if (null != list) {
				List<CommodityResponse> commodityList = new ArrayList<>(list.size());
				for (OrderCommodity orderCommodity : list) {
					final int commodityId = orderCommodity.getCommodityId();
					final int sendAmount = orderCommodity.getSendAmount();
					final int realAnount = orderCommodity.getRealAmount();
					CommodityResponse response = new CommodityResponse();
					response.setId(commodityId);
					response.setCommodityId(commodityId);
					CommodityResponse rsp = commodtityService.getCommodity(commodityId);
					response.setName(rsp.getName());
					response.setCommodityTypeId(rsp.getCommodityTypeId());
					response.setCommodityTypeName(rsp.getCommodityTypeName());
					response.setPrice(orderCommodity.getPrice());
					response.setUnit(orderCommodity.getUnit());
					response.setAmount(orderCommodity.getAmount());
					response.setRealAmount(realAnount);
					response.setSendAmount(sendAmount);
					final int groupId = orderCommodity.getCustomerCommodityGroupId();
					response.setCustomerCommodityGroupId(groupId);
					CustomerCommodityGroupResponse group = customerCommodityGroupService.get(groupId);
					if (null != group) {
						response.setCustomerCommodityGroupName(group.getName());
					}
					commodityList.add(response);
				}
				result.setCommodityList(commodityList);
			}
		}
		return result;
	}

	@Override
	public void deleteOrder(int id) {
		if (id <= 0) {
			throw new BusinessRuntimeException("无效的订单ID");
		}
		Order order = this.getOrder(id);
		order.setState(State.INVALID.getState());
		order.setUpdateUser(userService.getCurrentUserId());
		orderDAO.update(order);
		// 暂时不删除订单和商品的绑定关系表
	}

	private Order getOrder(int orderId) {
		Order order = new Order();
		order.setId(orderId);
		order = orderDAO.findOne(order);
		if (null == order) {
			throw new BusinessRuntimeException("根据订单ID找不到订单信息");
		}
		return order;
	}

	@Transactional
	@Override
	public OrderDetailResponse updateOrder(OrderRequst orderRequst) {
		if (orderRequst == null) {
			throw new BusinessRuntimeException("参数不能为空");
		}
		final int orderId = orderRequst.getId();
		final String commodityListJson = orderRequst.getCommodityListJson();
		if (!StringUtils.hasText(commodityListJson)) {
			throw new BusinessRuntimeException("请选择商品");
		}
		if (orderId <= 0) {
			throw new BusinessRuntimeException("无效的订单ID");
		}
		Order order = this.getOrder(orderId);
		order.setRemark(orderRequst.getRemark());
		order.setUpdateUser(userService.getCurrentUserId());
		orderDAO.update(order);
		// 删除之前的关联
		orderCommodityService.deleteByOrderId(orderId);
		// 建立新的订单商品关联
		List<OrderCommodity> list = JSON.parseArray(commodityListJson, OrderCommodity.class);
		if (list == null || list.isEmpty()) {
			throw new BusinessRuntimeException("请选择商品");
		}
		for (OrderCommodity orderCommodity : list) {
			orderCommodity.setOrderId(order.getId());
			orderCommodityService.insert(orderCommodity);
		}
		return this.getOrderDetail(orderId);
	}

	@Override
	public Page<OrderPageResponse> getOrderPage(OrderPageRequst orderPageRequst) {
		List<Order> list = orderDAO.findPage(orderPageRequst);
		final int totalCount = orderDAO.count(orderPageRequst);
		Page<OrderPageResponse> result = null;
		List<OrderPageResponse> li = null;
		if (null != list) {
			li = new ArrayList<>();
			result = new Page<>();
			for (Order order : list) {
				OrderPageResponse rsp = new OrderPageResponse();
				BeanUtils.copyProperties(order, rsp);
				Customer customer = customerService.findById(order.getCustomerId());
				if (null != customer) {
					rsp.setCustomerName(customer.getName());
				}
				li.add(rsp);
			}
			result.setList(li);
		}
		result.setPageNumber(orderPageRequst.getPageNumber());
		result.setPageSize(orderPageRequst.getPageSize());
		result.setTotalCount(totalCount);
		return result;
	}

	@Override
	public void deleteByCustomerId(int customerId) {
		Order query = new Order();
		query.setCustomerId(customerId);
		orderDAO.delete(query);
	}

	@Override
	/**
	 * TODO 分组 和总计
	 */
	public void exportOrder(int id, HttpServletResponse response) {
		OutputStream os = null;
		try {
			OrderDetailResponse rsp = this.getOrderDetail(id);
			final String[] title = {"","","","","","","",""};
			final String[] header_1 = { "订单号", "", "公司名称","", "日期", "", "联系电话", "" };
			final String[] header_1_val = { "", "", "", "", "", "", "","" };
			final String[] merge_1 = { "0,0,0,1", "0,0,2,3", "0,0,4,5", "0,0,6,7"};
			final String[] header_2 = { "商品名称", "价格（元）", "单位", "种类", "订购数量", "发货数量", "签收数量","小计（元）"};
			@SuppressWarnings("resource")
			HSSFWorkbook wb = new HSSFWorkbook();
			Sheet sheet = wb.createSheet();
			sheet.setColumnWidth(0, 4 * 1000);
			sheet.setColumnWidth(1, 4 * 1000);
			sheet.setColumnWidth(2, 4 * 1000);
			sheet.setColumnWidth(3, 4 * 1000);
			sheet.setColumnWidth(4, 4 * 1000);
			sheet.setColumnWidth(5, 4 * 1000);
			sheet.setColumnWidth(6, 4 * 1000);
			sheet.setColumnWidth(7, 4 * 1000);
			// 表头标题样式
			HSSFFont headfont = wb.createFont();
			headfont.setFontName("宋体");
			headfont.setFontHeightInPoints((short) 14);// 字体大小
			HSSFCellStyle headstyle = wb.createCellStyle();
			headstyle.setFont(headfont);
			headstyle.setLocked(true);
			headstyle.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
			headstyle.setWrapText(true);// 自适应宽高

			headstyle.setBorderLeft(BorderStyle.THIN);
			headstyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());

			headstyle.setBorderRight(BorderStyle.THIN);
			headstyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

			headstyle.setBorderTop(BorderStyle.THIN);
			headstyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			
			int row_number = 0;
			Cell cell = null;
			// 第一行标题
			// 第一行标题合并 
			sheet.addMergedRegion(new CellRangeAddress(row_number, row_number, 0, title.length-1));
			Row row = sheet.createRow(row_number++);
			row.setHeightInPoints(20f);
			for (int i = 0; i < title.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(title[i]);
				cell.setCellStyle(headstyle);
			}
			
			//第二行标题合并
			for (int i = 0; i < merge_1.length; i++) {
				String[] temp = merge_1[i].split(",");
				Integer startrow = row_number;
				Integer overrow =  row_number;
				Integer startcol = Integer.parseInt(temp[2]);
				Integer overcol = Integer.parseInt(temp[3]);
				sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
			}
			// 第二行标题
			row = sheet.createRow(row_number++);
			row.setHeightInPoints(20f);
			for (int i = 0; i < header_1.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(header_1[i]);
				cell.setCellStyle(headstyle);
			}
			
			// 合并单元格 第三行标题
			for (int i = 0; i < merge_1.length; i++) {
				String[] temp = merge_1[i].split(",");
				Integer startrow =row_number;
				Integer overrow = row_number;
				Integer startcol = Integer.parseInt(temp[2]);
				Integer overcol = Integer.parseInt(temp[3]);
				sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
			}
			
			// 第三行标题
			row = sheet.createRow(row_number++);
			row.setHeightInPoints(20f);
			String fileName = "";
			if (null != rsp) {
				for (int i = 0; i < header_1_val.length; i++) {
					cell = row.createCell(i);
					cell.setCellStyle(headstyle);
					if (i == 0) {
						cell.setCellValue(rsp.getNumber());
					}
					if (i == 2) {
						cell.setCellValue(rsp.getCompany());
					}
					if (i == 4) {
						cell.setCellValue(DateUtils.getDateTimeString(rsp.getCreateTime()));
					}
					if (i == 6) {
						cell.setCellValue(rsp.getPhone());
					}
				}
				fileName = rsp.getCompany() + "_" + rsp.getNumber();
			} else {
				for (int i = 0; i < header_1_val.length; i++) {
					cell = row.createCell(i);
					cell.setCellValue("");
					cell.setCellStyle(headstyle);
				}
				fileName = "订单详情";
			}
			
			
			// 第四行标题
			row = sheet.createRow(row_number++);
			row.setHeightInPoints(20f);
			// 底部边框 收底
			headstyle.setBorderBottom(BorderStyle.THIN);
			headstyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			for (int i = 0; i < header_2.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(header_2[i]);
				cell.setCellStyle(headstyle);
			}
			List<CommodityResponse> list = rsp.getCommodityList();
			
			if (null != list && !list.isEmpty()) {
				final String[][] values = new String[list.size()][header_2.length];
				int a = 0;
				// {"商品名称","价格","单位","种类","订购数量","发货数量","签收数量"}
				for (CommodityResponse com : list) {
					values[a][0] = com.getName();
					values[a][1] = MathUtils.divide(com.getPrice(), 100, 2) + "";
					values[a][2] = com.getUnit();
					values[a][3] = com.getCommodityTypeName();
					values[a][4] = com.getAmount() + "";
					values[a][5] = com.getSendAmount() + "";
					values[a][6] = com.getRealAmount() + "";
					values[a][7] = MathUtils.divide(com.getPrice()*com.getRealAmount() , 100, 2) + "";
					a++;
				}
				// 创建内容
				for (int i = 0; i < values.length; i++) {
					row = sheet.createRow(row_number++);
					for (int j = 0; j < values[i].length; j++) {
						cell = row.createCell(j);
						cell.setCellValue(values[i][j]);
					}
				}
			}
			setResponseHeader(response, fileName + ".xls");
			os = response.getOutputStream();
			wb.write(os);
		} catch (Exception e) {
			LOG.info("导出订单详情失败,{}", e);
		} finally {
			if (os != null) {
				try {
					os.flush();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void setResponseHeader(HttpServletResponse response, String fileName) {
		try {
			try {
				fileName = new String(fileName.getBytes(), "ISO8859-1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			response.setContentType("application/octet-stream;charset=ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {

		final String[] header_1 = { "订单号", "", "公司名称", "日期", "", "联系电话", "" };
		final String[] header_1_val = { "", "", "", "", "", "", "" };
		final String[] merge_1 = { "0,0,0,1", "0,0,3,4", "0,0,5,6", };
		final String[] header_2 = { "商品名称", "价格", "单位", "种类", "订购数量", "发货数量", "签收数量" };
		final String[][] values = new String[1][header_2.length];
		FileOutputStream fos = new FileOutputStream("C:\\logs\\demo.xls");
		@SuppressWarnings("resource")
		HSSFWorkbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet();
		sheet.setColumnWidth(0, 12 * 1000);
		// 表头标题样式
		HSSFFont headfont = wb.createFont();
		headfont.setFontName("宋体");
		headfont.setFontHeightInPoints((short) 14);// 字体大小
		HSSFCellStyle headstyle = wb.createCellStyle();
		headstyle.setFont(headfont);
		headstyle.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
		headstyle.setWrapText(true);// 自适应宽高
		// 底部边框
		headstyle.setBorderBottom(BorderStyle.THIN);
		headstyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		headstyle.setBorderLeft(BorderStyle.THIN);
		headstyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());

		headstyle.setBorderRight(BorderStyle.THIN);
		headstyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		headstyle.setBorderTop(BorderStyle.THIN);
		headstyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		
		// 动态合并单元格
		for (int i = 0; i < merge_1.length; i++) {
			String[] temp = merge_1[i].split(",");
			Integer startrow = Integer.parseInt(temp[0]);
			Integer overrow = Integer.parseInt(temp[1]);
			Integer startcol = Integer.parseInt(temp[2]);
			Integer overcol = Integer.parseInt(temp[3]);
			sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
		}
		// 动态合并单元格
		for (int i = 0; i < merge_1.length; i++) {
			String[] temp = merge_1[i].split(",");
			Integer startrow = Integer.parseInt(temp[0]) + 1;
			Integer overrow = Integer.parseInt(temp[1]) + 1;
			Integer startcol = Integer.parseInt(temp[2]);
			Integer overcol = Integer.parseInt(temp[3]);
			sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
		}
		// 第一行标题
		Row row = sheet.createRow(0);
		row.setHeightInPoints(20f);
		Cell cell = null;
		for (int i = 0; i < header_1.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(header_1[i]);
			cell.setCellStyle(headstyle);
		}
		row = sheet.createRow(1);
		row.setHeightInPoints(20f);
		for (int i = 0; i < header_1_val.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(header_1[i] + "TODO");
			cell.setCellStyle(headstyle);
		}
		// 第二行标题
		row = sheet.createRow(2);
		row.setHeightInPoints(20f);
		for (int i = 0; i < header_2.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(header_2[i]);
			cell.setCellStyle(headstyle);
		}
		// 创建内容
		for (int i = 0; i < values.length; i++) {
			row = sheet.createRow(i + 3);
			for (int j = 0; j < values[i].length; j++) {
				cell = row.createCell(j);
				cell.setCellValue(values[i][j]);
			}
		}
		wb.write(fos);
		fos.close();
	}

}
