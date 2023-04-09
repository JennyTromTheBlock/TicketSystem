package BLL;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class GeneratePDF {

private static String FILE = "resources/temp.pdf";






    public void generatePDF() {
        //created PDF document instance
        Document doc = new Document();
        try {
//generate a PDF at the specified location
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(FILE));
            System.out.println("PDF created.");
//opens the PDF
            doc.open();
//adds paragraph to the PDF file
            doc.add(new Paragraph("her er min helt egen paragraph"));
//close the PDF file
            doc.close();
//closes the writer
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
