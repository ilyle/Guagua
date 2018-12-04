package com.xiaoqi.guagua.mvp.vp.article.suggestion

import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.model.source.ArticleDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SuggestionPresenterImpl private constructor(view: SuggestionView, model: ArticleDataSource) : SuggestionPresenter {

    private val mView = view // 拥有View实例
    private val mModel = model // 拥有Model实例

    private val mDisposable: CompositeDisposable

    init {
        mView.setPresenter(this)
        mDisposable = CompositeDisposable()
    }

    companion object {
        /**
         * 对外构造函数，presenter的初始化在init中
         */
        fun build(view: SuggestionView, model: ArticleDataSource) {
            SuggestionPresenterImpl(view, model)
        }
    }

    override fun listArticle(page: Int, forceUpdate: Boolean, cleanCache: Boolean) {
        val disposable: Disposable = mModel.listArticle(page, forceUpdate, cleanCache)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<Article>>() {
                    override fun onComplete() {
                        if (mView.isActive()) {
                            mView.setLoadingIndicator(false)
                        }
                    }

                    override fun onNext(t: List<Article>) {
                        if (mView.isActive()) {
                            mView.showEmpty(false)
                            mView.showArticle(t)
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (mView.isActive()) {
                            mView.showEmpty(true)
                            mView.setLoadingIndicator(false)
                        }
                    }
                })
        mDisposable.add(disposable)
    }

    override fun subscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unSubscribe() {
        mDisposable.clear() // 切断管道，使得下游的Observe接受不到上游Observable发送的事件，避免View被回收而出现异常
    }

}