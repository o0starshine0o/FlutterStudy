package com.example.flutter_app.ui.contact

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flutter_app.R
import com.example.flutter_app.app.MyApp.Companion.TAG
import com.tencent.imsdk.BaseConstants
import com.tencent.imsdk.v2.V2TIMFriendAddApplication
import com.tencent.imsdk.v2.V2TIMFriendOperationResult
import com.tencent.imsdk.v2.V2TIMManager
import com.tencent.imsdk.v2.V2TIMValueCallback
import com.tencent.qcloud.tim.uikit.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_add_more.*

class AddMoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_more)
    }

    fun add(view: View?) {
        val id: String = user_id.text.toString()
        if (TextUtils.isEmpty(id)) {
            return
        }

        val v2TIMFriendAddApplication = V2TIMFriendAddApplication(id)
        v2TIMFriendAddApplication.setAddWording(add_wording.text.toString())
        v2TIMFriendAddApplication.setAddSource("android")
        V2TIMManager.getFriendshipManager().addFriend(v2TIMFriendAddApplication, object : V2TIMValueCallback<V2TIMFriendOperationResult> {
            override fun onError(code: Int, desc: String) {
                Log.e(TAG, "addFriend err code = $code, desc = $desc")
                ToastUtil.toastShortMessage("Error code = $code, desc = $desc")
            }

            override fun onSuccess(v2TIMFriendOperationResult: V2TIMFriendOperationResult) {
                Log.i(TAG, "addFriend success")
                when (v2TIMFriendOperationResult.resultCode) {
                    BaseConstants.ERR_SUCC -> ToastUtil.toastShortMessage("成功")
                    BaseConstants.ERR_SVR_FRIENDSHIP_INVALID_PARAMETERS -> {
                        if (TextUtils.equals(v2TIMFriendOperationResult.resultInfo, "Err_SNS_FriendAdd_Friend_Exist"))
                            ToastUtil.toastShortMessage("对方已是您的好友")
                        else
                            ToastUtil.toastShortMessage("您的好友数已达系统上限")
                    }
                    BaseConstants.ERR_SVR_FRIENDSHIP_COUNT_LIMIT -> ToastUtil.toastShortMessage("您的好友数已达系统上限")
                    BaseConstants.ERR_SVR_FRIENDSHIP_PEER_FRIEND_LIMIT -> ToastUtil.toastShortMessage("对方的好友数已达系统上限")
                    BaseConstants.ERR_SVR_FRIENDSHIP_IN_SELF_BLACKLIST -> ToastUtil.toastShortMessage("被加好友在自己的黑名单中")
                    BaseConstants.ERR_SVR_FRIENDSHIP_ALLOW_TYPE_DENY_ANY -> ToastUtil.toastShortMessage("对方已禁止加好友")
                    BaseConstants.ERR_SVR_FRIENDSHIP_IN_PEER_BLACKLIST -> ToastUtil.toastShortMessage("您已被被对方设置为黑名单")
                    BaseConstants.ERR_SVR_FRIENDSHIP_ALLOW_TYPE_NEED_CONFIRM -> ToastUtil.toastShortMessage("等待好友审核同意")
                    else -> ToastUtil.toastLongMessage(v2TIMFriendOperationResult.resultCode.toString() + " " + v2TIMFriendOperationResult.resultInfo)
                }
                finish()
            }
        })
    }
}