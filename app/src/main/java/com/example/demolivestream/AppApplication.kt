package com.example.demolivestream

import android.app.Application
import android.util.Log
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine

class AppApplication : Application() {
    private var mAgoraEngine: AgoraEngine? = null
    private val appId = "f4f6e0782b1245db9038b77f0d2bb8a5"
    var rtcEngine: RtcEngine? = null

    companion object {
        lateinit var instance: AppApplication
    }

    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        // Listen for the remote host joining the channel to get the uid of the host.
        override fun onUserJoined(uid: Int, elapsed: Int) {
            Log.e("onUserJoined", "onUserJoined--main")
        }

        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
            Log.e("onJoinChannelSuccess", "onJoinChannelSuccess--main")
        }

        override fun onUserOffline(uid: Int, reason: Int) {
            Log.e("onUserOffline", "onUserOffline--main")
        }

        override fun onLeaveChannel(stats: RtcStats?) {
            super.onLeaveChannel(stats)
            Log.e("onLeaveChannel", "onLeaveChannel--main")
        }
    }

    fun registerRtcHandler(handler: RtcEventHandler?) {
       mAgoraEngine!!.registerRtcHandler(handler)
    }

    fun removeRtcHandler(handler: RtcEventHandler?) {
        mAgoraEngine!!.removeRtcHandler(handler)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        mAgoraEngine = AgoraEngine(this, appId)
        rtcEngine = mAgoraEngine?.rtcEngine()
        Log.e("Main",Thread.currentThread().name)
    }

}