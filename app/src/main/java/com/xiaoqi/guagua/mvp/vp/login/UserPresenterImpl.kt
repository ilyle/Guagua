package com.xiaoqi.guagua.mvp.vp.login

import com.xiaoqi.guagua.mvp.model.bean.UserData
import com.xiaoqi.guagua.mvp.model.source.UserDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

class UserPresenterImpl(private val mLoginView: LoginView,
                        private val mRegisterView: RegisterView?,
                        private val mMineView: MineView?,
                        private val mModel: UserDataSource) : UserPresenter {

    private val mDisposable: CompositeDisposable = CompositeDisposable()

    init {
        mLoginView.setPresenter(this)
        mRegisterView?.setPresenter(this)
        mMineView?.setPresenter(this)
    }

    constructor(loginView: LoginView, model: UserDataSource) : this(loginView, null, null, model)

    override fun subscribe() {
    }

    override fun unSubscribe() {
        mDisposable.clear()
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

    override fun login(token: String) {
        val disposable = mModel.login(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<UserData>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: UserData) {
                        if (t.errorCode == -1) {
                            t.errorMsg?.let { mLoginView.showLoginFail(it) }
                        } else {
                            t.data?.let { mLoginView.loginSuccess(it) }
                        }
                    }

                    override fun onError(e: Throwable) {
                        mLoginView.showNetworkError(e.toString())
                    }

                })
        mDisposable.add(disposable)
    }

    override fun logout(uid: String) {
        val disposable = mModel.logout(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<UserData>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: UserData) {
                        mMineView?.let { view ->
                            if (view.isActive()) {
                                if (t.errorCode == -1) {
                                    t.errorMsg?.let { view.showLogoutFail(it) }
                                } else {
                                    t.data?.let { view.logoutSuccess(it) }
                                }
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        mMineView?.let {
                            if (it.isActive()) {
                                it.showNetworkError(e.toString())
                            }
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
                        mRegisterView?.let { view ->
                            if (view.isActive()) {
                                if (t.errorCode == -1) {
                                    t.errorMsg?.let { view.showRegisterFail(it) }
                                } else {
                                    t.data?.let { view.registerSuccess(it) }
                                }
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        mRegisterView?.let {
                            if (it.isActive()) {
                                it.showNetworkError(e.toString())
                            }
                        }
                    }
                })
        mDisposable.add(disposable)
    }

    override fun updateAvatar(uid: String, username: String, avatar: MultipartBody.Part) {
        val disposable = mModel.updateAvatar(uid, username, avatar)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<UserData>() {
                    override fun onError(e: Throwable) {
                        mMineView?.let {
                            if (it.isActive()) {
                                it.showNetworkError(e.toString())
                            }
                        }
                    }

                    override fun onNext(t: UserData) {

                        mMineView?.let { view ->
                            if (view.isActive()) {
                                if (t.errorCode == -1) {
                                    t.errorMsg?.let { view.showUpdateAvatarFail(it) }
                                } else {
                                    t.data?.let { view.showUpdateAvatarSuccess(it) }
                                }
                            }
                        }
                    }

                    override fun onComplete() {

                    }

                })
        mDisposable.add(disposable)
    }

    companion object {
        fun build(loginView: LoginView, registerView: RegisterView, mineView: MineView, model: UserDataSource) {
            UserPresenterImpl(loginView, registerView, mineView, model)
        }

        fun build(loginView: LoginView, model: UserDataSource) {
            UserPresenterImpl(loginView, model)
        }
    }
}