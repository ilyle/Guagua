package com.xiaoqi.guagua

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button

class MineActivity : AppCompatActivity(), OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_mine_logout -> {}
        }
    }

    private lateinit var mBtnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_logout)
        initView()
    }

    private fun initView() {
        mBtnLogout = findViewById(R.id.btn_mine_logout)
        mBtnLogout.setOnClickListener(this)
    }
}