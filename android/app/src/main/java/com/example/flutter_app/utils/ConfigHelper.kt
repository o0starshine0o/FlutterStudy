package com.example.flutter_app.utils

import android.content.Context
import android.os.Environment
import com.tencent.qcloud.tim.uikit.TUIKit
import com.tencent.qcloud.tim.uikit.config.CustomFaceConfig
import com.tencent.qcloud.tim.uikit.config.GeneralConfig
import com.tencent.qcloud.tim.uikit.config.TUIKitConfigs
import java.io.File

class ConfigHelper {

    fun getConfigs(context: Context): TUIKitConfigs? {
        val config = GeneralConfig()
        // 显示对方是否已读的view将会展示
        config.isShowRead = true
        config.appCacheDir = context.filesDir.path
        if (File(Environment.getExternalStorageDirectory().toString() + "/111222333").exists()) {
            config.isTestEnv = true
        }
        TUIKit.getConfigs().generalConfig = config
        TUIKit.getConfigs().customFaceConfig = initCustomFaceConfig()
        return TUIKit.getConfigs()
    }

    private fun initCustomFaceConfig(): CustomFaceConfig? {
        //创建一个表情组对象
//        CustomFaceGroup faceConfigs = new CustomFaceGroup();
//        //设置表情组每页可显示的表情列数
//        faceConfigs.setPageColumnCount(5);
//        //设置表情组每页可显示的表情行数
//        faceConfigs.setPageRowCount(2);
//        //设置表情组号
//        faceConfigs.setFaceGroupId(1);
//        //设置表情组的主ICON
//        faceConfigs.setFaceIconPath("4349/xx07@2x.png");
//        //设置表情组的名称
//        faceConfigs.setFaceIconName("4350");
//        for (int i = 1; i <= 15; i++) {
//            //创建一个表情对象
//            CustomFaceConfig faceConfig = new CustomFaceConfig();
//            String index = "" + i;
//            if (i < 10)
//                index = "0" + i;
//            //设置表情所在Asset目录下的路径
//            faceConfig.setAssetPath("4349/xx" + index + "@2x.png");
//            //设置表情所名称
//            faceConfig.setFaceName("xx" + index + "@2x");
//            //设置表情宽度
//            faceConfig.setFaceWidth(240);
//            //设置表情高度
//            faceConfig.setFaceHeight(240);
//            faceConfigs.addCustomFace(faceConfig);
//        }
//        groupFaces.add(faceConfigs);
        return null
    }
}