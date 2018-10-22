package com.xiaoqi.guagua.mvp.vp.article

import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View

class ArticleBehavior : CoordinatorLayout.Behavior<View>(){

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is Toolbar
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return true
    }
}