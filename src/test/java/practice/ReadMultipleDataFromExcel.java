package practice;

import java.io.IOException;
import org.apache.poi.EncryptedDocumentException;

import genericutility.ExcelFileUtility;

public class ReadMultipleDataFromExcel {

    public static void main(String[] args) throws EncryptedDocumentException, IOException {

        ExcelFileUtility eLib = new ExcelFileUtility();
        int rowCount = eLib.getRowCount("practice");
        System.out.println("Total Rows: " + rowCount);

        for (int row = 1; row <= rowCount; row++) {
            String data = eLib.readDataFromExcelFile("practice", row, 0);
            System.out.println(data);
        }
    }
}
