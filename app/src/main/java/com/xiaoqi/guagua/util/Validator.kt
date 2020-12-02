package com.xiaoqi.guagua.util

import java.util.regex.Pattern

/**
 * Created by Xujie on 2018/10/31
 * mail: jiexu215936@sohu-inc.com
 */
object Validator {
    /**
     * \w匹配字母、数字、下划线
     * 用户名：字母或下划线开头，4-16个字符
     */
    private const val REGEX_USERNAME = "^[A-Za-z_]\\w{3,15}$"

    /**
     * \W匹配任意不是字母，数字，下划线，汉字的字符
     * 正向否定匹配 (?!...)，例如(?![a-zA-Z]+\$)匹配非只有大小写字母的情况
     * 密码：大写字母，小写字母，数字，特殊符号必须四选三，长度至少6位
     */
    private const val REGEX_PASSWORD = "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_]+$)(?![a-z0-9]+$)(?![a-z\\W_]+$)(?![0-9\\W_]+$)[a-zA-Z0-9\\W_]{6,}$"

    fun isUsernameValidate(username: String): Boolean {
        return Pattern.matches(REGEX_USERNAME, username)
    }

    fun isPasswordValidate(password: String): Boolean {
        return Pattern.matches(REGEX_PASSWORD, password)
    }
}