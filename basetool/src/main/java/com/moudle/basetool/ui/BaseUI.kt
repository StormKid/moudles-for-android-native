package com.moudle.basetool.ui

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhy.autolayout.AutoLayoutActivity
import org.simple.eventbus.EventBus
import android.view.inputmethod.InputMethodManager


/**
 * Created by ke_li on 2017/12/15.
 */
abstract class BaseActivity : AutoLayoutActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
        setContentView(getLayout())
        initView()
        initEvent()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @LayoutRes
    protected abstract fun getLayout(): Int

    protected abstract fun initView()

    protected abstract fun initEvent()

    override fun finish() {
        hintKbTwo()
        super.finish()
    }

    private fun hintKbTwo() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isActive && currentFocus != null) {
            if (currentFocus!!.windowToken != null) {
                imm.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
    }

}

/**
 * fragment里面申请权限请在fragment里面添加接口回调
 */
abstract class BaseFragment : Fragment() {

    private var isMeet = true
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayout(), container, false)
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isMeet) {
            initData()
            isMeet = false
        }
    }


    @LayoutRes
    protected abstract fun getLayout(): Int


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        EventBus.getDefault().register(this)
        initView()
        initEvent()
    }

    protected abstract fun initData()
    protected abstract fun initView()
    protected abstract fun initEvent()


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}


