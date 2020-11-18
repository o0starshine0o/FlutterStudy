package com.example.flutter_app.analysis

import com.example.flutter_app.BuildConfig
import com.example.flutter_app.provider.C
import com.umeng.commonsdk.UMConfigure
import com.umeng.commonsdk.UMConfigure.DEVICE_TYPE_PHONE

class UmengAnalysis : IAnalysis {
    override fun key() = "5fab918e45b2b751a929443d"
    override fun channel() = "main"
    override fun enableLog() = BuildConfig.DEBUG
    override fun asyncInit() = UMConfigure.init(C.c, key(), channel(), DEVICE_TYPE_PHONE, null)
    override fun init() {
        UMConfigure.setLogEnabled(enableLog())
        UMConfigure.preInit(C.c, key(), channel())
    }

}