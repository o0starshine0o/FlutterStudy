package com.example.flutter_app.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.net.Uri

class C : ContentProvider() {
    companion object {
        @JvmStatic
        lateinit var c: Context
    }

    override fun onCreate() = true.apply { context?.apply { c = this } }
    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Nothing? = null
    override fun getType(uri: Uri): Nothing? = null
    override fun insert(uri: Uri, values: ContentValues?): Nothing? = null
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?) = 0
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?) = 0
}