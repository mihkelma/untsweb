package util;

import model.Invoice;
import model.InvoiceRow;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PdfUtil {


    public static void createPDF (Invoice invoice) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(PDRectangle.A4);
        document.addPage(page);

        PDTrueTypeFont fontBold = PDTrueTypeFont.loadTTF(document,
                new File(PdfUtil.class.getResource("lato-bold.ttf").getFile()));
        PDTrueTypeFont fontRegular = PDTrueTypeFont.loadTTF(document,
                new File (PdfUtil.class.getResource("lato-light.ttf").getFile()));


        String fileName = "arve_"+ Util.formatDateNoDots(invoice.getDateCreated()) +"_"+invoice.getId();
        Integer fontSizeHeader = 12;
        Integer fontSizeNorm = 11;
        Integer fontSizeSmall = 10;
        String string;
        Float lastX = 70f;
        Float lastY = 750f;

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        contentStream.setFont(fontBold, 22);
        contentStream.beginText();
        contentStream.newLineAtOffset(lastX,lastY);
        contentStream.showText(invoice.getOwnerName());
        contentStream.endText();
        lastX += 270;
        lastY -= 70;

        // header part
        contentStream.beginText();
        contentStream.setFont(fontBold, fontSizeNorm);
        contentStream.newLineAtOffset(lastX,lastY);
        contentStream.showText("Arve nr:  ");
        contentStream.setFont(fontRegular, fontSizeNorm);
        contentStream.showText( invoice.getId().toString());
        contentStream.endText();
        lastY -= 15;

        contentStream.beginText();
        contentStream.setFont(fontBold, fontSizeNorm);
        contentStream.newLineAtOffset(lastX,lastY);
        contentStream.showText("Väljastatud:  ");
        contentStream.setFont(fontRegular, fontSizeNorm);
        contentStream.showText(Util.formatDateDots(invoice.getDateCreated()));
        contentStream.endText();
        lastY -= 15;

        contentStream.beginText();
        contentStream.setFont(fontBold, fontSizeNorm);
        contentStream.newLineAtOffset(lastX,lastY);
        contentStream.showText("Maksetähtaeg:  ");
        contentStream.setFont(fontRegular, fontSizeNorm);
        contentStream.showText(Util.formatDateDots(invoice.getDateDue()));
        contentStream.endText();
        lastY -= 15;

        contentStream.beginText();
        contentStream.setFont(fontBold, fontSizeNorm);
        contentStream.newLineAtOffset(lastX,lastY);
        contentStream.showText("Viitenumber:  ");
        contentStream.setFont(fontRegular, fontSizeNorm);
        //
        contentStream.showText("-");
        contentStream.endText();
        lastX -= 270;
        lastY += 45;

        //customer part - name
        printTextTo(fontBold, fontSizeNorm, "Arve saaja:  ", lastX, lastY, contentStream);
        lastX += 60;

        string = invoice.getCustomerName();
        if(string.length() > 40) {
            Pattern p = Pattern.compile("[^,]+");
            Matcher m = p.matcher(string);
            while (m.find()) {
                printTextTo(fontRegular, fontSizeNorm, (m.group()).trim(), lastX, lastY, contentStream);
                lastY -= 15;
                //System.out.println(m.group().trim() + " " + lastY +","+lastX);
            }

        } else {
            printTextTo(fontRegular, fontSizeNorm, string, lastX, lastY, contentStream);
            lastY -= 15;
        }
        lastX -= 60;

        //customer part - address
        printTextTo(fontBold, fontSizeNorm, "Aadress:  ", lastX, lastY, contentStream);
        lastX += 60;

        string = invoice.getCustomerAddress();
        if(string.length() > 40) {
            Pattern p = Pattern.compile("[^,]+");
            Matcher m = p.matcher(string);
            while (m.find()) {
                printTextTo(fontRegular, fontSizeNorm, (m.group()).trim(), lastX, lastY, contentStream);
                lastY -= 15;
            }
        } else {
            printTextTo(fontRegular, fontSizeNorm, string, lastX, lastY, contentStream);
            lastY -= 15;
        }
        lastX -= 60;

        // table headers
        lastX = 70f;
        lastY = 520f;
        contentStream.beginText();
        contentStream.setFont(fontBold, fontSizeNorm);
        contentStream.newLineAtOffset(lastX, lastY);
        contentStream.showText("Teenus");
        contentStream.endText();
        lastX += 220;
        contentStream.beginText();
        contentStream.newLineAtOffset(lastX, lastY);
        contentStream.showText("Kogus");
        contentStream.endText();
        lastX += 80;
        contentStream.beginText();
        contentStream.newLineAtOffset(lastX, lastY);
        contentStream.showText("Hind");
        contentStream.endText();
        lastX += 90;
        contentStream.beginText();
        contentStream.newLineAtOffset(lastX, lastY);
        contentStream.showText("Summa");
        contentStream.endText();
        lastX -= 390;
        lastY -= 6;

        // table
        // add a line to separate table header
        contentStream.setLineWidth(1);
        contentStream.moveTo(lastX, lastY);
        lastX += 450;
        contentStream.lineTo(lastX, lastY);
        contentStream.closeAndStroke();
        lastX -= 450;
        lastY -= 20;


        //table content
        Double sum = 0.0;
        if (!invoice.getInvoiceRows().isEmpty()) {
            List<InvoiceRow> invoiceRowsList = invoice.getInvoiceRows();
            for(InvoiceRow i : invoiceRowsList) {
                contentStream.beginText();
                contentStream.setFont(fontRegular, fontSizeNorm);
                contentStream.newLineAtOffset(lastX, lastY);
                contentStream.showText(i.getTitle());
                contentStream.endText();
                lastX += 220;
                contentStream.beginText();
                contentStream.newLineAtOffset(lastX, lastY);
                contentStream.showText(i.getQuantity().toString());
                contentStream.endText();
                lastX += 80;
                contentStream.beginText();
                contentStream.newLineAtOffset(lastX, lastY);
                contentStream.showText(Util.printTwoDecimals(i.getUnitPrice()));
                contentStream.endText();
                lastX += 90;
                contentStream.beginText();
                contentStream.newLineAtOffset(lastX, lastY);
                contentStream.showText(Util.printTwoDecimals(i.getSumPrice()));
                contentStream.endText();
                lastX -= 390;
                lastY -= 18;
                sum += i.getSumPrice();
            }
        }

        // add a line to separate table footer
        lastY += 5;
        contentStream.setLineWidth(1);
        contentStream.moveTo(lastX, lastY);
        lastX += 450;
        contentStream.lineTo(lastX, lastY);
        contentStream.closeAndStroke();
        lastX -= 450;
        lastY -= 24;

        //table sum part
        lastX += 300;
        if(invoice.getIsVATRequired()) {
            Double vat = sum * 0.2;
            contentStream.beginText();
            contentStream.setFont(fontRegular, fontSizeNorm);
            contentStream.newLineAtOffset(lastX,lastY);
            contentStream.showText("Kokku:");
            contentStream.endText();
            lastX += 90;
            contentStream.beginText();
            contentStream.newLineAtOffset(lastX,lastY);
            contentStream.showText(Util.printTwoDecimals(sum));
            contentStream.endText();
            lastY -= 20;
            lastX -= 90;
            contentStream.beginText();
            contentStream.setFont(fontRegular, fontSizeNorm);
            contentStream.newLineAtOffset(lastX,lastY);
            contentStream.showText("Käibemks:");
            contentStream.endText();
            lastX += 90;
            contentStream.beginText();
            contentStream.newLineAtOffset(lastX,lastY);
            contentStream.showText(Util.printTwoDecimals(vat));
            contentStream.endText();
            sum = sum * 1.2;
            lastX -= 90;
            lastY -= 20;
        }

        contentStream.beginText();
        contentStream.setFont(fontBold, fontSizeNorm);
        contentStream.newLineAtOffset(lastX,lastY);
        contentStream.showText("Tasuda (EUR):");
        contentStream.endText();
        lastX += 90;
        contentStream.beginText();
        contentStream.newLineAtOffset(lastX,lastY);
        contentStream.showText(Util.printTwoDecimals(sum));
        contentStream.endText();

        //invoice notes
        lastX -= 390;
        lastY -= 40;
        contentStream.beginText();
        contentStream.setFont(fontRegular, fontSizeNorm);
        contentStream.newLineAtOffset(lastX,lastY);
        contentStream.showText(invoice.getOwnerNotes());
        contentStream.endText();

        //invoice sales representative
        lastY -= 110;
        contentStream.beginText();
        contentStream.setFont(fontRegular, fontSizeNorm);
        contentStream.newLineAtOffset(lastX,lastY);
        contentStream.showText(invoice.getOwnerSalesName());
        contentStream.endText();


        // ---footer creation---
        // add one line
        contentStream.setLineWidth(1);
        contentStream.moveTo(70, 84);
        contentStream.lineTo(520, 84);
        contentStream.closeAndStroke();
        contentStream.beginText();
        contentStream.setFont(fontRegular, fontSizeSmall);
        contentStream.newLineAtOffset(70,70);
        contentStream.showText(invoice.getOwnerName());
        contentStream.newLineAtOffset(0,-12);
        contentStream.showText("Reg.kood " +invoice.getOwnerName());
        contentStream.newLineAtOffset(0,-12);
        contentStream.showText(invoice.getOwnerAddress());
        contentStream.newLineAtOffset(0,-12);
        contentStream.showText("Põlva maakond");
        contentStream.endText();
        // footer mid section
        contentStream.beginText();
        contentStream.setFont(fontRegular, fontSizeSmall);
        contentStream.newLineAtOffset(230,70);
        contentStream.showText("Tel: " + invoice.getOwnerPhone());
        contentStream.newLineAtOffset(0,-12);
        contentStream.showText("Email: " + invoice.getOwnerEmail());
        // footer right section
        contentStream.newLineAtOffset(140,12);
        contentStream.showText("IBAN " + invoice.getOwnerIBAN());
        contentStream.newLineAtOffset(0,-12);
        contentStream.showText(invoice.getOwnerBank());
        contentStream.endText();

        contentStream.close();

        document.save(fileName + ".pdf");
        document.close();
    }

    private static void printTextTo(PDTrueTypeFont fontRegular, Integer fontSizeNorm, String string, Float lastX, Float lastY, PDPageContentStream contentStream) throws IOException {
        contentStream.beginText();
        contentStream.setFont(fontRegular, fontSizeNorm);
        contentStream.newLineAtOffset(lastX,lastY);
        contentStream.showText(string);
        contentStream.endText();
    }
}
