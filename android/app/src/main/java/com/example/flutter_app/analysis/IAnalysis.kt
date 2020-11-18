package com.example.flutter_app.analysis

interface IAnalysis {
    fun key():String
    fun channel():String
    fun enableLog():Boolean
    fun init()
    fun asyncInit(){}
}