package tn.esprit.myapplication.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;
import tn.esprit.myapplication.R;

public class StripeWebViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stripe_webview); // ← important

        webView = findViewById(R.id.webViewStripe);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                if (url.contains("payment_success")) {
                    Intent intent = new Intent(StripeWebViewActivity.this, InvoiceActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


        String stripeLink = "https://buy.stripe.com/test_3cI8wR2SucOL7lx5L71sQ01";
        webView.loadUrl(stripeLink);
    }
}
