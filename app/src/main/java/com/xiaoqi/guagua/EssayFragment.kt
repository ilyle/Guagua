package com.xiaoqi.guagua

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xiaoqi.guagua.bean.Essay

class EssayFragment: Fragment() {
    private lateinit var mRvEssay: RecyclerView

    companion object {
        fun newInstance(): EssayFragment {
            return EssayFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_essay, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {
        mRvEssay = view.findViewById(R.id.rv_essay)
        val essayList = arrayListOf<Essay>()
        val e1 = Essay()
        var e2 = Essay()

        e1.mChapterName = "chapter1"
        e1.mDate = "date1"
        e1.mTitle = "title1"
        e1.mAuthor = "author1"

        e2.mChapterName = "chapter2"
        e2.mDate = "date2"
        e2.mTitle = "title2"
        e2.mAuthor = "author2"

        essayList.add(e1)
        essayList.add(e2)
        val adapter = EssayAdapter(context, essayList)
        mRvEssay.adapter = adapter
        mRvEssay.layoutManager = LinearLayoutManager(context)
    }
}