package poi;

import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class PoiUtil {

	public static void generateExcel(String[] rows, List<List<String>> colsList, OutputStream os){
		int rownum = 0;
		Sheet sheet = null;
		Row row = null;
		Cell cell = null;
		Workbook wb = new HSSFWorkbook();
		
		try{
			Font font = wb.createFont();
			font.setColor(HSSFColor.WHITE.index);

			CellStyle tableheadStyle = wb.createCellStyle();
			tableheadStyle.setAlignment(CellStyle.ALIGN_CENTER);
			tableheadStyle.setFillForegroundColor(HSSFColor.ROYAL_BLUE.index);
			tableheadStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			tableheadStyle.setFont(font);
			
			CellStyle normalStyle = wb.createCellStyle();
			normalStyle.setWrapText(true);
			
			for(int i = 0; i < colsList.size(); i++){
				
				if(rownum == 0){
					sheet = wb.createSheet();
					row = sheet.createRow(0);
					for(int k = 0; k < rows.length; k++){
						String rowName = rows[k];
						cell = row.createCell(k);
						cell.setCellValue(rowName);
						cell.setCellStyle(tableheadStyle);
						sheet.autoSizeColumn(k);
					}
					rownum++;
				}
				
				row = sheet.createRow(rownum);
				List<String> cols = colsList.get(i);
				for(int j = 0; j < cols.size(); j++){
					cell = row.createCell(j);
					cell.setCellValue(cols.get(j));
					cell.setCellStyle(normalStyle);
					sheet.autoSizeColumn(i);
				}
				rownum++;
				
				if(rownum == 65536){
					rownum = 0;
				}
			}
			
			wb.write(os);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
