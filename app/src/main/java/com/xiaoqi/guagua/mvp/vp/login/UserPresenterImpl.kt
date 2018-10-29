package com.xiaoqi.guagua.mvp.vp.login

import com.xiaoqi.guagua.mvp.model.bean.UserData
import com.xiaoqi.guagua.mvp.model.source.UserDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class UserPresenterImpl(private val mLoginView: LoginView, private val mLogoutView: LogoutView, private val mModel: UserDataSource) : UserPresenter {

    private val mDisposable: CompositeDisposable = CompositeDisposable()

    init {
        mLoginView.setPresenter(this)
        mLogoutView.setPresenter(this)
    }

    override fun login(username: String, password: String) {
        val disposable = mModel.login(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<UserData>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: UserData) {
                        if (mLoginView.isActive()) {
                            if (t.errorCode == -1) {
                                t.errorMsg?.let { mLoginView.showLoginFail(it) }
                            } else {
                                t.data?.let { mLoginView.loginSuccess(it) }
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (mLoginView.isActive()) {
                            mLoginView.showNetworkError(e.toString())
                        }
                    }

                })
        mDisposable.add(disposable)
    }

    override fun logout(userId: Int) {
        val disposable = mModel.logout(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<UserData>(){
                    override fun onComplete() {
                    }

                    override fun onNext(t: UserData) {
                        if (mLogoutView.isActive()) {
                            if (t.errorCode == -1) {
                                t.errorMsg?.let { mLogoutView.showLogoutFail(it) }
                            } else {
                                t.data?.let { mLogoutView.logoutSuccess(it) }
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (mLogoutView.isActive()) {
                            mLogoutView.showNetworkError(e.toString())
                        }
                    }
                })
        mDisposable.add(disposable)
    }

    companion object {
        fun build(loginView: LoginView, logoutView: LogoutView, model: UserDataSource) {
            UserPresenterImpl(loginView, logoutView, model)
        }
    }
}