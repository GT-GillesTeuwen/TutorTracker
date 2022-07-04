/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PDF;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import tutorprogram.DBManager;
import tutorprogram.Payment;
import tutorprogram.Session;

/**
 *
 * @author Client
 */
public class PDFMaker {

    public static void main(String[] args) throws Exception {
        print(2);
        // print2();
    }

    public static void print(int std) throws SQLException, FileNotFoundException {

        String name = DBManager.getStudent(std);
        ObservableList<Session> info = DBManager.populateSessions(std);

        Font bold = new Font(FontFamily.COURIER, 8, Font.BOLD);
        Font italic = new Font(FontFamily.COURIER, 9, Font.BOLDITALIC);
        Font small = new Font(FontFamily.COURIER, 9);
        Font total = new Font(FontFamily.COURIER, 8, Font.BOLDITALIC | Font.UNDERLINE);

        try {
            File file = new File(".\\Invoices\\");
            file.mkdir();
            Document doc = new Document();
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(".\\Invoices\\" + name + ".pdf"));

            PdfPTable footer = new PdfPTable(1);
            footer.setTotalWidth(515);
            float[] footWidth = new float[]{515f};
            footer.setWidths(footWidth);

            Paragraph foot = new Paragraph("Bank details: FNB account: 62882540993 (Mr G N A Teuwen)\n" +
"Branch: Cresta 254905. **Please quote reference:"+name+" "+std+"**\n" +
"Please send proof of payment to gillesteuwen@gmail.com", small);

            PdfPCell cell = new PdfPCell(foot);

            // Image img = Image.getInstance("icons/PDFLogo.png");
            // PdfPCell iconCell = new PdfPCell(img);
            //// iconCell.setUseVariableBorders(true);
            //iconCell.setBorderColor(BaseColor.BLACK);
            // iconCell.setBorderColorRight(BaseColor.WHITE);
            //footer.addCell(iconCell);
            footer.addCell(cell);

            FooterTable event = new FooterTable(footer);
            writer.setPageEvent(event);

            doc.open();
            Chunk glue = new Chunk(new VerticalPositionMark());
            Paragraph topDeatails = new Paragraph("Gilles Teuwen", small);
            topDeatails.add(new Chunk(glue));
            topDeatails.add("14 Verona Drive" + "\n");

            topDeatails.add("");
            topDeatails.add(new Chunk(glue));
            topDeatails.add("Northcliff ext 25, 1709" + "\n");

            topDeatails.add("");
            topDeatails.add(new Chunk(glue));
            topDeatails.add("WhatsApp: 082 518 4662" + "\n");

            topDeatails.add("");
            topDeatails.add(new Chunk(glue));
            topDeatails.add("Email: gillesteuwen@gmail.com" + "\n");

            doc.add(topDeatails);

            doc.add(Chunk.NEWLINE);        //Blank line
            doc.add(new LineSeparator());      //Thick line
            //doc.add(Chunk.NEWLINE);        //Blank line
            doc.add(new Paragraph("Invoice", italic));
            doc.add(Chunk.NEWLINE);
            doc.add(new LineSeparator());      //Thick line

            Paragraph student = new Paragraph("", small);
            student.add("\nStudent: " + name);
            student.add(glue);
            student.add("Reference: " + name + ", " + std + "\n");

            doc.add(student);

            doc.add(Chunk.NEWLINE);
            doc.add(new LineSeparator());      //Thick line
            doc.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(3);
            table.setTotalWidth(520);
            float[] widths = new float[]{70f, 370f, 80f};
            table.setWidths(widths);
            table.setLockedWidth(true);

            PdfPCell cella = new PdfPCell();
            cella.addElement(new Paragraph("Date", bold));
            cella.setBorderColor(BaseColor.WHITE);
            table.addCell(cella);

            PdfPCell cellc = new PdfPCell();
            cellc.addElement(new Paragraph("Service", bold));
            cellc.setBorderColor(BaseColor.WHITE);
            table.addCell(cellc);

            PdfPCell amountHeadCell = new PdfPCell();
            Paragraph amountHead = new Paragraph("Amount", bold);
            amountHead.setAlignment(Element.ALIGN_RIGHT);
            amountHeadCell.addElement(amountHead);
            amountHeadCell.setBorderColor(BaseColor.WHITE);
            table.addCell(amountHeadCell);

            int ttlrows = 0;
            double ttlOwed = 0;
            double ttlPayed = 0;

            PdfPCell blank = new PdfPCell();
            blank.addElement(new Paragraph(" "));
            blank.setBorderColor(BaseColor.WHITE);

            for (int aw = 0; aw < info.size(); aw++) {
                PdfPCell cell4 = new PdfPCell();
                PdfPCell cell3 = new PdfPCell();
                // PdfPCell cell2 = new PdfPCell();
                PdfPCell cell1 = new PdfPCell();

                Paragraph p1 = new Paragraph((info.get(aw).getDate() + ""), small);
                // Paragraph p2 = new Paragraph((info.get(aw).getCode() + ""), small);
                Paragraph p3 = new Paragraph(("Tutor Session, " + info.get(aw).getContent().replace("\n", ", ") + ", " + info.get(aw).getTimeTaken() + " hours"), small);
                Paragraph p4 = new Paragraph(((info.get(aw).getTimeTaken() * DBManager.getRATE()) + ""), small);
                ttlOwed += (info.get(aw).getTimeTaken() * DBManager.getRATE());
                p1.setAlignment(Element.ALIGN_LEFT);
                // p2.setAlignment(Element.ALIGN_LEFT);
                p3.setAlignment(Element.ALIGN_LEFT);
                p4.setAlignment(Element.ALIGN_RIGHT);

                cell1.setBorderColor(BaseColor.WHITE);
                //cell2.setBorderColor(BaseColor.WHITE);
                cell3.setBorderColor(BaseColor.WHITE);
                cell4.setBorderColor(BaseColor.WHITE);

                cell1.addElement(p1);
                //cell2.addElement(p2);
                cell3.addElement(p3);
                cell4.addElement(p4);

                table.addCell(cell1);
                //table.addCell(cell2);
                table.addCell(cell3);
                table.addCell(cell4);
                if (ttlrows ==28) {
                   

                        for (int i = 0; i < 12; i++) {
                            table.addCell(blank);
                        }
                    
                    
                }else{
                    if (((ttlrows-28) % (37) == 0) && ttlrows != 0) {

                        for (int i = 0; i < 12; i++) {
                            table.addCell(blank);
                        }
                    }

            }
            ttlrows++;
        }
        ObservableList<Payment> payments = DBManager.populatePaymentsForStudent(std);
        for (int aw = 0; aw < payments.size(); aw++) {
            PdfPCell cell4 = new PdfPCell();
            PdfPCell cell3 = new PdfPCell();
            //PdfPCell cell2 = new PdfPCell();
            PdfPCell cell1 = new PdfPCell();

            Paragraph p1 = new Paragraph((payments.get(aw).getDate() + ""), small);
            //Paragraph p2 = new Paragraph(("Payment"), small);
            Paragraph p3 = new Paragraph(("Payment made"), small);
            Paragraph p4 = new Paragraph("- " + (payments.get(aw).getAmount() + ""), small);
            ttlPayed += payments.get(aw).getAmount();
            p1.setAlignment(Element.ALIGN_LEFT);
            //p2.setAlignment(Element.ALIGN_LEFT);
            p3.setAlignment(Element.ALIGN_LEFT);
            p4.setAlignment(Element.ALIGN_RIGHT);

            cell1.setBorderColor(BaseColor.WHITE);
            //cell2.setBorderColor(BaseColor.WHITE);
            cell3.setBorderColor(BaseColor.WHITE);
            cell4.setBorderColor(BaseColor.WHITE);

            cell1.addElement(p1);
            //cell2.addElement(p2);
            cell3.addElement(p3);
            cell4.addElement(p4);

            table.addCell(cell1);
            //table.addCell(cell2);
            table.addCell(cell3);
            table.addCell(cell4);
            if (ttlrows ==28) {
                   

                        for (int i = 0; i < 12; i++) {
                            table.addCell(blank);
                        }
                    
                    
                }else{
                    if (((ttlrows-28) % (37) == 0) && ttlrows != 0) {

                        for (int i = 0; i < 12; i++) {
                            table.addCell(blank);
                        }
                    }

            }
            ttlrows++;
        }

        PdfPCell cell4 = new PdfPCell();
        PdfPCell cell3 = new PdfPCell();
        // PdfPCell cell2 = new PdfPCell();
        PdfPCell cell1 = new PdfPCell();

        Paragraph p1 = new Paragraph(("\n"), small);
        // Paragraph p2 = new Paragraph(("\n"), small);
        Paragraph p3 = new Paragraph(("\n% VAT Included"), small);
        Paragraph p4 = new Paragraph(("\nTotal R" + (Math.round((ttlOwed - ttlPayed) * 100.0)) / 100.0 + ""), small);

        p1.setAlignment(Element.ALIGN_LEFT);
        // p2.setAlignment(Element.ALIGN_LEFT);
        p3.setAlignment(Element.ALIGN_RIGHT);
        p4.setAlignment(Element.ALIGN_RIGHT);

        cell1.setUseVariableBorders(true);
        cell1.setBorderColor(BaseColor.WHITE);
        //  cell1.setBorderColorTop(BaseColor.BLACK);

        // cell2.setUseVariableBorders(true);
        // cell2.setBorderColor(BaseColor.WHITE);
        // cell2.setBorderColorTop(BaseColor.BLACK);
        cell3.setUseVariableBorders(true);
        cell3.setBorderColor(BaseColor.WHITE);
        //cell3.setBorderColorTop(BaseColor.BLACK);

        cell4.setUseVariableBorders(true);
        cell4.setBorderColor(BaseColor.WHITE);

        // cell4.setBorderColorTop(BaseColor.BLACK);
        cell1.addElement(p1);
        //cell2.addElement(p2);
        cell3.addElement(p3);
        cell4.addElement(p4);

        table.addCell(cell1);
        //table.addCell(cell2);
        table.addCell(cell3);
        table.addCell(cell4);
        doc.add(table);

        doc.close();
    }
    catch (Exception e

    
        ) {
            System.out.println("Crap");
    }
}

public static void print2() throws FileNotFoundException, DocumentException {
        File file = new File(".\\Invoices\\");
        file.mkdir();
        Document doc = new Document();
        Document document = new Document(new Rectangle(792, 612));
        // step 2
        PdfWriter.getInstance(document, new FileOutputStream(".\\Invoices\\a.pdf"));
        // step 3
        document.open();
        // step 4
        document.add(new Paragraph("Hello World"));
        // step 5
        document.close();
    }

}
