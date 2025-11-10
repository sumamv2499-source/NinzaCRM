package genericutility;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtility {
	public String readDataFromExcelFile(String sheetName, int rowNum, int cellNum)
			throws EncryptedDocumentException, IOException {

		FileInputStream fis = new FileInputStream("src/test/resources/Crm/Ninza.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		String value = wb.getSheet(sheetName).getRow(rowNum).getCell(cellNum).getStringCellValue();

		wb.close();
		return value;
	}

        public int getRowCount(String sheetName) throws EncryptedDocumentException, IOException {
		
		FileInputStream fis = new FileInputStream("src/test/resources/Crm/Ninza.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		int rowCount = wb.getSheet(sheetName).getLastRowNum();
		
		wb.close();
		return rowCount;
	}
}
