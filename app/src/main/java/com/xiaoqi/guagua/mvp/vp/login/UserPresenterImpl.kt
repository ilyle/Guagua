package com.xiaoqi.guagua.mvp.vp.login

import com.xiaoqi.guagua.mvp.model.bean.UserData
import com.xiaoqi.guagua.mvp.model.source.UserDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class UserPresenterImpl(private val mLoginView: LoginView, private val mRegisterView: RegisterView, private val mMineView: MineView, private val mModel: UserDataSource) : UserPresenter {

    private val mDisposable: CompositeDisposable = CompositeDisposable()

    init {
        mLoginView.setPresenter(this)
        mRegisterView.setPresenter(this)
        mMineView.setPresenter(this)
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

    override fun logout(uid: String) {
        val disposable = mModel.logout(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<UserData>(){
                    override fun onComplete() {
                    }

                    override fun onNext(t: UserData) {
                        if (mMineView.isActive()) {
                            if (t.errorCode == -1) {
                                t.errorMsg?.let { mMineView.showLogoutFail(it) }
                            } else {
                                t.data?.let { mMineView.logoutSuccess(it) }
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (mMineView.isActive()) {
                            mMineView.showNetworkError(e.toString())
                        }
                    }
                })
        mDisposable.add(disposable)
    }

    override fun register(username: String, password: String) {
        val disposable = mModel.register(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<UserData>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: UserData) {
                        if (mRegisterView.isActive()) {
                            if (t.errorCode == -1) {
                                t.errorMsg?.let { mRegisterView.showRegisterFail(it) }
                            } else {
                                t.data?.let { mRegisterView.registerSuccess(it) }
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (mRegisterView.isActive()) {
                            mRegisterView.showNetworkError(e.toString())
                        }
                    }
                })
        mDisposable.add(disposable)
    }

    companion object {
        fun build(loginView: LoginView, registerView: RegisterView, mineView: MineView, model: UserDataSource) {
            UserPresenterImpl(loginView, registerView, mineView, model)
        }
    }
}