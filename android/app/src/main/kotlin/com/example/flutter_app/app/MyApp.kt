package com.example.flutter_app.app

import com.example.flutter_app.analysis.IAnalysis
import com.example.flutter_app.analysis.UmengAnalysis
import io.flutter.app.FlutterApplication
import kotlin.concurrent.thread

class MyApp : FlutterApplication() {

    override fun onCreate() {
        super.onCreate()

        initInUiThread()
        
        thread(name = "init") { asyncInit() }
    }

    private fun initInUiThread() {
        analysis(UmengAnalysis())
    }

    private fun asyncInit() {
        asyncAnalysis(UmengAnalysis())
    }

    private fun analysis(analysis: IAnalysis) = analysis.init()
    private fun asyncAnalysis(analysis: IAnalysis) = analysis.asyncInit()
}