package com.example.grifon

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val wholesaleIntro = findViewById<TextView>(R.id.wholesale_intro)
        wholesaleIntro.text = Html.fromHtml(
            getString(R.string.wholesale_intro),
            Html.FROM_HTML_MODE_LEGACY,
        )
        wholesaleIntro.movementMethod = LinkMovementMethod.getInstance()
    }
}
