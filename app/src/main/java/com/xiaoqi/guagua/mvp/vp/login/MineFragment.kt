package com.xiaoqi.guagua.mvp.vp.login

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.xiaoqi.base.dialog.BaseDialog
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.constant.Constant
import com.xiaoqi.guagua.mvp.model.bean.User
import com.xiaoqi.guagua.mvp.model.bean.UserInfo
import com.xiaoqi.guagua.retrofit.Api
import com.xiaoqi.guagua.util.PreferenceUtil
import com.xiaoqi.guagua.util.ToastUtil
import com.xiaoqi.liteitemview.LiteItemView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MineFragment : BaseFragment(), MineView, View.OnClickListener {

    private lateinit var mPresenter: UserPresenter
    private lateinit var mLivUsername: LiteItemView
    private lateinit var mIvMine: ImageView // 大头像
    private lateinit var mTbMine: Toolbar
    private lateinit var mIvAvatar: ImageView
    private lateinit var mBtnLogout: Button

    private lateinit var mDialog: BaseDialog // 头像选择对话框
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_mine_logout -> {
                UserInfo.user?.let { mPresenter.logout(it.uid!!) }
            }
            R.id.iv_mine_avatar -> {
                showAvatarChooseDialog()
            }
        /*
        选择相机拍摄
         */
            R.id.tv_mine_camera -> {
                closeDialog()
                // openCamera()
            }
        /*
        选择从图片中选取
         */
            R.id.tv_mine_gallery -> {
                closeDialog()
                openGallery()
            }
        }
    }

    override fun isActive(): Boolean {
        return isAdded && isResumed
    }

    override fun logoutSuccess(user: User) {
        ToastUtil.showMsg(getString(R.string.logout_success))
        PreferenceUtil.cleanUser()
        navigate2Main()
    }


    private fun navigate2Main() {
        activity?.onBackPressed()
    }

    override fun showLogoutFail(errorMsg: String) {
        ToastUtil.showMsg(errorMsg)
    }

    override fun showNetworkError(errorMsg: String) {
        ToastUtil.showMsg(errorMsg)
    }

    override fun showUpdateAvatarSuccess(user: User) {
        ToastUtil.showMsg(getString(R.string.toast_update_avatar_success))
        PreferenceUtil.setUser(user)

        user.avatar?.let {
            val avatarUri = Uri.parse(Api.API_GUA_GUA + it)
            val requestOptions = RequestOptions()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(ColorDrawable(Color.WHITE))
                    .error(ColorDrawable(Color.WHITE))
                    .fallback(ColorDrawable(Color.WHITE));
            Glide.with(this).load(avatarUri).apply(requestOptions).into(mIvMine) // 自定义头像
        }
        if (user.avatar == null) {
            Glide.with(this).load(R.drawable.ic_avatar_default).into(mIvMine) // 默认头像
        }
    }

    override fun showUpdateAvatarFail(errorMsg: String) {
        ToastUtil.showMsg(errorMsg)
    }

    override fun setPresenter(presenter: UserPresenter) {
        mPresenter = presenter
    }

    override fun getResource(): Int {
        return R.layout.fragment_mine
    }

    override fun initView(view: View) {
        mLivUsername = view.findViewById(R.id.liv_mine_username)
        mBtnLogout = view.findViewById(R.id.btn_mine_logout)

        mIvMine = view.findViewById(R.id.iv_mine)
        mIvAvatar = view.findViewById(R.id.iv_mine_avatar)
        mTbMine = view.findViewById(R.id.tb_mine)


        mTbMine.setNavigationOnClickListener { activity?.onBackPressed() }
        mLivUsername.rightText = UserInfo.user?.username

        UserInfo.user?.avatar?.let {
            val avatarUri = Uri.parse(Api.API_GUA_GUA + it)
            val requestOptions = RequestOptions()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(ColorDrawable(Color.WHITE))
                    .error(ColorDrawable(Color.WHITE))
                    .fallback(ColorDrawable(Color.WHITE));
            Glide.with(this).load(avatarUri).apply(requestOptions).into(mIvMine) // 自定义头像
        }
        if (UserInfo.user?.avatar == null) {
            Glide.with(this).load(R.drawable.ic_avatar_default).into(mIvMine) // 默认头像
        }

        mIvAvatar.setOnClickListener(this)
        mBtnLogout.setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        /*
        用户无操作返回
         */
        if (resultCode == RESULT_CANCELED) {

        } else if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_CAMERA -> {

                }
                REQUEST_CODE_GALLERY -> {
                    data?.let { cropAvatar(it.data) }
                }
                REQUEST_CODE_CROP -> {
                    data?.let {
                        setAvatar(it)
                    }
                }
            }

        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun showAvatarChooseDialog() {
        context?.let {
            mDialog = BaseDialog.Builder(it)
                    .contentView(R.layout.dialog_mine_avatar)
                    .widthPx(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .heightPx(ViewGroup.LayoutParams.WRAP_CONTENT)
                    .gravity(Gravity.CENTER)
                    .addViewOnClick(R.id.tv_mine_camera, this)
                    .addViewOnClick(R.id.tv_mine_gallery, this)
                    .build()
            mDialog.show()
        }
    }

    private fun closeDialog() {
        if (mDialog.isShowing) {
            mDialog.dismiss()
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        intent.action = Intent.ACTION_PICK
        startActivityForResult(intent, REQUEST_CODE_GALLERY)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val savePath: String = Environment.getExternalStorageDirectory().path
        val avatarFile = File(savePath, "avatar.jpg")
        if (!avatarFile.exists()) {
            avatarFile.mkdirs()
        }
        val avatarUri: Uri

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

        } else {

        }

        // intent.putExtra(MediaStore.EXTRA_OUTPUT, avatarUri)
        startActivityForResult(intent, REQUEST_CODE_CAMERA)
    }

    /**
     * 调用系统剪切功能
     */
    private fun cropAvatar(uri: Uri) {
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        // 设置裁剪
        intent.putExtra("crop", "true")
        // aspectX , aspectY :宽高的比例
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        // outputX , outputY : 裁剪图片宽高
        intent.putExtra("outputX", 200)
        intent.putExtra("outputY", 200)
        intent.putExtra("return-data", true)
        startActivityForResult(intent, REQUEST_CODE_CROP)
    }

    private fun setAvatar(intent: Intent) {
        val extras = intent.extras
        extras?.let {
            val photo: Bitmap = extras.getParcelable("data")
            val savePath = Environment.getExternalStorageDirectory().path + Constant.PATH.PIC
            saveCropPhoto(photo, savePath)
            // mIvMine.setImageBitmap(photo)
        }
    }

    private fun saveCropPhoto(bitmap: Bitmap, path: String) {
        val dir = File(path) // 将要保存图片的路径
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val file = File(path, "avatar.jpg")
        if (file.exists()) {
            file.delete()
        }
        try {
            val bos = BufferedOutputStream(FileOutputStream(file))
            val res = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            if (res) {
                bos.flush()
                bos.close()
                updateAvatar(file) // 更新头像至服务器
            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun updateAvatar(avatar: File) {
        UserInfo.user?.let {
            val uid: String = it.uid!!
            val username: String = it.username!!
            val requestFile: RequestBody = RequestBody.create(MediaType.parse("application/jpeg"), avatar)
            val body = MultipartBody.Part.createFormData("file", avatar.name, requestFile)
            mPresenter.updateAvatar(uid, username, body)
        }
        if (UserInfo.user == null) {
            ToastUtil.showMsg(getString(R.string.toast_login_first))
        }
    }

    companion object {

        const val REQUEST_CODE_GALLERY = 0
        const val REQUEST_CODE_CAMERA = 1
        const val REQUEST_CODE_CROP = 2
        fun newInstance(): MineFragment {
            return MineFragment()
        }
    }
}