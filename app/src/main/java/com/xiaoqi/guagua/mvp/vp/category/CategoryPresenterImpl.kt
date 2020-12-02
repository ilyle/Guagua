package com.xiaoqi.guagua.mvp.vp.category

import com.xiaoqi.guagua.mvp.model.bean.Category
import com.xiaoqi.guagua.mvp.model.source.CategoryDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class CategoryPresenterImpl private constructor(view: CategoryView, model: CategoryDataSource) : CategoryPresenter {

    private val mView = view
    private val mModel = model

    private val mDisposable: CompositeDisposable = CompositeDisposable()

    init {
        mView.setPresenter(this)
    }

    companion object {
        fun build(view: CategoryView, model: CategoryDataSource): CategoryPresenterImpl {
            return CategoryPresenterImpl(view, model)
        }
    }

    override fun listCategory() {
        val disposable: Disposable = mModel.listCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<MutableList<Category>>() {
                    override fun onComplete() {
                        if (mView.isActive()) {
                            mView.setLoadingIndicator(false)
                        }
                    }

                    override fun onNext(t: MutableList<Category>) {
                        if (mView.isActive()) {
                            mView.showEmpty(false)
                            mView.showCategory(t)
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
        mDisposable.clear()
    }
}