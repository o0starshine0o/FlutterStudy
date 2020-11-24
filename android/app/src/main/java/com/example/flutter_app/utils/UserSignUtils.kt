package com.example.flutter_app.utils

import android.text.TextUtils
import android.util.Base64
import org.json.JSONException
import org.json.JSONObject
import java.util.zip.Deflater
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


object UserSignUtils {

    /**
     * 腾讯云 SDKAppId，需要替换为您自己账号下的 SDKAppId。
     *
     *
     * 进入腾讯云云通信[控制台](https://console.cloud.tencent.com/avc ) 创建应用，即可看到 SDKAppId，
     * 它是腾讯云用于区分客户的唯一标识。
     */
    const val SdkAppId = 1400453709


    /**
     * 签名过期时间，建议不要设置的过短
     *
     *
     * 时间单位：秒
     * 默认时间：7 x 24 x 60 x 60 = 604800 = 7 天
     */
    private const val ExpireTime = 604800


    /**
     * 计算签名用的加密密钥，获取步骤如下：
     *
     *
     * step1. 进入腾讯云云通信[控制台](https://console.cloud.tencent.com/avc ) ，如果还没有应用就创建一个，
     * step2. 单击“应用配置”进入基础配置页面，并进一步找到“帐号体系集成”部分。
     * step3. 点击“查看密钥”按钮，就可以看到计算 UserSig 使用的加密的密钥了，请将其拷贝并复制到如下的变量中
     *
     *
     * 注意：该方案仅适用于调试Demo，正式上线前请将 UserSig 计算代码和密钥迁移到您的后台服务器上，以避免加密密钥泄露导致的流量盗用。
     * 文档：https://cloud.tencent.com/document/product/269/32688#Server
     */
    private const val SecretKey = "ec0ba437b6a5150027b10a829face22ac8cdcb8ea089516f0f0933527f27c081"

    /**
     * 计算 UserSig 签名
     *
     *
     * 函数内部使用 HMAC-SHA256 非对称加密算法，对 SDKAPPID、userId 和 EXPIRETIME 进行加密。
     *
     * @note: 请不要将如下代码发布到您的线上正式版本的 App 中，原因如下：
     *
     *
     * 本文件中的代码虽然能够正确计算出 UserSig，但仅适合快速调通 SDK 的基本功能，不适合线上产品，
     * 这是因为客户端代码中的 SECRETKEY 很容易被反编译逆向破解，尤其是 Web 端的代码被破解的难度几乎为零。
     * 一旦您的密钥泄露，攻击者就可以计算出正确的 UserSig 来盗用您的腾讯云流量。
     *
     *
     * 正确的做法是将 UserSig 的计算代码和加密密钥放在您的业务服务器上，然后由 App 按需向您的服务器获取实时算出的 UserSig。
     * 由于破解服务器的成本要高于破解客户端 App，所以服务器计算的方案能够更好地保护您的加密密钥。
     *
     *
     * 文档：https://cloud.tencent.com/document/product/269/32688#Server
     */
    fun generalUserSign(userId: String) = genTLSSignature(SdkAppId.toLong(), userId, ExpireTime.toLong(), null, SecretKey)

    /**
     * 生成 tls 票据
     *
     * @param sdkAppId      应用的 appid
     * @param userId        用户 id
     * @param expire        有效期，单位是秒
     * @param userBuffer       默认填写null
     * @param priKeyContent 生成 tls 票据使用的私钥内容
     * @return 如果出错，会返回为空，或者有异常打印，成功返回有效的票据
     */
    private fun genTLSSignature(sdkAppId: Long, userId: String, expire: Long, userBuffer: ByteArray?, priKeyContent: String): String {
        if (TextUtils.isEmpty(priKeyContent)) {
            return ""
        }
        val currTime = System.currentTimeMillis() / 1000
        val sigDoc = JSONObject()
        try {
            sigDoc.put("TLS.ver", "2.0")
            sigDoc.put("TLS.identifier", userId)
            sigDoc.put("TLS.sdkappid", sdkAppId)
            sigDoc.put("TLS.expire", expire)
            sigDoc.put("TLS.time", currTime)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        var base64UserBuf: String? = null
        if (null != userBuffer) {
            base64UserBuf = Base64.encodeToString(userBuffer, Base64.NO_WRAP)
            try {
                sigDoc.put("TLS.userbuf", base64UserBuf)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        val sig = sha256(sdkAppId, userId, currTime, expire, priKeyContent, base64UserBuf)
        if (sig.isEmpty()) {
            return ""
        }
        try {
            sigDoc.put("TLS.sig", sig)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        val compressor = Deflater()
        compressor.setInput(sigDoc.toString().toByteArray())
        compressor.finish()
        val compressedBytes = ByteArray(2048)
        val compressedBytesLength: Int = compressor.deflate(compressedBytes)
        compressor.end()
        return String(base64EncodeUrl(compressedBytes.copyOfRange(0, compressedBytesLength))!!)
    }


    private fun sha256(sdkAppId: Long, userId: String, currTime: Long, expire: Long, priKeyContent: String, base64UserBuffer: String?): String {
        var contentToBeSigned = "TLS.identifier:$userId\nTLS.sdkappid:$sdkAppId\nTLS.time:$currTime\nTLS.expire:$expire\n"
        if (null != base64UserBuffer) contentToBeSigned += "TLS.userbuf:$base64UserBuffer\n"
        return try {
            val byteKey = priKeyContent.toByteArray()
            val hmac = Mac.getInstance("HmacSHA256")
            val keySpec = SecretKeySpec(byteKey, "HmacSHA256")
            hmac.init(keySpec)
            val byteSig = hmac.doFinal(contentToBeSigned.toByteArray())
            String(Base64.encode(byteSig, Base64.NO_WRAP))
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    private fun base64EncodeUrl(input: ByteArray): ByteArray? {
        val base64 = String(Base64.encode(input, Base64.NO_WRAP)).toByteArray()
        for (i in base64.indices) when (base64[i].toChar()) {
            '+' -> base64[i] = '*'.toByte()
            '/' -> base64[i] = '-'.toByte()
            '=' -> base64[i] = '_'.toByte()
        }
        return base64
    }
}