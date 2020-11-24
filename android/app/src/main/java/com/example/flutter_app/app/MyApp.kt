package com.example.flutter_app.app

import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.example.flutter_app.analysis.IAnalysis
import com.example.flutter_app.analysis.UmengAnalysis
import com.example.flutter_app.utils.UserSignUtils.SdkAppId
import com.example.flutter_app.utils.UserSignUtils.generalUserSign
import com.tencent.imsdk.v2.V2TIMSDKConfig
import com.tencent.imsdk.v2.V2TIMSDKConfig.V2TIM_LOG_INFO
import com.tencent.qcloud.tim.uikit.TUIKit
import com.tencent.qcloud.tim.uikit.base.IMEventListener
import com.tencent.qcloud.tim.uikit.base.IUIKitCallBack
import com.tencent.qcloud.tim.uikit.config.CustomFaceConfig
import com.tencent.qcloud.tim.uikit.config.GeneralConfig
import io.flutter.app.FlutterApplication
import kotlin.concurrent.thread


class MyApp : FlutterApplication() {
    companion object {
        public const val TAG = "FlutterDemo"
    }

    override fun onCreate() {
        super.onCreate()

        initInUiThread()

        thread(name = "init") { asyncInit() }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    private fun initInUiThread() {
        analysis(UmengAnalysis())
        // 腾讯IM必须在UI线程初始化,因为会创建View
        tencentIm()
    }

    private fun asyncInit() {
        asyncAnalysis(UmengAnalysis())
    }

    private fun analysis(analysis: IAnalysis) = analysis.init()
    private fun asyncAnalysis(analysis: IAnalysis) = analysis.asyncInit()

    private fun tencentIm() = TUIKit.getConfigs().apply {
        val userId = "123321"
        val userSign = generalUserSign(userId)
        sdkConfig = V2TIMSDKConfig().apply { setLogLevel(V2TIM_LOG_INFO) }
        customFaceConfig = CustomFaceConfig()
        generalConfig = GeneralConfig().apply {
            this.userId = userId
            userSig = userSign
            userNickname = "测试:星"
            userFaceUrl = "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKzp1slRgwRorEyB0QoY3BexUqT48Uz0MMibYmgYvAMbG9JqF40LqcibL4G9ibyscFkCQ5oISmLo177g/132"
        }
        TUIKit.init(this@MyApp, SdkAppId, this)

        TUIKit.login(userId, userSign, object : IUIKitCallBack {
            override fun onSuccess(data: Any?) = Unit.apply { Log.i(TAG, "user login onSuccess: $data") }
            override fun onError(module: String?, errCode: Int, errMsg: String?) = Unit.apply { Log.e(TAG, "user login onError($module)[$errCode]: $errMsg") }
        })

        TUIKit.addIMEventListener(object : IMEventListener() {})
    }

}