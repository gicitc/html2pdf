package itc.gic.html2pdf.app;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.itextpdf.text.DocumentException;

import java.io.File;
import java.io.IOException;

import itc.gic.html2pdf.Html2PdfFactory;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private EditText etSearch;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView) findViewById(R.id.wv_main);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                webView.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementsByTagName('html')[0].innerHTML)");
            }
        });

        etSearch = (EditText) findViewById(R.id.et_search);
    }

    public void doSearch(View view) {
        webView.loadUrl(etSearch.getText().toString());
    }

    public void doToPdfNoScript(View view) {
        File dir = Environment.getExternalStoragePublicDirectory("Download");
        try {
            Html2PdfFactory.htmlText2pdf(content, new File(dir, "html.pdf").getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }
    class MyJavaScriptInterface {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html){
            content = html;
        }
    }
}
