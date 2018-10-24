package com.xiaoqi.guagua.mvp.vp.banner

import com.xiaoqi.guagua.mvp.model.bean.Banner
import com.xiaoqi.guagua.mvp.model.source.BannerDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class BannerPresenterImpl private constructor(private val mView: BannerView, private val mModel: BannerDataSource): BannerPresenter {

    private val mDisposable: CompositeDisposable = CompositeDisposable()

    init {
        mView.setPresenter(this)
    }

    companion object {
        fun build(view: BannerView, model: BannerDataSource){
            BannerPresenterImpl(view, model)
        }
    }

    override fun getBanner() {
        val disposable: Disposable = mModel.getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<Banner>>() {
                    override fun onComplete() {

                    }

                    override fun onNext(t: List<Banner>) {
                        mView.showBanner(t)
                    }

                    override fun onError(e: Throwable) {
                    }

                })
        mDisposable.add(disposable)
    }
}