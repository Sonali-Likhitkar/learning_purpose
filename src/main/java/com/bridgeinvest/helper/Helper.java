package com.bridgeinvest.helper;

import com.bridgeinvest.entity.User;
import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.opencsv.CSVWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

@Component
public class Helper {

    public static String [] HEADER={
            "id",
            "username",
            "password"
    };

    public static String SHEET_NAME="users_data";

    public static ByteArrayInputStream dataToExcel(List<User>list) throws IOException {


        //create a workbook
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            //create sheet
            Sheet sheet = workbook.createSheet(SHEET_NAME);

            //create row: header row

            Row row = sheet.createRow(0);

            for (int i = 0; i < HEADER.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(HEADER[i]);
            }
            //value row
            int rowIndex = 1;
            for (User c : list) {
                Row dataRow = sheet.createRow(rowIndex);
                dataRow.createCell(0).setCellValue(c.getId());
                dataRow.createCell(1).setCellValue(c.getUsername());
                dataRow.createCell(2).setCellValue(c.getPassword());

            }
            workbook.write(out);

            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("fail to import data excel");
            return null;
        } finally {

            workbook.close();
            out.close();
        }
    }

    public void writeToCsv(List<User> users, String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        CSVWriter csvWriter = new CSVWriter(fileWriter);

        // Write the header row
        String[] header = {"id", "username", "password"};
        csvWriter.writeNext(header);

        // Write data rows
        for (User user : users) {

            String[] rowData = {Long.toString(user.getId()), user.getUsername(), String.valueOf(user.getPassword())};
            csvWriter.writeNext(rowData);
        }

        // Close writers
        csvWriter.close();
        fileWriter.close();
    }

    public static void generatePdf(List<User> users, String fileName) throws Exception {
        Document  document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));
        document.open();

        for (User user : users) {
            Paragraph paragraph = new Paragraph(user.getId().toString()+ " - " + user.getUsername()+ " - " + user.getPassword());
            document.add(paragraph);
        }
        document.close();
    }


}

