package com.taskmanagerplus.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for Excel-related operations in the Task Manager Plus application.
 * 
 * <p>This class provides various utility methods to interact with and manipulate 
 * Excel files. It is intended to centralize common Excel operations that can be 
 * reused across different test classes.</p>
 * 
 * <p>Example usage:</p>
 * <pre>
 * {@code
 * // Example future method usage
 * ExcelUtils.readExcelFile(filePath);
 * }
 * </pre>
 * 
 * <p><b>Note:</b> This class should be extended with static methods to perform 
 * common Excel actions, such as reading data, writing data, and updating cells.</p>
 * 
 * Author: Maicon Fang
 * Date: 2024-07-09
 * Version: 1.0
 */
public class ExcelUtils {

    private Workbook workbook;
    private String filePath;

    public ExcelUtils(String filePath) {
        this.filePath = filePath;
        try (InputStream file = getClass().getClassLoader().getResourceAsStream(filePath)) {
            if (file == null) {
                throw new IllegalArgumentException("File not found: " + filePath);
            }
            workbook = new XSSFWorkbook(file);
            System.out.println("Workbook has been loaded successfully from: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printFilePath() {
        URL resource = getClass().getClassLoader().getResource(filePath);
        if (resource != null) {
            System.out.println("Absolute path of the file: " + resource.getPath());
        } else {
            System.out.println("File not found: " + filePath);
        }
    }

    public String getCellData(String sheetName, int rowNum, int colNum) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet not found: " + sheetName);
        }
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            throw new IllegalArgumentException("Row not found: " + rowNum);
        }
        Cell cell = row.getCell(colNum);
        if (cell == null) {
            throw new IllegalArgumentException("Cell not found: " + colNum);
        }

        return cell.toString();
    }

    public Map<String, String> getRowData(String sheetName, int rowNum) {
        Map<String, String> rowData = new HashMap<>();
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet not found: " + sheetName);
        }
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            throw new IllegalArgumentException("Row not found: " + rowNum);
        }

        for (Cell cell : row) {
            rowData.put(cell.getSheet().getRow(0).getCell(cell.getColumnIndex()).toString(), cell.toString());
        }

        return rowData;
    }

    public String getCellDataByColumnName(String sheetName, int rowNum, String colName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            throw new IllegalArgumentException("Sheet not found: " + sheetName);
        }
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            throw new IllegalArgumentException("Row not found: " + rowNum);
        }
        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            throw new IllegalArgumentException("Header row not found: " + 0);
        }
        
        int colNum = -1;
        for (Cell cell : headerRow) {
            if (cell.toString().equalsIgnoreCase(colName)) {
                colNum = cell.getColumnIndex();
                break;
            }
        }

        if (colNum == -1) {
            throw new IllegalArgumentException("Column " + colName + " not found");
        }
        
        Cell cell = row.getCell(colNum);
        if (cell == null) {
            throw new IllegalArgumentException("Cell not found at row " + rowNum + " and column " + colNum);
        }
        
        return formatCellValue(cell);
    }

    private String formatCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        return String.format("%d", (long) numericValue);
                    } else {
                        return String.valueOf(numericValue);
                    }
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return cell.toString();
        }
    }
}
