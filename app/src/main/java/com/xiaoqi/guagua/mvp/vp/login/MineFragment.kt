package com.xiaoqi.guagua.mvp.vp.login

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.ContentUris
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.xiaoqi.base.dialog.BaseDialog
import com.xiaoqi.base.dialog.ConfirmDialog
import com.xiaoqi.guagua.BaseFragment
import com.xiaoqi.guagua.R
import com.xiaoqi.guagua.constant.Constant
import com.xiaoqi.guagua.mvp.model.bean.User
import com.xiaoqi.guagua.mvp.model.bean.UserInfo
import com.xiaoqi.guagua.retrofit.Api
import com.xiaoqi.guagua.util.GlideUtil
import com.xiaoqi.guagua.util.PreferenceUtil
import com.xiaoqi.guagua.util.ToastUtil
import com.xiaoqi.liteitemview.LiteItemView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class MineFragment : BaseFragment(), MineView, View.OnClickListener {

    private lateinit var mPresenter: UserPresenter
    private lateinit var mLivUsername: LiteItemView
    private lateinit var mIvMine: ImageView // 大头像
    private lateinit var mTbMine: Toolbar
    private lateinit var mIvAvatar: ImageView
    private lateinit var mBtnLogout: Button

    private lateinit var mDialog: BaseDialog // 头像选择对话框

    private val mPicDir = Environment.getExternalStorageDirectory().path + Constant.PATH.PIC // storage/emulated/0/Guagua/pic
    private val mCameImagePath = "came.jpg" // 拍摄保存
    private val mCropImagePath = "avatar.jpg" // 截图保存

    private val mCameImageFile: File by lazy {
        val dir = File(mPicDir)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val file = File(mPicDir, mCameImagePath)
        if (file.exists()) {
            file.delete()
        }
        file
    }

    private val mCropImageFile: File by lazy {
        val dir = File(mPicDir)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val file = File(mPicDir, mCropImagePath)
        if (file.exists()) {
            file.delete()
        }
        file
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_mine_logout -> {
                // 弹出对话框
                val dialog = ConfirmDialog(context!!)
                dialog.setContent(R.string.logout_confirm)
                        .setLeftBtn(R.string.cancel, View.OnClickListener { dialog.dismiss() })
                        .setRightBtn(R.string.sure, View.OnClickListener {
                            dialog.dismiss()
                            UserInfo.user?.let { mPresenter.logout(it.uid!!) }
                        }).show()
            }
            R.id.iv_mine_avatar -> {
                showAvatarChooseDialog()
            }
        /*
        选择相机拍摄
         */
            R.id.tv_mine_camera -> {
                closeDialog()
                openCamera()
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
            Glide.with(this).load(avatarUri).apply(GlideUtil.requestOptions).into(mIvMine) // 自定义头像
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
            Glide.with(this).load(avatarUri).apply(GlideUtil.requestOptions).into(mIvMine) // 自定义头像
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
                    crop(mCameImageFile.absolutePath)
                }
                REQUEST_CODE_GALLERY -> {
                    data?.let { crop(handleImage(it)!!) }
                }
                REQUEST_CODE_CROP -> {
                    updateAvatar(mCropImageFile)
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

    /**
     * 打开图库
     */
    private fun openGallery() {
        val intent = Intent()
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
        intent.action = Intent.ACTION_PICK
        startActivityForResult(intent, REQUEST_CODE_GALLERY)
    }

    /**
     * 打开相机
     */
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(activity?.packageManager) != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(context!!, context!!.applicationContext.packageName + ".provider", mCameImageFile))
            } else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCameImageFile))
            }
            startActivityForResult(intent, REQUEST_CODE_CAMERA)
        }
    }

    /**
     * 调用系统剪切功能
     */
    private fun crop(imagePath: String) {
        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(getImageContentUri(File(imagePath)), "image/*")
        intent.putExtra("crop", "true")
        intent.putExtra("aspectX", 1)
        intent.putExtra("aspectY", 1)
        intent.putExtra("outputX", 720)
        intent.putExtra("outputY", 720)
        intent.putExtra("return-data", false)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mCropImageFile))
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("noFaceDetection", true)
        startActivityForResult(intent, REQUEST_CODE_CROP)
    }

    private fun handleImage(intent: Intent): String? {
        val uri = intent.data
        var imagePath: String? = null
        if (Build.VERSION.SDK_INT >= 19) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                if ("com.android.providers.media.documents" == uri.authority) {
                    val id = docId.split(":")[1]
                    val selection = MediaStore.Images.Media._ID + "=" + id
                    imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection)
                } else if ("com.android.providers.downloads.documents" == uri.authority) {
                    val contentUri = ContentUris.withAppendedId(Uri.parse("" + "content://downloads/public_downloads"), docId.toLong())
                    imagePath = getImagePath(contentUri, null)
                }
            } else if ("content" == uri.scheme) {
                imagePath = getImagePath(uri, null)
            }
        } else {
            imagePath = getImagePath(uri, null)
        }
        return imagePath
    }

    private fun getImagePath(uri: Uri, selection: String?): String? {
        var path: String? = null
        val cursor = activity?.contentResolver?.query(uri, null, selection, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            }
            cursor.close()
        }
        return path
    }

    /**
     * 把fileUri转换成ContentUri
     */
    private fun getImageContentUri(imageFile: File): Uri? {
        val filePath = imageFile.absolutePath
        val cursor = activity?.contentResolver?.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Images.Media._ID),
                MediaStore.Images.Media.DATA + "=? ",
                arrayOf(filePath), null)
        return if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID))
            val baseUri = Uri.parse("content://media/external/images/media")
            cursor.close()
            Uri.withAppendedPath(baseUri, "" + id)
        } else {
            if (imageFile.exists()) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DATA, filePath)
                activity?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            } else {
                null
            }
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