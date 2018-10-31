package com.xiaoqi.guagua.util

import android.text.TextUtils
import java.security.MessageDigest

/**
 * Created by Xujie on 2018/10/31
 * mail: jiexu215936@sohu-inc.com
 */
object Md5Util {

    @JvmStatic
    fun encode(input: String): String {
        if (TextUtils.isEmpty(input))
            return ""
        val md5: MessageDigest?
        try {
            md5 = MessageDigest.getInstance("MD5")
            md5?.let {
                val charArray = input.toCharArray()
                val byteArray = ByteArray(charArray.size)
                for (i in charArray.indices)
                    byteArray[i] = charArray[i].toByte()
                val md5Bytes = it.digest(byteArray)
                val hexValue = StringBuilder()
                for (i in md5Bytes.indices) {
                    val v = md5Bytes[i].toInt() and 0xff
                    if (v < 16)
                        hexValue.append("0")
                    hexValue.append(Integer.toHexString(v))
                }
                return hexValue.toString()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
        return ""
    }
}