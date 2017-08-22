package itc.gic.html2pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

/**
 * Created by Institute on 8/19/2017.
 */

public class Html2PdfFactory {
    public static void htmlText2pdf(String text, String filePDF) throws IOException, DocumentException {
        Document document = new Document();
        FileOutputStream os = new FileOutputStream(filePDF);
        PdfWriter pdfWriter = PdfWriter.getInstance(document, os);
        document.open();

        /*HTMLWorker htmlWorker = new HTMLWorker(document);
        htmlWorker.parse(new StringReader(text));*/
        InputStream is = new ByteArrayInputStream(text.getBytes());
        XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, is);

        document.close();
        os.close();
    }
}
