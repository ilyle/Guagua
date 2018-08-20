package com.xiaoqi.base.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.*
import com.xiaoqi.base.R

/**
 * @author mochangsheng
 * @description 基本通用的Dialog
 */
class BaseDialog private constructor(builder: Builder)
    : Dialog(builder.mContext, if (builder.mResStyle != -1) builder.mResStyle else R.style.BaseDialog) {

    private var mHeight = -1
    private var mWidth = -1
    private var mContentView: View
    private var mResStyle = -1
    private var mCanceledOnTouchOutside = true
    private var mGravity = Gravity.CENTER
    private var mVerticalMargin = 0.0f

    /**
     * 对外提供view，方便根据业务设定view的内容
     */
    fun getContentView(): View {
        return mContentView
    }

    init {
        mHeight = builder.mHeight
        mWidth = builder.mWidth
        mContentView = builder.mContentView
        mResStyle = builder.mResStyle
        mGravity = builder.mGravity
        mVerticalMargin = builder.mVerticalMargin
        mCanceledOnTouchOutside = builder.mCanceledOnTouchOutside
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(mContentView)
        setCanceledOnTouchOutside(mCanceledOnTouchOutside)

        window.attributes.verticalMargin = mVerticalMargin
        window.attributes.gravity = mGravity
        window.attributes.height = mHeight
        window.attributes.width = mWidth
        window.attributes = window.attributes
    }

    /**
     * 通用Dialog构造者
     */
    class Builder(context: Context) {
        internal var mContext: Context = context
        //高度
        internal var mHeight = -1
        //宽度
        internal var mWidth = -1
        //弹窗显示View
        internal lateinit var mContentView: View
        //弹窗样式
        internal var mResStyle = -1
        //弹窗外是否点击可取消
        internal var mCanceledOnTouchOutside = true
        internal var mGravity = Gravity.CENTER
        internal var mVerticalMargin = 0.0f

        /**
         *  设置高度  像素
         */
        fun heightPx(height: Int): Builder {
            mHeight = height
            return this
        }

        /**
         * 设置宽度  像素
         */
        fun widthPx(width: Int): Builder {
            mWidth = width
            return this
        }


        /**
         * 设置弹窗内容View
         */
        fun contentView(viewRes: Int): Builder {
            mContentView = LayoutInflater.from(mContext).inflate(viewRes, null)
            return this
        }

        /**
         * 设置弹窗内容View
         */
        fun contentView(view: View): Builder {
            mContentView = view
            return this
        }

        /**
         *  设置高度  资源dimen Id
         */
        fun heightDimenRes(dimenRes: Int): Builder {
            mHeight = mContext.resources.getDimensionPixelOffset(dimenRes)
            return this
        }

        /**
         * 设置宽度  资源dimen Id
         */
        fun widthDimenRes(dimenRes: Int): Builder {
            mWidth = mContext.resources.getDimensionPixelOffset(dimenRes)
            return this
        }

        /**
         * 设置style样式
         */
        fun style(resStyle: Int): Builder {
            mResStyle = resStyle
            return this
        }

        /**
         * 设置点击弹窗外是否dismiss
         */
        fun canceledOnTouchOutside(cancel: Boolean): Builder {
            mCanceledOnTouchOutside = cancel
            return this
        }

        /**
         * 显示的位置
         */
        fun gravity(gravity: Int): Builder {
            mGravity = gravity
            return this
        }


        /**
         * 垂直间距  比例来的
         */
        fun verticalMargin(verticalMargin: Float): Builder {
            mVerticalMargin = verticalMargin
            return this
        }

        /**
         * 添加点击监听
         */
        fun addViewOnClick(viewId: Int, listener: View.OnClickListener): Builder {
            (mContentView.findViewById<View>(viewId)).setOnClickListener(listener)
            return this
        }

        fun build(): BaseDialog {
            return BaseDialog(this)
        }

    }
}

