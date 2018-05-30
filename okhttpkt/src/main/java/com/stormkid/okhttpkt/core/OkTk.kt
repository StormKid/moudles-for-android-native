package com.stormkid.okhttpkt.core

import android.util.Log
import com.google.gson.Gson
import com.stormkid.okhttpkt.rule.CallbackRule
import com.stormkid.okhttpkt.rule.ClientRule
import okhttp3.*
import java.io.File

/**
分别启动工厂模式创建更多okhttpclient或者启动单例模式的okhttpclient
@author ke_li
@time 2018/5/9
 */
class OkTk private constructor() {
    /**
     * 是否是工厂模式
     */
    private var isFactory = false
    /**
     * 默认的clientType为单例模式
     */
    private var clientType = SINGLE_CLIENT

    /**
     * 默认获取http对象
     */
    private var clientNetType = HTTP_TYPE

    /**
     * 默认为http请求单例对象
     */
    private var okHttpClient: OkHttpClient = OkHttpClientBuilder.Builder.build().getHttpClient().build()


    /**
     * 设置baseUrl
     */
    private var baseUrl = ""

    /**
     * 设置错误指令，默认不处理
     */
    private var error = "网络链接失效，请检查网络连接"

    companion object {
        /**
         * 获取单例对象
         */
        const val SINGLE_CLIENT = "SINGLE_CLIENT"
        /**
         * 获取工厂对象
         */
        const val FACTORY_CLIENT = "FACTORY_CLIENT"

        /**
         * 获取http请求的OkHttpclient对象
         */
        const val HTTP_TYPE = "HTTP"
        /**
         * 获取https请求的OkHttpclient对象
         */
        const val HTTPS_TYPE = "HTTPS"

        /**
         * 获取自定义OkHttpclient对象
         */
        const val COMMOM_TYPE = "COMMOM_TYPE"

        @JvmStatic
        val instance by lazy { OkTk() }


    }


    /**
     * 设置获取的okhttpclient
     */
    fun setClientType(type: String): OkTk {
        if (type == FACTORY_CLIENT || type == SINGLE_CLIENT)
            this.clientType = type
        else this.clientType = SINGLE_CLIENT
        return this
    }

    /**
     * 设置请求方式，http请求，https请求或者自定义全局方式
     */
    fun setNetClientType(type: String): OkTk {
        if (type == HTTPS_TYPE || type == HTTP_TYPE || type == COMMOM_TYPE)
            this.clientNetType = type
        else this.clientType = HTTP_TYPE
        return this
    }

    /**
     *  可调用不init采取默认调用
     */
    fun initHttpClient() {
        initNetType(OkHttpClientBuilder.Builder.build())
        when (clientType) {
            FACTORY_CLIENT -> isFactory = true
        }
    }

    /**
     * 获取相应的对象
     */
    private fun getHttpClient(): OkHttpClient {
        if (isFactory) initNetType(OkHttpClientBuilder.Builder.build())
        return okHttpClient
    }

    /**
     * 更新头部布局
     */
    fun initHead(map: HashMap<String, String>): OkTk {
        initNetType(OkHttpClientBuilder.Builder.setHead(map).build())
        return this
    }


    private fun initNetType(clientRule: ClientRule) {

        when (clientNetType) {
            HTTP_TYPE -> okHttpClient = clientRule.getHttpClient().build()
            HTTPS_TYPE -> okHttpClient = clientRule.getHttpsClient().build()
            COMMOM_TYPE -> okHttpClient = clientRule.getCustomnClient().build()
        }
    }

    /**
     * 全部以long为单位输入请求超时时间
     */
    fun setTimeOut(time: Long): OkTk {
        OkHttpClientBuilder.Builder.build().setTimeOut(time)
        return this
    }

    /**
     * 是否显示log
     */
    fun isLogShow(boolean: Boolean): OkTk {
        OkHttpClientBuilder.Builder.build().isLogShow(boolean)
        return this
    }


    /**
     * 设置主体url
     */
    fun setBase(url: String): OkTk {
        this.baseUrl = url
        return this
    }

    /**
     * 设置错误信息
     */
    fun setErr(err: String): OkTk {
        this.error = err
        return this
    }


    inner class Builder {
        private var url = ""
        private val params = hashMapOf<String, String>()
        private val body = hashMapOf<String, String>()
        private var file = File("")
        private var fileNameKey = ""
        /**
         * 获取单独flag对象
         */
        private var flag = ""

        /**
         * 输入url
         */
        fun setUrl(url: String): Builder {
            this.url = baseUrl + url
            return this
        }

        /**
         * 获取独有的请求标识,多连接的时候进行回调处理
         */
        fun setFlag(flag: String): Builder {
            this.flag = flag
            return this
        }

        /**
         * 输入请求body
         */
        fun putBody(params: HashMap<String, String>): Builder {
            this.body.clear()
            this.body.putAll(params)
            return this
        }

        /**
         * 传入file
         */
        fun putFile(file: File): Builder {
            this.file = file
            return this
        }

        /**
         * 传入fileNameKey
         */
        fun putFileNameKey(key: String): Builder {
            this.fileNameKey = key
            return this
        }


        /**
         * 传入url拼接属性
         */
        fun setParams(params: HashMap<String, String>): Builder {
            this.params.clear()
            this.params.putAll(params)
            return this
        }

        private fun <T> init(callbackRule: CallbackRule<T>): Request.Builder {
            val url = url + initUrl(params)
            val request = Request.Builder().url(url)
            return request
        }


        fun <T> get(call: CallbackRule<T>) {
            val request = init(call).build()
            getHttpClient().newCall(request).enqueue(OkCallback(call, OkCallbackNeed(flag, error)))
        }

        fun <T> post(call: CallbackRule<T>) {
            val request = init(call)
            val requestBody = FormBody.Builder().apply {
                body.forEach { this.add(it.key, it.value) }
            }.build()
            getHttpClient().newCall(request.post(requestBody).build()).enqueue(OkCallback(call, OkCallbackNeed(flag, error)))
        }

        fun <T> postJson(call: CallbackRule<T>) {
            val request = init(call)
            val json = Gson().toJson(body)
            val requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json)
            getHttpClient().newCall(request.post(requestBody).build()).enqueue(OkCallback(call, OkCallbackNeed(flag, error)))
        }

        fun <T> postFile(call: CallbackRule<T>) {
            val request = init(call)
            if (file.exists()) {
                val multipartBody = initFileBody(body).build()
                getHttpClient().newCall(request.post(multipartBody).build()).enqueue(OkCallback(call, OkCallbackNeed(flag, error)))
            }
        }

        private fun initUrl(map: HashMap<String, String>) = "".let {
            var onFirst = false
            var result = ""
            map.forEach {
                if (onFirst) result += ("&${it.key}=${it.value}")
                else {
                    onFirst = true
                    result += ("?${it.key}=${it.value}")
                }
            }
            result
        }

        private fun initFileBody(map: HashMap<String, String>): MultipartBody.Builder = MultipartBody.Builder().setType(MultipartBody.FORM).apply {
            map.forEach {
                this.addFormDataPart(it.key, it.value)
            }
            this.addFormDataPart(fileNameKey, file.name, MultipartBody.create(MultipartBody.FORM, file))
        }
    }


}