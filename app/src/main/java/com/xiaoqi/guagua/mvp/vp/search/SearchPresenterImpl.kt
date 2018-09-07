package com.xiaoqi.guagua.mvp.vp.search

import com.xiaoqi.guagua.mvp.model.bean.Article
import com.xiaoqi.guagua.mvp.model.source.ArticleDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class SearchPresenterImpl private constructor(view: SearchView, model: ArticleDataSource) : SearchPresenter {

    private val mView = view
    private val mModel = model

    private val mDisposable: CompositeDisposable

    init {
        mView.setPresenter(this)
        mDisposable = CompositeDisposable()
    }

    companion object {
        fun build(view: SearchView, model: ArticleDataSource): SearchPresenterImpl {
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
                            if (t.isEmpty()) {
                                mView.showEmpty(true)
                            } else {
                                mView.showEmpty(false)
                                mView.showArticle(t)
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (mView.isActive()) {
                            mView.showEmpty(true)
                        }
                    }
                })
        mDisposable.add(disposable)
    }

    override fun categoryArticle(page: Int, categoryId: Int, forceUpdate: Boolean, cleanCache: Boolean) {
        val disposable = mModel.categoryArticle(page, categoryId, forceUpdate, cleanCache)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<MutableList<Article>>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: MutableList<Article>) {
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
        mDisposable.add(disposable)
    }
}