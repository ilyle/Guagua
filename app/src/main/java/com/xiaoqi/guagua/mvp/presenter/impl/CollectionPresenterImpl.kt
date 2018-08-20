package com.xiaoqi.guagua.mvp.presenter.impl

import com.xiaoqi.guagua.mvp.model.bean.Essay
import com.xiaoqi.guagua.mvp.model.source.CollectionDataSource
import com.xiaoqi.guagua.mvp.presenter.CollectionPresenter
import com.xiaoqi.guagua.mvp.view.article.collection.CollectionFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class CollectionPresenterImpl(view: CollectionFragment, model: CollectionDataSource) : CollectionPresenter {

    private val mView = view
    private val mModel = model

    private val mCompositeDisposable: CompositeDisposable

    init {
        mView.setPresenter(this)
        mCompositeDisposable = CompositeDisposable()
    }

    companion object {
        /**
         * 对外构造函数
         */
        fun build(view: CollectionFragment, model: CollectionDataSource) {
            CollectionPresenterImpl(view, model)
        }
    }

    override fun getCollection(userId: Int) {
        val disposable: Disposable = mModel.getCollection(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<MutableList<Essay>>(){
                    override fun onNext(t: MutableList<Essay>) {
                        if (mView.isActive()) {
                            mView.showEmptyView(false)
                            mView.showCollection(t)
                        }
                    }

                    override fun onComplete() {
                    }

                    override fun onError(e: Throwable) {
                        if (mView.isActive()) {
                            mView.showEmptyView(true)
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