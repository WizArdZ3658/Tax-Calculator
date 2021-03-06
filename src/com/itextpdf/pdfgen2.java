package com.itextpdf;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class pdfgen2 {
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static Font small = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    private String username1;
    private String FILE;

    public pdfgen2(String uid1, String file) {
        username1 = uid1;
        FILE = file;

    }

    public void getPDF() {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
            addTitlePage(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addMetaData(Document document) {
        document.addTitle("Statistics of Users");
        document.addSubject("Tax Calculator");
        document.addKeywords("Java, PDF, iText, tax, calculator");
        document.addAuthor("Admin");
        document.addCreator("Somnath Pal");
    }

    private void addTitlePage(Document document) throws DocumentException {
        Paragraph preface = new Paragraph();
        preface.add(new Paragraph("Record Table", catFont));

        preface.add(new Paragraph(
                "Report generated by: " + "Tax Calculator" + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                small));
        addEmptyLine(preface, 2);
        preface.add(new Paragraph("You are viewing the records of all the users who have used the Tax Calculator application", smallBold));

        addEmptyLine(preface, 2);

        document.add(preface);
        PdfPTable table = new PdfPTable(4);


        PdfPCell c1 = new PdfPCell(new Phrase("User-ID"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Income"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Total Tax"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(new Phrase("Year"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb","root","admin");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select userid, income, taxtopay, assessyr from statistics");
            while (rs.next()){
                String temp = rs.getString(1);
                table.addCell(temp);
                temp = rs.getString(2);
                table.addCell(temp);
                temp = rs.getString(3);
                table.addCell(temp);
                temp = rs.getString(4);
                table.addCell(temp);

            }

            con.close();
        }
        catch(Exception e1){
            System.out.println(e1);
        }

        document.add(table);
    }



    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

}