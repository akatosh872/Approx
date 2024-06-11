package org.inter.interpolation;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelReader {

    public Map<String, List<Integer>> readExcel(File file) {
        Map<String, List<Integer>> data = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
            if (row.getRowNum() == 0) { // Пропустить заголовок
                    continue;
                }

                Cell monthCell = row.getCell(0);
                Cell itemCell = row.getCell(1);
                Cell quantityCell = row.getCell(2);

                String month = monthCell.getStringCellValue();
                String item = itemCell.getStringCellValue();
                int quantity = (int) quantityCell.getNumericCellValue();

                if (!data.containsKey(item)) {
                    data.put(item, new ArrayList<>());
                }
                data.get(item).add(quantity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
