package com.xiaoqi.guagua.mvp.vp.search

import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.model.source.ArticleDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchPresenterImpl private constructor(view: SearchView, model: ArticleDataSource) : SearchPresenter {

    private val mView = view
    private val mModel = model

    init {
        mView.setPresenter(this)
    }

    companion object {
        fun build(view: SearchView, model: ArticleDataSource) : SearchPresenterImpl {
            return SearchPresenterImpl(view, model)
        }
    }

    override fun subscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unSubscribe() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun queryArticle(page: Int, query: String, forceUpdate: Boolean, cleanCache: Boolean) {
        val disposable: Disposable = mModel.queryArticle(page, query, forceUpdate, cleanCache)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<Article>>() {
                    override fun onComplete() {
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
                        }
                    }
                })
    }

}