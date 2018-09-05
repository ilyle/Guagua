package com.xiaoqi.guagua

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.webkit.WebView

class LicenseActivity : AppCompatActivity() {

    private lateinit var mTbLicense: Toolbar
    private lateinit var mWvLicense: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)
        initView()
    }

    private fun initView() {
        mTbLicense = findViewById(R.id.tb_license)
        mTbLicense.setNavigationOnClickListener { onBackPressed() }
        mWvLicense = findViewById(R.id.wv_license)
        mWvLicense.loadUrl("file:///android_asset/licenses.html")
    }
}
