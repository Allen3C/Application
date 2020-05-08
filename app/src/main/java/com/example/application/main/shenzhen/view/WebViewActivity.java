package com.example.application.main.shenzhen.view;

import android.webkit.WebView;
import android.webkit.WebViewClient;


import com.example.application.R;
import com.example.application.base.BaseActivity;
import com.example.application.base.ViewInject;

import butterknife.BindView;

@ViewInject(mainlayoutid = R.layout.activity_webview)
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.web_view)
    WebView web_view;
    @Override
    public void afterBindView() {
        web_view.setWebViewClient(new WebViewClient());
        web_view.loadUrl("http://www.baidu.com");
    }
}
