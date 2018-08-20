package com.xiaoqi.guagua.mvp.presenter.impl

import com.xiaoqi.guagua.mvp.model.bean.EssayData.Data.Essay
import com.xiaoqi.guagua.mvp.model.source.impl.EssayDataSourceImpl
import com.xiaoqi.guagua.mvp.presenter.EssayPresenter
import com.xiaoqi.guagua.mvp.view.article.essay.EssayView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class EssayPresenterImpl private constructor(view: EssayView, model: EssayDataSourceImpl) : EssayPresenter {

    private val mView = view // 拥有View实例
    private val mModel = model // 拥有Model实例

    private val mCompositeDisposable: CompositeDisposable

    init {
        mView.setPresenter(this)
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
        val disposable: Disposable = mModel.getEssay(page, forceUpdate, cleanCache)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<Essay>>() {
                    override fun onComplete() {
                        if (mView.isActive()) {
                            mView.setLoadingIndicator(false)
                        }
                    }

                    override fun onNext(t: List<Essay>) {
                        if (mView.isActive()) {
                            mView.showEmptyView(false)
                            mView.showEssay(t)
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (mView.isActive()) {
                            mView.showEmptyView(true)
                            mView.setLoadingIndicator(false)
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