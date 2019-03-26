package com.gopher.system.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
import com.gopher.system.dao.mysql.PriceGroupDAO;
import com.gopher.system.exception.BusinessRuntimeException;
import com.gopher.system.model.Commodity;
import com.gopher.system.model.CommodityPrice;
import com.gopher.system.model.CommodityType;
import com.gopher.system.model.PriceGroup;
import com.gopher.system.model.vo.request.PriceCommodityPageRequest;
import com.gopher.system.model.vo.request.PriceGroupPageRequst;
import com.gopher.system.model.vo.request.PriceGroupRequest;
import com.gopher.system.model.vo.response.CommodityPriceResponse;
import com.gopher.system.model.vo.response.CommodityResponse;
import com.gopher.system.model.vo.response.PriceGroupResponse;
import com.gopher.system.service.CommodityPriceService;
import com.gopher.system.service.CommodityService;
import com.gopher.system.service.CommodityTypeService;
import com.gopher.system.service.PriceGroupService;
import com.gopher.system.service.UserService;
import com.gopher.system.util.MathUtils;
import com.gopher.system.util.Page;

@Service
public class PriceGroupServiceImpl implements PriceGroupService {
	@Autowired
	private PriceGroupDAO priceGroupDAO;
	@Autowired
	private CommodityPriceService commodityPriceService;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private UserService userService;
	@Autowired
	private CommodityTypeService commodityTypeService;

	private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

	/**
	 * 添加定价表
	 */
	@Override
	@Transactional
	public int add(PriceGroupRequest priceGroupRequest) {
		if (null == priceGroupRequest) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		final String name = priceGroupRequest.getName();
		final String remark = priceGroupRequest.getRemark();
		final String commodityListJson = priceGroupRequest.getCommodityPriceListJson();

		if (!StringUtils.hasText(name)) {
			throw new BusinessRuntimeException("名称不能为空");
		}
		PriceGroup query = new PriceGroup();
		query.setName(name);
		PriceGroup pgDB = priceGroupDAO.findOne(query);
		if (pgDB != null) {
			throw new BusinessRuntimeException("重复的定价名");
		}
		PriceGroup priceGroup = new PriceGroup();
		priceGroup.setName(name);
		priceGroup.setNumber("D" + System.currentTimeMillis());
		priceGroup.setRemark(remark);
		priceGroupDAO.insert(priceGroup);
		final int userId = userService.getCurrentUserId();
		priceGroup.setCreateUser(userId);
		priceGroup.setUpdateUser(userId);
		final int priceGroupId = priceGroup.getId();
		if(StringUtils.hasText(commodityListJson)) {
			List<CommodityPrice> list = JSON.parseArray(commodityListJson, CommodityPrice.class);
			if(null != list) {
				for (CommodityPrice commodityPrice : list) {
					commodityPrice.setPriceGroupId(priceGroupId);
					commodityPriceService.add(commodityPrice);
				}
			}
		}
		return priceGroupId;
	}

	@Override
	public void update(PriceGroupRequest priceGroupRequest) {
		if (null == priceGroupRequest) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		final int id = priceGroupRequest.getId();
		final String name = priceGroupRequest.getName();
		final String remark = priceGroupRequest.getRemark();
		if (id <= 0) {
			throw new BusinessRuntimeException("ID不能为空");
		}
		if (!StringUtils.hasText(name)) {
			throw new BusinessRuntimeException("名称不能为空");
		}
		PriceGroup priceGroup = new PriceGroup();
		priceGroup.setId(id);
		priceGroup = priceGroupDAO.findOne(priceGroup);
		if (null == priceGroup) {
			throw new BusinessRuntimeException("根据ID找不到对应的记录");
		}
		if (!Objects.equals(name, priceGroup.getName())) {
			// 如果编辑的时候 名称发生改变， 检查修改后的名称是否已经存在于数据库中
			PriceGroup query = new PriceGroup();
			query.setName(name);
			query = priceGroupDAO.findOne(query);
			if (null != query) {
				throw new BusinessRuntimeException("重复的定价名");
			}
		}
		priceGroup.setName(name);
		priceGroup.setRemark(remark);
		final int userId = userService.getCurrentUserId();
		priceGroup.setUpdateUser(userId);
		priceGroupDAO.update(priceGroup);

//		if(StringUtils.hasText(commodityListJson)) {
//			List<CommodityPrice> listDB = commodityPriceService.getList(id);
//			if(listDB != null) {
//				for (CommodityPrice commodityPrice : listDB) {
//					commodityPriceService.delete(commodityPrice);
//				}
//			}
//			List<CommodityPrice> list = JSON.parseArray(commodityListJson, CommodityPrice.class);
//			if(null != list) {
//				for (CommodityPrice commodityPrice : list) {
//					commodityPrice.setPriceGroupId(id);
//					commodityPriceService.add(commodityPrice);
//				}
//			}
//		}
	}

	@Override
	public PriceGroupResponse get(int id) {
		if (id <= 0) {
			throw new BusinessRuntimeException("ID不能为空");
		}
		List<CommodityPrice> listDB = commodityPriceService.getList(id);
		PriceGroupResponse result = null;
		PriceGroup priceGroup = new PriceGroup();
		priceGroup.setId(id);
		priceGroup = priceGroupDAO.findOne(priceGroup);
		
		if (null == priceGroup) {
			return null;
		}
		List<CommodityPriceResponse> commodityPriceList = null;
		if (null != listDB) {
			commodityPriceList = new ArrayList<>();
			for (CommodityPrice commodityPrice : listDB) {
				CommodityResponse commodity = commodityService.getCommodity(commodityPrice.getCommodityId());
				if (null != commodity) {
					CommodityPriceResponse rsp = new CommodityPriceResponse();
					rsp.setPrice(commodityPrice.getPrice());
					rsp.setPriceGroupId(id);
					rsp.setName(commodity.getName());
					rsp.setCommodityId(commodity.getId());
					rsp.setUnit(commodity.getUnit());
					rsp.setCommodityTypeId(commodity.getCommodityTypeId());
					rsp.setCommodityTypeName(commodity.getCommodityTypeName());
					commodityPriceList.add(rsp);
				}
			}
		}
		result = new PriceGroupResponse();
		result.setId(id);
		result.setName(priceGroup.getName());
		result.setNumber(priceGroup.getNumber());
		result.setRemark(priceGroup.getRemark());
		result.setCommodityPriceList(commodityPriceList);
		return result;
	}

	@Override
	public void delete(int id) {
		if (id <= 0) {
			throw new BusinessRuntimeException("ID不能为空");
		}
		// 逻辑删除
		PriceGroup priceGroup = new PriceGroup();
		priceGroup.setId(id);
		priceGroup = priceGroupDAO.findOne(priceGroup);
		if (priceGroup == null) {
			throw new BusinessRuntimeException("商品定价列表不能为空");
		}
		priceGroup.setState(State.INVALID.getState());
		priceGroupDAO.update(priceGroup);
		List<CommodityPrice> listDB = commodityPriceService.getList(id);
		if (listDB != null) {
			for (CommodityPrice commodityPrice : listDB) {
				// 清理以前的
				commodityPriceService.delete(commodityPrice);
			}
		}
	}

	@Override
	public Page<PriceGroup> getPage(PriceGroupPageRequst priceGroupPageRequst) {
		if (null == priceGroupPageRequst) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		final int pageNumber = priceGroupPageRequst.getPageNumber();
		final int pageSize = priceGroupPageRequst.getPageSize();
		priceGroupPageRequst.setSkip((pageNumber - 1) * pageSize);
		final int totalCount = priceGroupDAO.count(priceGroupPageRequst);
		List<PriceGroup> list = priceGroupDAO.findList(priceGroupPageRequst);
		Page<PriceGroup> result = new Page<PriceGroup>();
		result.setList(list);
		result.setTotalCount(totalCount);
		result.setPageNumber(pageNumber);
		result.setPageSize(pageSize);
		return result;
	}

	@Override
	public List<PriceGroup> getList(PriceGroupPageRequst priceGroupPageRequst) {
		if (null == priceGroupPageRequst) {
			throw new BusinessRuntimeException(CodeAndMsg.PARAM_NOT_NULL);
		}
		final int pageNumber = 1;
		final int pageSize = Integer.MAX_VALUE;
		priceGroupPageRequst.setSkip((pageNumber - 1) * pageSize);
		List<PriceGroup> list = priceGroupDAO.findList(priceGroupPageRequst);
		return list;
	}

	@Override
	public PriceGroup getPriceGroup(int id) {
		PriceGroup priceGroup = new PriceGroup();
		priceGroup.setId(id);
		return priceGroupDAO.findOne(priceGroup);
	}

	@Override
	public Page<CommodityResponse> getCommodityPage(PriceCommodityPageRequest priceCommodityPageRequest) {
		final int groupId = priceCommodityPageRequest.getId();
		PriceGroup query = new PriceGroup();
		query.setId(groupId);
		PriceGroup priceGroup = priceGroupDAO.findOne(query);
		if (null == priceGroup) {
			throw new BusinessRuntimeException("无效的定价表ID");
		}
		final int pageSize = priceCommodityPageRequest.getPageSize();
		final int pageNumber = priceCommodityPageRequest.getPageNumber();

		Page<CommodityResponse> page = new Page<CommodityResponse>();
		List<Commodity> commodityList = priceGroupDAO.getPage(priceCommodityPageRequest);
		final int totalCount = priceGroupDAO.getCount(priceCommodityPageRequest);
		if (null != commodityList) {
			List<CommodityResponse> list = new ArrayList<>();
			for (Commodity commodity : commodityList) {
				CommodityResponse cr = new CommodityResponse();
				BeanUtils.copyProperties(commodity, cr);
				CommodityType commodityType = commodityTypeService.getCommodityTypeById(commodity.getCommodityTypeId());
				if (commodityType != null) {
					cr.setCommodityTypeName(commodityType.getName());
				}
				list.add(cr);
			}
			page.setList(list);
		}
		page.setTotalCount(totalCount);
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		return page;
	}
	@Override
	public void export(HttpServletResponse response, int id) {
		PriceGroup query = new PriceGroup();
		query.setId(id);
		PriceGroup priceGroup = priceGroupDAO.findOne(query);
		if (null == priceGroup) {
			throw new BusinessRuntimeException("无效的定价表ID");
		}
		PriceCommodityPageRequest priceCommodityPageRequest = new PriceCommodityPageRequest();
		priceCommodityPageRequest.setId(id);
		priceCommodityPageRequest.setPageSize(Integer.MAX_VALUE);
		Page<CommodityResponse> page = this.getCommodityPage(priceCommodityPageRequest);
		final int common_column_width = 4 * 1000;
		final float header_row_height_in_point = 20f;
		OutputStream os = null;
		if (null != page) {
			Map<String, List<CommodityResponse>> map = new HashMap<>();
			List<CommodityResponse> all = page.getList();
			if (null != all) {
				for (CommodityResponse commodityResponse : all) {
					final String type = commodityResponse.getCommodityTypeName();
					List<CommodityResponse> sub = map.get(type);
					if (null == sub) {
						sub = new ArrayList<>();
					}
					sub.add(commodityResponse);
					map.put(type, sub);
				}
			}
			final String[] title = { "", "", "", "", "" };
			final String[] header = { "序号", "名称", "价格（元）", "单位", "分类" };
			@SuppressWarnings("resource")
			HSSFWorkbook wb = new HSSFWorkbook();
			Sheet sheet = wb.createSheet();
			for (int i = 0; i < header.length; i++) {
				sheet.setColumnWidth(i, common_column_width);
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
				cell.setCellValue(priceGroup.getName());
				cell.setCellStyle(headstyle);
			}
			row = sheet.createRow(row_number++);
			row.setHeightInPoints(header_row_height_in_point);
			for (int i = 0; i < header.length; i++) {
				cell = row.createCell(i);
				cell.setCellValue(header[i]);
				cell.setCellStyle(headstyle);
			}
			Set<String> keys = map.keySet();
			if (!keys.isEmpty()) {
				for (String key : keys) {
					sheet.addMergedRegion(new CellRangeAddress(row_number, row_number, 0, header.length - 1));
					row = sheet.createRow(row_number++);
					for (int i = 0; i < header.length; i++) {
						cell = row.createCell(i);
						if (i == 0) {
							cell.setCellValue(key);
						}
						cell.setCellStyle(bodystyle);
					}
					List<CommodityResponse> list = map.get(key);
					if (null != list) {
						// { "序号", "名称", "价格", "单位", "分类" };
						int j = 1;
						for (CommodityResponse comm : list) {
							row = sheet.createRow(row_number++);
							for (int i = 0; i < header.length; i++) {
								cell = row.createCell(i);
								if (i == 0) {
									cell.setCellValue(j++);
								}
								if (i == 1) {
									cell.setCellValue(comm.getName());
								}
								if (i == 2) {
									cell.setCellValue(MathUtils.divide(comm.getPrice(), 100, 2));
								}
								if (i == 3) {
									cell.setCellValue(comm.getUnit());
								}
								if (i == 4) {
									cell.setCellValue(comm.getCommodityTypeName());
								}
								cell.setCellStyle(bodystyle);
							}
						}
					}
				}
				setResponseHeader(response, priceGroup.getName() + ".xls");
				try {
					os = response.getOutputStream();
					wb.write(os);
				} catch (Exception ex) {
					LOG.info("导出定价表失败，{}", ex);
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
}
