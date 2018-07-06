package canh.tan.nguye.newsreader;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dmax.dialog.SpotsDialog;

public class DetailArticleActivity extends AppCompatActivity {

    WebView webView;

    AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);

        webView = findViewById(R.id.webView);
        dialog = new SpotsDialog.Builder().setContext(this).build();

        dialog.show();

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialog.dismiss();
            }
        });


        if (!getIntent().getStringExtra("webUrl").isEmpty()){
            webView.loadUrl(getIntent().getStringExtra("webUrl"));
        }
    }
}
