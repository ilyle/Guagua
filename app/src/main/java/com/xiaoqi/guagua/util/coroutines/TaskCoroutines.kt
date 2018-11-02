package com.xiaoqi.guagua.util.coroutines

import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.experimental.Delay
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.newFixedThreadPoolContext

/**
 *
 * @author zhujiechen@sohu-inc.com （陈祝界）
 * 创建于 2017/11/3
 */
/**
 * 根据当前处理器个数，并获得合理的线程池大小，作为异步协程的上下文
 */
@PublishedApi
internal var sThreadPool = newFixedThreadPoolContext(Runtime.getRuntime().availableProcessors() * 2, "ThreadPool")
/**
 * 将执行调度到Android主UI线程上，并提供本机[延迟] [Delay.delay]支持。
 */
val sMainThreadPool = HandlerContext(Handler(Looper.getMainLooper()), "sMainThreadPool")

/**
 * 并发执行，常用于最外层
 * 特点不带返回值
 */
fun <T> taskLaunch(delayTime: Long = 0, job: suspend () -> T) = launch(sThreadPool) {
    delay(delayTime)
    job()
}

/**
 * 在Android UI线程中执行，可以用于最外层
 * 此方法用于协程上下文调度，目前主要用于切换到android UI线程
 * 参数添加CoroutineStart.UNDISPATCHED的话表示立即执行
 */
fun <T> taskRunOnUiThread(job: suspend () -> T) = launch(sMainThreadPool) {
    job()
}

/**
 * 心跳执行 默认重复次数1次，不能用于最外层
 */
suspend inline fun <T> taskHeartbeat(times: Int = 1, delayTime: Long = 0, crossinline job: () -> T) = repeat(times) {
    delay(delayTime)
    job()
}

