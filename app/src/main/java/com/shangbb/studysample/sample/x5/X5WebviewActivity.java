package com.shangbb.studysample.sample.x5;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.EditText;

import com.shangbb.studysample.R;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class X5WebviewActivity extends AppCompatActivity{

    private EditText mUrlEt;
    private Button mGoBtn;
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_x5);

        mUrlEt = (EditText) findViewById(R.id.edit_url);
        mGoBtn = (Button) findViewById(R.id.btn_go);
        mWebView = (WebView) findViewById(R.id.x5_webview);
        initWebView();

        mGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl(mUrlEt.getText().toString());
            }
        });

    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initWebView() {

        mWebView.addJavascriptInterface(new PageJSInterface(), "pagejs");
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAllowFileAccess(true);
        // This settings enable Javascript click() open link in new tab
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        mWebView.setWebViewClient(new WebViewClient() {

        });

        mWebView.loadUrl("https://www.baidu.com/");
    }

    private final class PageJSInterface {

        @JavascriptInterface
        public void clickTest() {

        }
    }
}
