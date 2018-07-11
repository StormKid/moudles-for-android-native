package com.zhandlk.okgoutil

import android.app.Application
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.FileCallback
import com.lzy.okgo.https.HttpsUtils
import com.lzy.okgo.interceptor.HttpLoggingInterceptor
import com.lzy.okgo.model.HttpHeaders
import com.lzy.okgo.model.Progress
import com.lzy.okgo.model.Response
import com.zhandlk.okgoutil.Interf.MyFileNetCallback
import com.zhandlk.okgoutil.Interf.MyViewCallback
import okhttp3.OkHttpClient
import java.io.File
import java.io.Serializable
import java.util.concurrent.TimeUnit
import java.util.logging.Level

/**

@author ke_li
@date 2018/7/9
 */
class OkTools private constructor() {
    private val requestBean = RequestBean()
    private var httpClient = OkHttpClient()
    private val log = HttpLoggingInterceptor("okgo")
    companion object {
        val instance by lazy { OkTools() }
    }

    /**
     * 如果想复写httpclient，请使用此方法复写
     */
    fun initClient(httpClient: OkHttpClient,application: Application){
        this.httpClient = httpClient
        OkGo.getInstance().init(application).okHttpClient = this.httpClient
    }

    fun init(application: Application){
        httpClient = initOkHttpClient()
        initClient(httpClient,application)
    }

    inner class Builder {
        /**
         * 自定义tag分清哪种请求
         */
        fun setTag(tag: String): Builder {
            requestBean.tag = tag
            return this
        }

        fun setUrl(url: String): Builder {
            requestBean.url = url
            return this
        }

        fun putBody(body: HashMap<String, String>): Builder {
            requestBean.body = body
            return this
        }

        fun setParams(params: HashMap<String, String>): Builder {
            requestBean.params = params
            return this
        }

        fun putFile(file: File): Builder {
            requestBean.file = file
            return this
        }

        fun putFiles(files: ArrayList<File>): Builder {
            requestBean.files.clear()
            requestBean.files.addAll(files)
            return this
        }

        fun putFileKey(fileKey: String): Builder {
            requestBean.fileKey = fileKey
            return this
        }

        fun putFileKeys(fileKeys: String): Builder {
            requestBean.fileKeys=fileKeys
            return this
        }
        fun build() = instance
    }

    fun initBuilder()= Builder()


    fun initBaseUrl(base: String): OkTools {
        requestBean.baseUrl = base
        return this
    }

    fun isShowLog(boolean: Boolean): OkTools {
        if (boolean){
            log.setPrintLevel(HttpLoggingInterceptor.Level.BODY)
            log.setColorLevel(Level.WARNING)
        }else log.setPrintLevel(HttpLoggingInterceptor.Level.NONE)
        return this
    }


    fun <T> get(myViewCallback: MyViewCallback<T>) {
        OkGo.get<String>(requestBean.baseUrl + requestBean.url).params(requestBean.params).execute(MyIntentCallback(requestBean, myViewCallback))
    }

    fun <T> post(myViewCallback: MyViewCallback<T>) {
        OkGo.post<String>(requestBean.baseUrl + requestBean.url).params(requestBean.params).execute(MyIntentCallback(requestBean, myViewCallback))
    }

    fun <T> postJson(myViewCallback: MyViewCallback<T>) {
        val json = GsonFactory.toJson(requestBean.body)
        OkGo.post<String>(requestBean.baseUrl + requestBean.url).upJson(json).execute(MyIntentCallback(requestBean, myViewCallback))
    }

    fun <T> postFile(myViewCallback: MyViewCallback<T>) {
        if (requestBean.file.exists())
            OkGo.post<String>(requestBean.baseUrl + requestBean.url).params(requestBean.fileKey, requestBean.file).execute(MyIntentCallback(requestBean, myViewCallback))
    }

    fun <T> postFiles(myViewCallback: MyViewCallback<T>) {
        if (requestBean.files.isEmpty()) return
        OkGo.post<String>(requestBean.baseUrl + requestBean.url).addFileParams(requestBean.fileKeys, requestBean.files).params(requestBean.params).execute(MyIntentCallback(requestBean, myViewCallback))
    }


    fun downLoadFile(myFileNetCallback: MyFileNetCallback) {
        OkGo.post<File>(requestBean.baseUrl + requestBean.url).tag(requestBean.tag).execute(object : FileCallback() {
            override fun onSuccess(response: Response<File>?) {
                if (response!!.code() == 200) {
                    if (response.body() != null) {
                        myFileNetCallback.success(response.body())
                    } else {
                        myFileNetCallback.err("文件为空，请重新选择文件下载")
                    }
                } else {
                    myFileNetCallback.err("网络不给力，请检查网络链接")
                }
            }

            override fun downloadProgress(progress: Progress?) {
                super.downloadProgress(progress)
                if (progress != null)
                    myFileNetCallback.progress(progress)
            }

            override fun onFinish() {
                super.onFinish()
                OkGo.getInstance().cancelTag(this@OkTools)
            }
        })
    }


    private val TIME_OUT = 25000L
    private fun initOkHttpClient(): OkHttpClient {
        val client: OkHttpClient.Builder = OkHttpClient.Builder()
        // 信任所有证书的方式，比较危险
        // TODO 写https证书配置方式
        val sslParams = HttpsUtils.getSslSocketFactory()
        client.addInterceptor(log)
                .readTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .connectTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.MILLISECONDS)
                .pingInterval(TIME_OUT, TimeUnit.MILLISECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
        return client.build()
    }


    data class RequestBean(var url: String = "",
                           var params: HashMap<String, String> = hashMapOf(),
                           var body: HashMap<String, String> = hashMapOf(),
                           var tag: String = "", var baseUrl: String = "",
                           var file: File = File(""), var files: ArrayList<File> = arrayListOf(),
                           var headers: HttpHeaders = HttpHeaders(), var fileKey: String = "file", var fileKeys: String = "files"
    ) : Serializable
}