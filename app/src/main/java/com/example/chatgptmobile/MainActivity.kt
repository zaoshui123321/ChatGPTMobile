package com.example.chatgptmobile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.browser.customtabs.CustomTabsIntent

/**
 * Opens the official ChatGPT website in a Chrome Custom Tab.
 * This avoids embedding the sign-in flow in an Android WebView and lets
 * the browser handle Google/OAuth sign-in using its supported browser context.
 */
class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openChatGPT()
    }

    private fun openChatGPT() {
        val uri = Uri.parse(HOME_URL)
        try {
            val customTabsIntent = CustomTabsIntent.Builder()
                .setShowTitle(true)
                .build()
            customTabsIntent.launchUrl(this, uri)
        } catch (_: Exception) {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            } catch (_: Exception) {
                finish()
            }
        }
    }

    companion object {
        private const val HOME_URL = "https://chatgpt.com/"
    }
}
