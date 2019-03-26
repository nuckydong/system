package com.gopher.system.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.gopher.system.model.Commodity;
import com.gopher.system.model.Customer;
import com.gopher.system.model.CustomerUser;
import com.gopher.system.model.Order;
import com.gopher.system.model.OrderCommodity;
import com.gopher.system.model.User;
import com.gopher.system.model.vo.request.OrderPageRequst;
import com.gopher.system.model.vo.request.OrderRequst;
import com.gopher.system.model.vo.request.OrderStatisticRequest;
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
		final int customerId = cu.getCustomerId();
		/**
		 * 当前订单的客户ID
		 */
		order.setCustomerId(customerId);
		orderDAO.insert(order);
		LOG.info("新增订单成功：订单ID：{}", order.getId());
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
			result.setChange(order.getChange());
			result.setSend(order.getSend());
			final int customerId = order.getCustomerId();
			/**
			 * 当前客户使用的定价号
			 */
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
					final int amount = orderCommodity.getAmount();
					final int change = orderCommodity.getChange();
					
					CommodityResponse response = new CommodityResponse();
					response.setId(commodityId);
					response.setCommodityId(commodityId);
					response.setAmount(amount);
					response.setChange(change);
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
		if (null == order) {
			throw new BusinessRuntimeException("根据订单ID找不到对应的订单");
		}
		// 这个订单之前的商品
		List<OrderCommodity> oldList = orderCommodityService.findList(orderId);
		// 建立新的订单商品关联
		List<OrderCommodity> newList = JSON.parseArray(commodityListJson, OrderCommodity.class);
		if (newList == null || newList.isEmpty()) {
			throw new BusinessRuntimeException("请选择商品");
		}
		if (order.getSend() == 1) {// 如果是已经打单的，判断修改的新的列表和旧的商品 有没有不同的地方
			if (oldList == null || oldList.isEmpty()) {
				for (OrderCommodity orderCommodity : newList) {
					orderCommodity.setChange(1);
				}
			} else {
				for (OrderCommodity newOne : newList) {
					newOne.setChange(1);
					for (OrderCommodity oldOne : oldList) {
						if (newOne.getCommodityId() == oldOne.getCommodityId()
								&& newOne.getAmount() == oldOne.getAmount()) {
							newOne.setChange(0);
							break;
						}
					}
				}
			}
		}
		// 删除之前的关联
		orderCommodityService.deleteByOrderId(orderId);
		boolean orderChanged = false;
		for (OrderCommodity newOne : newList) {
			if (newOne.getChange() != 0 && !orderChanged) {
				orderChanged = true;
			}
			newOne.setOrderId(order.getId());
			orderCommodityService.insert(newOne);
		}
		order.setRemark(orderRequst.getRemark());
		order.setUpdateUser(userService.getCurrentUserId());
		if (orderChanged) {
			order.setChange(1);
		}
		orderDAO.update(order);
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
		final int common_column_width = 4 * 1000;
		final float header_row_height_in_point = 20f;
		OutputStream os = null;
		try {
			OrderDetailResponse rsp = this.getOrderDetail(id);
			final String[] title = { "", "", "", "", "", "", "", "" };
			final String[] header_1 = { "订单号", "", "公司名称", "", "日期", "", "联系电话", "" };
			final String[] header_1_val = { "", "", "", "", "", "", "", "" };
			final String[] merge_1 = { "0,0,0,1", "0,0,2,3", "0,0,4,5", "0,0,6,7" };
			final String[] header_2 = { "序号", "商品名称", "价格（元）", "发货数量", "签收数量", "单位", "小计（元）","订购数量" };
			final int col_len = header_2.length;
			@SuppressWarnings("resource")
			HSSFWorkbook wb = new HSSFWorkbook();
			Sheet sheet = wb.createSheet();
			for (int i = 0; i < col_len; i++) {
				if (i == 0) {
					sheet.setColumnWidth(i, 2 * 1000);
				} else {
					sheet.setColumnWidth(i, common_column_width);
				}
			}
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

			headstyle.setBorderBottom(BorderStyle.THIN);
			headstyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
			// ================================================================================
			HSSFFont bodyfont = wb.createFont();
			bodyfont.setFontName("宋体");
			bodyfont.setFontHeightInPoints((short) 10);// 字体大小
			HSSFCellStyle bodystyle = wb.createCellStyle();
			bodystyle.setFont(bodyfont);
			bodystyle.setLocked(true);
			bodystyle.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
			bodystyle.setWrapText(true);// 自适应宽高

			bodystyle.setBorderLeft(BorderStyle.THIN);
			bodystyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());

			bodystyle.setBorderRight(BorderStyle.THIN);
			bodystyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

			bodystyle.setBorderTop(BorderStyle.THIN);
			bodystyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
			bodystyle.setBorderBottom(BorderStyle.THIN);
			bodystyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

			int row_number = 0;
			Cell cell = null;
			// 第一行标题
			// 第一行标题合并
			sheet.addMergedRegion(new CellRangeAddress(row_number, row_number, 0, title.length - 1));
			Row row = sheet.createRow(row_number++);
			row.setHeightInPoints(header_row_height_in_point);
			for (int i = 0; i < title.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(title[i]);
				cell.setCellStyle(headstyle);
			}

			// 第二行标题合并
			for (int i = 0; i < merge_1.length; i++) {
				String[] temp = merge_1[i].split(",");
				Integer startrow = row_number;
				Integer overrow = row_number;
				Integer startcol = Integer.parseInt(temp[2]);
				Integer overcol = Integer.parseInt(temp[3]);
				sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
			}
			// 第二行标题
			row = sheet.createRow(row_number++);
			row.setHeightInPoints(header_row_height_in_point);
			for (int i = 0; i < header_1.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(header_1[i]);
				cell.setCellStyle(headstyle);
			}

			// 合并单元格 第三行标题
			for (int i = 0; i < merge_1.length; i++) {
				String[] temp = merge_1[i].split(",");
				Integer startrow = row_number;
				Integer overrow = row_number;
				Integer startcol = Integer.parseInt(temp[2]);
				Integer overcol = Integer.parseInt(temp[3]);
				sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
			}

			// 第三行标题
			row = sheet.createRow(row_number++);
			row.setHeightInPoints(header_row_height_in_point);
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
				fileName = rsp.getCompany() + "_" +DateUtils.getDateString(rsp.getCreateTime());
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
			row.setHeightInPoints(header_row_height_in_point);
			for (int i = 0; i < header_2.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(header_2[i]);
				cell.setCellStyle(headstyle);
			}
			List<CommodityResponse> list = rsp.getCommodityList();

			Map<String, List<CommodityResponse>> mapList = new HashMap<>();

			if (null != list && !list.isEmpty()) {
				for (CommodityResponse commodityResponse : list) {
					final String groupName = commodityResponse.getCustomerCommodityGroupName();
					List<CommodityResponse> li = mapList.get(groupName);
					if (li == null) {
						li = new ArrayList<>();
					}
					li.add(commodityResponse);
					mapList.put(groupName, li);
				}
			}

			Set<String> keys = mapList.keySet();
			if (null != keys && !keys.isEmpty()) {
				for (String groupName : keys) {
					List<CommodityResponse> li = mapList.get(groupName);
					sheet.addMergedRegion(new CellRangeAddress(row_number, row_number, 0, col_len - 1));
					row = sheet.createRow(row_number++);
					for (int j = 0; j < col_len; j++) {
						cell = row.createCell(j);
						cell.setCellValue(groupName);
						cell.setCellStyle(bodystyle);
					}
					if (null != li && !li.isEmpty()) {
						final String[][] values = new String[li.size()][header_2.length];
						int a = 0;
						// { "序号", "商品名称", "价格（元）", "发货数量", "签收数量", "单位", "小计（元）","订购数量" };
						// { "序号","商品名称", "价格（元）", "订购数量", "发货数量", "签收数量", "单位", "小计（元）" };
						for (CommodityResponse com : li) {
							values[a][0] = (a + 1) + "";
							values[a][1] = com.getName();
							values[a][2] = MathUtils.divide(com.getPrice(), 100, 2) + "";
							values[a][3] = com.getSendAmount() + "";
							values[a][4] =  "";
							values[a][5] = com.getUnit();
							values[a][6] = MathUtils.divide(com.getPrice() * com.getRealAmount(), 100, 2) + "";
							values[a][7] = com.getAmount() + "";
							a++;
						}
						// 创建内容
						for (int i = 0; i < values.length; i++) {
							row = sheet.createRow(row_number++);
							for (int j = 0; j < values[i].length; j++) {
								cell = row.createCell(j);
								cell.setCellValue(values[i][j]);
								cell.setCellStyle(bodystyle);
							}
						}
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

	@Override
	public void sending(int id) {
		Order order = this.getOrder(id);
		if (null == order) {
			throw new BusinessRuntimeException("根据订单ID找不到对应的订单");
		}
		order.setSend(1);
		order.setUpdateUser(userService.getCurrentUserId());
		orderDAO.update(order);
	}

	@Override
	public void exportOrder(OrderStatisticRequest orderStatisticRequest, HttpServletResponse response) {
		final String[] header = { "商品名称", "数量", "单位" };
		final int common_column_width = 4 * 1000;
		final float header_row_height_in_point = 20f;
		@SuppressWarnings("resource")
		HSSFWorkbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet();
		for (int i = 0; i < header.length; i++) {
			if (i == 1) {
				sheet.setColumnWidth(i, 8 * 1000);
			} else {
				sheet.setColumnWidth(i, common_column_width);
			}
		}
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

		headstyle.setBorderBottom(BorderStyle.THIN);
		headstyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		// ================================================================================
		HSSFFont bodyfont = wb.createFont();
		bodyfont.setFontName("宋体");
		bodyfont.setFontHeightInPoints((short) 10);// 字体大小
		HSSFCellStyle bodystyle = wb.createCellStyle();
		bodystyle.setFont(bodyfont);
		bodystyle.setLocked(true);
		bodystyle.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
		bodystyle.setWrapText(true);// 自适应宽高

		bodystyle.setBorderLeft(BorderStyle.THIN);
		bodystyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());

		bodystyle.setBorderRight(BorderStyle.THIN);
		bodystyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		bodystyle.setBorderTop(BorderStyle.THIN);
		bodystyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		bodystyle.setBorderBottom(BorderStyle.THIN);
		bodystyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

		int row_number = 0;
		Cell cell = null;
		// 第一行标题
		Row row = sheet.createRow(row_number++);
		row.setHeightInPoints(header_row_height_in_point);
		for (int i = 0; i < header.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(header[i]);
			cell.setCellStyle(headstyle);
		}
		List<Order> list = orderDAO.findList4Statistic(orderStatisticRequest);
		LOG.info("订单列表：{}",JSON.toJSONString(list));
		List<OrderCommodity> all = new ArrayList<>();
		if (null != list) {
			for (Order order : list) {
				List<OrderCommodity> commodityList = orderCommodityService.findList(order.getId());
				all.addAll(commodityList);
			}
		}
		LOG.info("商品列表：{}",JSON.toJSONString(list));
		// 商品对应的数量 总和
		Map<Integer, Integer> commodityNum = new HashMap<>();
		// 商品对应的数量 详细
		Map<Integer, String> commodityNumDetail = new HashMap<>();
		if (!all.isEmpty()) {
			for (OrderCommodity orderCommodity : all) {
				final Integer commodityId = orderCommodity.getCommodityId();
				final Integer amount = orderCommodity.getAmount();
				Integer num = commodityNum.get(commodityId);
				String detail = commodityNumDetail.get(commodityId);
				if (num != null) {
					num += amount;
				} else {
					num = amount;
				}
				if (StringUtils.hasText(detail)) {
					detail += ("+" + amount);
				} else {
					detail = amount + "";
				}
				commodityNum.put(commodityId, num);
				commodityNumDetail.put(commodityId, detail);
			}
		}
		Set<Integer> keys = commodityNumDetail.keySet();
		if (!keys.isEmpty()) {
			for (Integer key : keys) {
				Commodity commodity = commodtityService.get(key);
				if(commodity == null) {
					continue;
				}
				final String name = commodity.getName();
				final String unit = commodity.getUnit();
				final Integer total = commodityNum.get(key);
				final String str = commodityNumDetail.get(key);
				row = sheet.createRow(row_number++);
				row.setHeightInPoints(header_row_height_in_point);
				for (int i = 0; i < header.length; i++) {
					cell = row.createCell(i);
					if (i == 0) {
						cell.setCellValue(name);
					}
					if (i == 1) {
						if(str.contains("+")) {
							cell.setCellValue(str + "=" + total);
						}else {
							cell.setCellValue(total);
						}
						
					}
					if (i == 2) {
						cell.setCellValue(unit);
					}
					cell.setCellStyle(bodystyle);
				}
			}
		}
		setResponseHeader(response, "订单统计" + ".xls");
		OutputStream os = null;
		try {
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

}
