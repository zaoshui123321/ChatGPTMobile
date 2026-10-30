package com.example.chatgptmobile

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    private lateinit var webView: WebView
    private lateinit var statusText: TextView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(0xFFF7F7F8.toInt())
        }

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = android.view.Gravity.CENTER_VERTICAL
            setPadding(16, 12, 12, 12)
            setBackgroundColor(0xFF202123.toInt())
        }

        val title = TextView(this).apply {
            text = "ChatGPT 手机版"
            textSize = 18f
            setTextColor(0xFFFFFFFF.toInt())
            layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        }
        val homeButton = Button(this).apply {
            text = "首页"
            setOnClickListener { webView.loadUrl(HOME_URL) }
        }
        header.addView(title)
        header.addView(homeButton)

        statusText = TextView(this).apply {
            text = "正在打开 ChatGPT…"
            textSize = 13f
            setTextColor(0xFF555555.toInt())
            setPadding(16, 8, 16, 8)
        }

        webView = WebView(this).apply {
            setBackgroundColor(0xFFF7F7F8.toInt())
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            settings.loadsImagesAutomatically = true
            settings.javaScriptCanOpenWindowsAutomatically = false
            settings.allowFileAccess = false
            settings.allowContentAccess = false
            CookieManager.getInstance().setAcceptCookie(true)
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)
            webChromeClient = WebChromeClient()
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    statusText.text = "正在加载…"
                    statusText.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    statusText.visibility = View.GONE
                }

                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    val uri = request?.url ?: return false
                    val host = uri.host.orEmpty()
                    val allowed = host == "chatgpt.com" ||
                        host.endsWith(".chatgpt.com") ||
                        host == "openai.com" ||
                        host.endsWith(".openai.com") ||
                        host == "auth0.openai.com"
                    if (uri.scheme == "https" && allowed) return false

                    // Open external links in the phone's browser.
                    return try {
                        startActivity(Intent(Intent.ACTION_VIEW, uri))
                        true
                    } catch (_: Exception) {
                        true
                    }
                }
            }
        }

        root.addView(header)
        root.addView(statusText)
        root.addView(webView, LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f
        ))
        setContentView(root)

        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState)
        } else {
            webView.loadUrl(HOME_URL)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        webView.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    @Deprecated("Deprecated in Android API")
    override fun onBackPressed() {
        if (::webView.isInitialized && webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        if (::webView.isInitialized) {
            webView.stopLoading()
            webView.destroy()
        }
        super.onDestroy()
    }

    companion object {
        private const val HOME_URL = "https://chatgpt.com/"
    }
}
