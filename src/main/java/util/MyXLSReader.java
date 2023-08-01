package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MyXLSReader {
	
	public String filepath;
	FileInputStream fis=null;;
	Workbook workbook=null;;
	Sheet sheet=null;;
	Row row=null;;
	Cell cell=null;;
	public  FileOutputStream fileOut =null;
	String fileExtension=null;
		
	public MyXLSReader(String filepath) throws IOException{
		
		this.filepath = filepath;
		fileExtension = filepath.substring(filepath.indexOf(".x"));
		
	   try {
			fis = new FileInputStream(filepath);
			
			if(fileExtension.equals(".xlsx")){
				
				workbook = new XSSFWorkbook(fis);
				
				
			} else if(fileExtension.equals(".xls")){
				
				workbook = new HSSFWorkbook(fis);
				
			}
			
			sheet = workbook.getSheetAt(0);	
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			fis.close();			
		}
		
	}
	
	// returns the row count in a sheet
	public int getRowCount(String sheetname){
		
		int sheetIndex = workbook.getSheetIndex(sheetname);
		if(sheetIndex==-1){			
			return 0;
		} else {			
			sheet = workbook.getSheetAt(sheetIndex);
			int rowsCount = sheet.getLastRowNum()+1;
			return rowsCount;		
		}
		
	}	
	
	
	// returns the data from a cell
	public String getCellData(String sheetname,String colName,int rowNum){
		try{		
		if(rowNum<=0)
		 return "";
		
		int sheetIndex = workbook.getSheetIndex(sheetname);
		if(sheetIndex==-1)			
			return "";
			
	    sheet = workbook.getSheetAt(sheetIndex);
	    row = sheet.getRow(0);
	    int colNum=-1;
	    
	    for(int i=0;i<row.getLastCellNum();i++){
	    	
	    	if(row.getCell(i).getStringCellValue().equals(colName))
	    		colNum=i;
	    	
	    }
	    
	    if(colNum==-1)
	    	return "";
	    
	    sheet = workbook.getSheetAt(sheetIndex);
	    row = sheet.getRow(rowNum-1);
	    if(row==null)
	    	return "";
	    
	    cell = row.getCell(colNum);
	    if(cell==null)
	    	return "";
	    
	    if(cell.getCellType()==Cell.CELL_TYPE_STRING){
	    	return cell.getStringCellValue();	    	
	    } else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA){
	    	
	    	String cellText  = String.valueOf(cell.getNumericCellValue());
			  if (HSSFDateUtil.isCellDateFormatted(cell)) {
		           // format in form of M/D/YY
				  double d = cell.getNumericCellValue();

				  Calendar cal =Calendar.getInstance();
				  cal.setTime(HSSFDateUtil.getJavaDate(d));
		            cellText =
		             (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
		           cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" +
		                      cal.get(Calendar.MONTH)+1 + "/" + 
		                      cellText;
	    }    
				
		return cellText;	
		
	    }else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
		      return ""; 
		  else 
			  return String.valueOf(cell.getBooleanCellValue());
		}catch(Exception e){
			
			e.printStackTrace();
			return "row "+rowNum+" or column "+colName +" does not exist in xls";
			
		}
		
	}
	
	// returns the data from a cell
	public String getCellData(String sheetname,int colNum,int rowNum){
		try{
		if(rowNum <=0)
			return "";
		
		int sheetIndex = workbook.getSheetIndex(sheetname);
		
		if(sheetIndex==-1)
			return "";
		
		sheet = workbook.getSheetAt(sheetIndex);
		row = sheet.getRow(rowNum-1);
		if(row==null)
			return "";
		cell = row.getCell(colNum-1);
		if(cell==null)
			return "";
		
		if(cell.getCellType()==Cell.CELL_TYPE_STRING)
			  return cell.getStringCellValue();
		  else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA ){
			  String cellText  = String.valueOf(cell.getNumericCellValue());
			  return cellText;
		  }else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
		      return "";
		  else 
			  return String.valueOf(cell.getBooleanCellValue());
		
	       }catch(Exception e){
	    	   
	    	   e.printStackTrace();
			   return "row "+rowNum+" or column "+colNum +" does not exist  in xls";
	    	   
	       }
	}
		
	// returns true if data is set successfully else false
		public boolean setCellData(String sheetName,String colName,int rowNum, String data){
			try{
			//fis = new FileInputStream(filepath); 
			//workbook = new XSSFWorkbook(fis);

			if(rowNum<=0)
				return false;
			
			int sheetIndex = workbook.getSheetIndex(sheetName);
			int colNum=-1;
			if(sheetIndex==-1)
				return false;
			
			
			sheet = workbook.getSheetAt(sheetIndex);
			

			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				if(row.getCell(i).getStringCellValue().trim().equals(colName))
					colNum=i;
			}
			if(colNum==-1)
				return false;

			sheet.autoSizeColumn(colNum); 
			row = sheet.getRow(rowNum-1);
			if (row == null)
				row = sheet.createRow(rowNum-1);
			
			cell = row.getCell(colNum);	
			if (cell == null)
		        cell = row.createCell(colNum);

		    // cell style
		   CellStyle cs = workbook.createCellStyle();
		   cs.setWrapText(true);
		   cell.setCellStyle(cs);
		    cell.setCellValue(data);

		    fileOut = new FileOutputStream(filepath);

			workbook.write(fileOut);

		    fileOut.close();	

			}
			catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
		}	
	
		// returns true if data is set successfully else false
		public boolean setCellData(String sheetName,String colName,int rowNum, String data,String url){
			//System.out.println("setCellData setCellData******************");
			try{
			//fis = new FileInputStream(filepath); 
			//workbook = new XSSFWorkbook(fis);

			if(rowNum<=0)
				return false;
			
			int index = workbook.getSheetIndex(sheetName);
			int colNum=-1;
			if(index==-1)
				return false;
			
			
			sheet = workbook.getSheetAt(index);
			//System.out.println("A");
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				//System.out.println(row.getCell(i).getStringCellValue().trim());
				if(row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
					colNum=i;
			}
			
			if(colNum==-1)
				return false;
			sheet.autoSizeColumn(colNum); //ashish
			row = sheet.getRow(rowNum-1);
			if (row == null)
				row = sheet.createRow(rowNum-1);
			
			cell = row.getCell(colNum);	
			if (cell == null)
		        cell = row.createCell(colNum);
				
		    cell.setCellValue(data);
		    CreationHelper createHelper = workbook.getCreationHelper();

		    //cell style for hyperlinks
		    //by default hypelrinks are blue and underlined
		    CellStyle hlink_style = workbook.createCellStyle();
		    Font hlink_font = workbook.createFont();
		    hlink_font.setUnderline(Font.U_SINGLE);
		    hlink_font.setColor(IndexedColors.BLUE.getIndex());
		    hlink_style.setFont(hlink_font);
		    //hlink_style.setWrapText(true);

		    Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_FILE);
		    link.setAddress(url);
		    cell.setHyperlink(link);
		    cell.setCellStyle(hlink_style);
		      
		    fileOut = new FileOutputStream(filepath);
			workbook.write(fileOut);

		    fileOut.close();	

			}
			catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
		// returns true if sheet is created successfully else false
		public boolean addSheet(String  sheetname){		
			
			FileOutputStream fileOut;
			try {
				 workbook.createSheet(sheetname);	
				 fileOut = new FileOutputStream(filepath);
				 workbook.write(fileOut);
			     fileOut.close();		    
			} catch (Exception e) {			
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
		// returns true if sheet is removed successfully else false if sheet does not exist
		public boolean removeSheet(String sheetName){		
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return false;
			
			FileOutputStream fileOut;
			try {
				workbook.removeSheetAt(index);
				fileOut = new FileOutputStream(filepath);
				workbook.write(fileOut);
			    fileOut.close();		    
			} catch (Exception e) {			
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
		// returns true if column is created successfully
		public boolean addColumn(String sheetName,String colName){
			//System.out.println("**************addColumn*********************");
			
			try{				
				//fis = new FileInputStream(filepath); 
				//workbook = new XSSFWorkbook(fis);
				int index = workbook.getSheetIndex(sheetName);
				if(index==-1)
					return false;
				
			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			
			sheet=workbook.getSheetAt(index);
			
			row = sheet.getRow(0);
			if (row == null)
				row = sheet.createRow(0);
			
			//cell = row.getCell();	
			//if (cell == null)
			//System.out.println(row.getLastCellNum());
			if(row.getLastCellNum() == -1)
				cell = row.createCell(0);
			else
				cell = row.createCell(row.getLastCellNum());
		        
		        cell.setCellValue(colName);
		        cell.setCellStyle(style);
		        
		        fileOut = new FileOutputStream(filepath);
				workbook.write(fileOut);
			    fileOut.close();		    

			}catch(Exception e){
				e.printStackTrace();
				return false;
			}
			
			return true;			
			
		}
		
		// removes a column and all the contents
		public boolean removeColumn(String sheetName, int colNum) {
			try{
			if(!isSheetExist(sheetName))
				return false;
			//fis = new FileInputStream(filepath); 
			//workbook = new XSSFWorkbook(fis);
			sheet=workbook.getSheet(sheetName);
			CellStyle style = workbook.createCellStyle();
			style.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);
			@SuppressWarnings("unused")
			CreationHelper createHelper = workbook.getCreationHelper();
			style.setFillPattern(HSSFCellStyle.NO_FILL);		    
		
			for(int i=0;i<getRowCount(sheetName);i++){
				row=sheet.getRow(i);	
				if(row!=null){
					cell=row.getCell(colNum-1);
					if(cell!=null){
						cell.setCellStyle(style);
						row.removeCell(cell);
					}
				}
			}
			fileOut = new FileOutputStream(filepath);
			workbook.write(fileOut);
		    fileOut.close();
			}
			catch(Exception e){
				e.printStackTrace();
				return false;
			}
			return true;
			
		}
		
		
		// find whether sheets exists	
		public boolean isSheetExist(String sheetName){
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1){
				index=workbook.getSheetIndex(sheetName.toUpperCase());
					if(index==-1)
						return false;
					else
						return true;
			}
			else
				return true;
		}
		
		
		// returns number of columns in a sheet	
		public int getColumnCount(String sheetName){
			// check if sheet exists
			if(!isSheetExist(sheetName))
			 return -1;
			
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0);
			
			if(row==null)
				return -1;
			
			return row.getLastCellNum();			
			
		}
		
		
		//String sheetName, String testCaseName,String keyword ,String URL,String message
		public boolean addHyperLink(String sheetName,String screenShotColName,String testCaseName,String url,String message){
			if(!isSheetExist(sheetName))
				 return false;
			
		    sheet = workbook.getSheet(sheetName);
		    
		    for(int i=1;i<=getRowCount(sheetName);i++){
		    	if(getCellData(sheetName, 1, i).equalsIgnoreCase(testCaseName)){
		    		//System.out.println("**caught "+(i+index));
		    		setCellData(sheetName, screenShotColName,i,message,url);
		    		break;
		    	}
		    }


			return true; 
		}
		
		
		public int getCellRowNum(String sheetName,String colName,String cellValue){
			
			for(int i=1;i<=getRowCount(sheetName);i++){
		    	if(getCellData(sheetName,colName,i).equalsIgnoreCase(cellValue)){
		    		return i;
		    	}
		    }
			return -1;
			
		}
		
		
}