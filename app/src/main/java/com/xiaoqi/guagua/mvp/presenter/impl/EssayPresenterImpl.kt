package com.xiaoqi.guagua.mvp.presenter.impl

import com.xiaoqi.guagua.mvp.model.bean.EssayData
import com.xiaoqi.guagua.mvp.model.source.impl.EssayDataSourceImpl
import com.xiaoqi.guagua.mvp.presenter.EssayPresenter
import com.xiaoqi.guagua.mvp.view.article.essay.EssayView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class EssayPresenterImpl private constructor(view: EssayView, model: EssayDataSourceImpl) : EssayPresenter {

    private val mEssayView = view // 拥有View实例
    private val mEssayDataSourceImpl = model // 拥有Model实例

    private val mCompositeDisposable: CompositeDisposable

    init {
        mEssayView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    companion object {
        /**
         * 对外构造函数
         */
        fun build(view: EssayView, model: EssayDataSourceImpl) {
            EssayPresenterImpl(view, model)
        }
    }

    override fun getEssay(page: Int, forceUpdate: Boolean, cleanCache: Boolean) {
        val disposable: Disposable = mEssayDataSourceImpl.getEssay(page, forceUpdate, cleanCache)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<EssayData.Data.Essay>>() {
                    override fun onComplete() {
                        if (mEssayView.isActive()) {
                            mEssayView.setLoadingIndicator(false)
                        }
                    }

                    override fun onNext(t: List<EssayData.Data.Essay>) {
                        if (mEssayView.isActive()) {
                            mEssayView.showEmptyView(false)
                            mEssayView.showEssay(t)
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (mEssayView.isActive()) {
                            mEssayView.showEmptyView(true)
                            mEssayView.setLoadingIndicator(false)
                        }
                    }
                })
        mCompositeDisposable.add(disposable)
    }

    override fun subscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unSubscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}