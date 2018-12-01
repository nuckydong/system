package com.gopher.system.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExcelUtils {
    
	public static HSSFWorkbook getHSSFWorkbookCommons(String sheetName, String[] title, String[][] values, HSSFWorkbook wb) {
		if (wb == null) {
			wb = new HSSFWorkbook();
		}
		HSSFSheet sheet = wb.createSheet(sheetName);
//		sheet.setDefaultColumnWidth(4*1000);
		// 表头标题样式
		HSSFFont headfont = wb.createFont();
		headfont.setFontName("宋体");
		headfont.setFontHeightInPoints((short) 12);// 字体大小
		HSSFCellStyle headstyle = wb.createCellStyle();
		headstyle.setFont(headfont);
		headstyle.setLocked(true);
		headstyle.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
		//底部边框
		headstyle.setBorderBottom(BorderStyle.THIN);
		headstyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		
		headstyle.setBorderLeft(BorderStyle.THIN);
		headstyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		
		headstyle.setBorderRight(BorderStyle.THIN);
		headstyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		
		headstyle.setBorderTop(BorderStyle.THIN);
		headstyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		// 创建标题
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(headstyle);
		}
		// 创建内容
		for (int i = 0; i < values.length; i++) {
			row = sheet.createRow(i + 1);
			for (int j = 0; j < values[i].length; j++) {
				cell = row.createCell(j);
				cell.setCellValue(values[i][j]);
			}
		}

		return wb;
	}
	
	public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, String[][] values) {
		 HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName);
		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
		style.setWrapText(true);
		HSSFCell cell = null;
		// 创建标题
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}
		// 创建内容
		for (int i = 0; i < values.length; i++) {
			row = sheet.createRow(i + 1);
			for (int j = 0; j < values[i].length; j++) {
				cell = row.createCell(j);
				cell.setCellValue(values[i][j]);
				cell.setCellStyle(style);
			}
		}

		return wb;
	}

	public static HSSFWorkbook getHSSFWorkbook(String sheetName, String[] title, List<? extends Object> values) throws IllegalArgumentException, IllegalAccessException {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName);
		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
		HSSFCell cell = null;
		// 创建标题
		for (int i = 0; i < title.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(title[i]);
			cell.setCellStyle(style);
		}
		// 创建内容
		Field[] fields = null;
		int i = 0;
		for (Object obj : values) {
			fields = obj.getClass().getDeclaredFields();
			row = sheet.createRow(i + 1);
			int j = 0;
			for (Field v : fields) {
				v.setAccessible(true);
				Object va = v.get(obj);
				if (va == null) {
					va = "";
				}
				row.createCell(j).setCellValue(va.toString());
				j++;
			}
			i++;
		}

		return wb;
	}

	public static void export(HttpServletResponse response, String sheetName, String[] title,
			List<? extends Object> values, String fileName) {
		OutputStream os = null;
		try {
			setResponseHeader(response, fileName);
			os = response.getOutputStream();
			ExcelUtils.getHSSFWorkbook(sheetName, title, values).write(os);
		} catch (Throwable t) {
		} finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	

	public static void export(HttpServletResponse response, String sheetName, String[] title, String[][] values,
			String fileName) {
		OutputStream os = null;
		try {
			setResponseHeader(response, fileName);
			os = response.getOutputStream();
			ExcelUtils.getHSSFWorkbookCommons(sheetName, title, values, null).write(os);
		} catch (Throwable t) {
			
		} finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) throws IOException {

		final String[] header_1 = { "时间", "电压", "", "", "电流", "", "", "功率因素", "", "", "", "有功功率", "", "", "", "无功功率",
				"", "", "", "有功总电能", "", "", "无功总电能", "", "", "频率" };
		final String[] header_2 = { "", "A相", "B相", "C相", "A相", "B相", "C相", "A相", "B相", "C相", "总", "A相", "B相", "C相",
				"总", "A相", "B相", "C相", "总", "正向", "反向", "组合", "正向", "反向", "组合", "" };
		final String[] merge = { "0,1,0,0", "0,0,1,3", "0,0,4,6", "0,0,7,10", "0,0,11,14", "0,0,15,18", "0,0,19,21",
				"0,0,22,24", "0,1,25,25" };
		final String[][] values = new String[1][header_2.length];
		for (int i = 0; i < header_2.length; i++) {
			if(i == 0){
				values[0][i] = i + "TOO_LONGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG";
			}else{
				values[0][i] = i + "";
			}
			
		}
		FileOutputStream fos = new FileOutputStream("C:\\logs\\demo.xls");
		@SuppressWarnings("resource")
		HSSFWorkbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet();
		sheet.setColumnWidth(0, 6*1000);
		// 表头标题样式
		HSSFFont headfont = wb.createFont();
		headfont.setFontName("宋体");
		headfont.setFontHeightInPoints((short) 14);// 字体大小
		HSSFCellStyle headstyle = wb.createCellStyle();
		headstyle.setFont(headfont);
		headstyle.setLocked(true);
		headstyle.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
		headstyle.setWrapText(true);//自适应宽高
		//底部边框
		headstyle.setBorderBottom(BorderStyle.THIN);
		headstyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		
		headstyle.setBorderLeft(BorderStyle.THIN);
		headstyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		
		headstyle.setBorderRight(BorderStyle.THIN);
		headstyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		
		headstyle.setBorderTop(BorderStyle.THIN);
		headstyle.setTopBorderColor(IndexedColors.BLACK.getIndex());

		
		// 动态合并单元格
		for (int i = 0; i < merge.length; i++) {
			String[] temp = merge[i].split(",");
			Integer startrow = Integer.parseInt(temp[0]);
			Integer overrow = Integer.parseInt(temp[1]);
			Integer startcol = Integer.parseInt(temp[2]);
			Integer overcol = Integer.parseInt(temp[3]);
			sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
		}
		// 第一行标题
		Row row = sheet.createRow(0);
		Cell cell = null;
		for (int i = 0; i < header_1.length; i++) {
			cell = row.createCell(i);
			if(i== 0){
			}
			cell.setCellValue(header_1[i]);
			cell.setCellStyle(headstyle);
		}
		// 第二行标题
		row = sheet.createRow(1);
		for (int i = 0; i < header_2.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(header_2[i]);
			cell.setCellStyle(headstyle);
		}
		// 创建内容
		for (int i = 0; i < values.length; i++) {
			row = sheet.createRow(i + 2);
			for (int j = 0; j < values[i].length; j++) {
				cell = row.createCell(j);
				cell.setCellValue(values[i][j]);
			}
		}
		wb.write(fos);
		fos.close();
	}

	public static void exportMergeXls(HttpServletResponse response, String fileName, String sheetName,
			String[][] values, String[] head1, String[] merge, String[] head2, HSSFWorkbook workbook) {
		if (null == workbook) {
			workbook = new HSSFWorkbook();
		}
		HSSFSheet sheet = workbook.createSheet();
		sheet.setColumnWidth(0, 6*1000);//设置第一列的宽度;(时间)
		// 表头标题样式
		HSSFFont headfont = workbook.createFont();
		headfont.setFontName("宋体");
		headfont.setFontHeightInPoints((short) 14);// 字体大小
		HSSFCellStyle headstyle = workbook.createCellStyle();
		headstyle.setFont(headfont);
		headstyle.setLocked(true);
		headstyle.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
		//底部边框
		headstyle.setBorderBottom(BorderStyle.THIN);
		headstyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		
		headstyle.setBorderLeft(BorderStyle.THIN);
		headstyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		
		headstyle.setBorderRight(BorderStyle.THIN);
		headstyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		
		headstyle.setBorderTop(BorderStyle.THIN);
		headstyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		// 内容样式
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);// 字体大小
		HSSFCellStyle contentStyle = workbook.createCellStyle();
		contentStyle.setFont(font);
		contentStyle.setLocked(true);
		// 动态合并单元格
		for (int i = 0; i < merge.length; i++) {
			String[] temp = merge[i].split(",");
			Integer startrow = Integer.parseInt(temp[0]);
			Integer overrow = Integer.parseInt(temp[1]);
			Integer startcol = Integer.parseInt(temp[2]);
			Integer overcol = Integer.parseInt(temp[3]);
			sheet.addMergedRegion(new CellRangeAddress(startrow, overrow, startcol, overcol));
		}
		// 第一行标题
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = null;
		for (int i = 0; i < head1.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(head1[i]);
			cell.setCellStyle(headstyle);
		}
		// 第二行标题
		row = sheet.createRow(1);
		for (int i = 0; i < head2.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(head2[i]);
			cell.setCellStyle(headstyle);
		}
		// 创建内容
		for (int i = 0; i < values.length; i++) {
			row = sheet.createRow(i + 2);
			for (int j = 0; j < values[i].length; j++) {
				cell = row.createCell(j);
				cell.setCellValue(values[i][j]);
				cell.setCellStyle(contentStyle);
			}
		}
		OutputStream os = null;
		try {
			setResponseHeader(response, fileName);
			os = response.getOutputStream();
			workbook.write(os);
		} catch (Throwable t) {

		} finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	public static void export(SXSSFWorkbook wb, HttpServletResponse response, String fileName) {
		OutputStream os = null;
		try {
			setResponseHeader(response, fileName);
			os = response.getOutputStream();
			wb.write(os);
		} catch (Throwable t) {
			
		} finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
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

	/**
	 * 大数据处理的导出
	 * 
	 * @param sheetNames
	 * @param title_v
	 * @param title_r_t
	 * @param values
	 * @param fileName
	 * @param wb
	 * @return
	 */
	public static SXSSFWorkbook getSXSSFWorkbook(String[] sheetNames, String[] title_v, String[] title_r_t,
			List<List<Map<Integer, String>>> values, String fileName, SXSSFWorkbook wb) {
		// 第一步，创建一个webbook，对应一个Excel文件
		if (wb == null) {
			wb = new SXSSFWorkbook(1000);
		}
		int titleLen = 0;
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		if (sheetNames != null && sheetNames.length > 0) {
			for (int a = 0; a < sheetNames.length; a++) {
				SXSSFSheet sheet = wb.createSheet(sheetNames[a]);
				// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
				SXSSFRow row = sheet.createRow(0);
				// 第四步，创建单元格，并设置值表头 设置表头居中
				CellStyle style = wb.createCellStyle();
				style.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
				SXSSFCell cell = null;
				if (a == 0) {
					titleLen = title_v.length;
					// 创建标题
					for (int i = 0; i < titleLen; i++) {
						cell = row.createCell(i);
						cell.setCellValue(title_v[i]);
						cell.setCellStyle(style);
					}
				} else {
					titleLen = title_r_t.length;
					for (int i = 0; i < titleLen; i++) {
						cell = row.createCell(i);
						cell.setCellValue(title_r_t[i]);
						cell.setCellStyle(style);
					}
				}

				List<Map<Integer, String>> value = values.get(a);
				if (null != value && !value.isEmpty()) {
					int b = 1;
					for (Map<Integer, String> map : value) {
						row = sheet.createRow(b);
						for (int i = 0; i < titleLen; i++) {
							row.createCell(i).setCellValue(map.get(i));
						}
						b++;
					}
				}
			}
		}
		return wb;
	}

	public static HSSFWorkbook getHSSFWorkbook(String[] sheetNames, String[] title_v, String[] title_r_t,
			List<List<Map<Integer, String>>> values, String fileName, HSSFWorkbook wb)
			throws IllegalArgumentException, IllegalAccessException {
		// 第一步，创建一个webbook，对应一个Excel文件
		if (wb == null) {
			wb = new HSSFWorkbook();
		}
		int titleLen = 0;
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
		if (sheetNames != null && sheetNames.length > 0) {
			for (int a = 0; a < sheetNames.length; a++) {
				HSSFSheet sheet = wb.createSheet(sheetNames[a]);
				// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
				HSSFRow row = sheet.createRow(0);
				// 第四步，创建单元格，并设置值表头 设置表头居中
				HSSFCellStyle style = wb.createCellStyle();
				style.setAlignment(HorizontalAlignment.CENTER);// 创建一个居中格式
				HSSFCell cell = null;
				if (a == 0) {
					titleLen = title_v.length;
					// 创建标题
					for (int i = 0; i < titleLen; i++) {
						cell = row.createCell(i);
						cell.setCellValue(title_v[i]);
						cell.setCellStyle(style);
					}
				} else {
					// 创建标题
					titleLen = title_r_t.length;
					for (int i = 0; i < titleLen; i++) {
						cell = row.createCell(i);
						cell.setCellValue(title_r_t[i]);
						cell.setCellStyle(style);
					}
				}

				List<Map<Integer, String>> value = values.get(a);
				if (null != value && !value.isEmpty()) {
					int b = 1;
					for (Map<Integer, String> map : value) {
						row = sheet.createRow(b);
						for (int i = 0; i < titleLen; i++) {
							row.createCell(i).setCellValue(map.get(i));
						}
						b++;
					}
				}
			}
		}

		return wb;
	}

	public static void export(HttpServletResponse response, String[] sheetNames, String[] title_r, String[] title_vt,
			List<List<Map<Integer, String>>> values, String fileName) {
		OutputStream os = null;
		try {
			setResponseHeader(response, fileName);
			os = response.getOutputStream();
			ExcelUtils.getHSSFWorkbook(sheetNames, title_r, title_vt, values, fileName, null).write(os);
		} catch (Throwable t) {
		} finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static void export_big_data(HttpServletResponse response, String[] sheetNames, String[] title_r,
			String[] title_vt, List<List<Map<Integer, String>>> values, String fileName) {
		OutputStream os = null;
		try {
			setResponseHeader(response, fileName);
			os = response.getOutputStream();
			ExcelUtils.getSXSSFWorkbook(sheetNames, title_r, title_vt, values, fileName, null).write(os);
		} catch (Throwable t) {
		} finally {
			try {
				if (os != null) {
					os.flush();
					os.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
