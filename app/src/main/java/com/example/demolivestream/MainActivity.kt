package com.example.demolivestream

import android.content.Intent
import android.graphics.SurfaceTexture
import android.os.Bundle
import android.util.Log
import android.view.SurfaceView
import android.view.TextureView
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demolivestream.databinding.ActivityMainBinding
import io.agora.rtc2.*
import io.agora.rtc2.video.VideoCanvas


class MainActivity : AppCompatActivity(), RtcEventHandler {
    private val appId = "f4f6e0782b1245db9038b77f0d2bb8a5"
    private val tag = MainActivity::class.java.name

    // Fill the channel name.
    private val channelName = "test3"

    // Fill the temp token generated on Agora Console.
    private val token =
        "007eJxTYAhJzvr44kSH+vp2ubrF/1gaWt8+fDHR6KPbwyOGPtdK389SYDBNNk9NNE01MElJSjJJTjO1sEg0Nk02NDZLM08zMUoxS/R7n9wQyMjge8GcmZEBAkF8VoaS1OISYwYGAHB5IvI="

    // An integer that identifies the local user.
    private val uid = 0
    private var isJoined = false


    //SurfaceView to render local video in a Container.
    private var localSurfaceView: TextureView? = null

    //SurfaceView to render Remote video in a Container.
    private var remoteSurfaceView: TextureView? = null

    private var remoteSurfaceView1: TextureView? = null

    // A toggle switch to change the User role.
    // private val audienceRole: Switch? = null
    private lateinit var binding: ActivityMainBinding


    override fun onStart() {
        super.onStart()
        AppApplication.instance.registerRtcHandler(this)
        Log.e("onStartMain", "onStartMain")
    }


    override fun onStop() {
        super.onStop()
        Log.e("onStop","onStopMain")
    }

    override fun onPause() {
        super.onPause()
        AppApplication.instance.removeRtcHandler(this)
        Log.e("onPauseMain", "onPauseMain")
    }

    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        // Listen for the remote host joining the channel to get the uid of the host.
        override fun onUserJoined(uid: Int, elapsed: Int) {
            if (uid == 726367602) {
                runOnUiThread { setupRemoteVideo(uid) }
            }
            if (uid == -352179401) {
                runOnUiThread { setupRemoteVideo1(uid) }
            }
            Log.e(tag, uid.toString())
        }


        override fun onLeaveChannel(stats: RtcStats?) {
            super.onLeaveChannel(stats)
            Log.e(tag, "onLeaveChannel")
        }

        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
            Log.e(tag, "onJoinChannelSuccess")
            isJoined = true
        }


        override fun onUserOffline(uid: Int, reason: Int) {
            Log.e(tag, "onUserOffline")
            runOnUiThread { remoteSurfaceView!!.visibility = View.GONE }
        }

        override fun onError(err: Int) {
            super.onError(err)
            Log.e(tag, err.toString())
        }
    }
    private var agoraEngine: RtcEngine? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // If all the permissions are granted, initialize the RtcEngine object and join a channel.
        setupVideoSDKEngine()
    }

    private fun setupVideoSDKEngine() {
        try {
            Log.e("Main1111", Thread.currentThread().name)
            agoraEngine = AppApplication.instance.rtcEngine
          //  agoraEngine?.addHandler(mRtcEventHandler)
            // By default, the video module is disabled, call enableVideo to enable it.
        } catch (e: Exception) {

        }
    }

    private fun setupRemoteVideo(uid: Int) {
        remoteSurfaceView = TextureView(baseContext)
        remoteSurfaceView?.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
                Log.e(tag, "onSurfaceTextureAvailable")
            }

            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {
                Log.e(tag, "onSurfaceTextureSizeChanged")
            }

            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
                Log.e(tag, "onSurfaceTextureDestroyed")
                return true
            }

            override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
                Log.e(tag, "onSurfaceTextureUpdated")
            }
        }
        binding.remoteVideoViewContainer.addView(remoteSurfaceView)
        agoraEngine!!.setupRemoteVideo(
            VideoCanvas(
                remoteSurfaceView,
                VideoCanvas.RENDER_MODE_HIDDEN,
                uid
            )
        )
        // Display RemoteSurfaceView.
        remoteSurfaceView?.setVisibility(View.VISIBLE)
    }


    private fun setupRemoteVideo1(uid: Int) {
        remoteSurfaceView1 = TextureView(baseContext)
        remoteSurfaceView1?.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
                Log.e(tag, "onSurfaceTextureAvailable1111")
            }

            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {
                Log.e(tag, "onSurfaceTextureSizeChanged111")
            }

            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
                Log.e(tag, "onSurfaceTextureDestroyed1111")
                return true
            }

            override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
                Log.e(tag, "onSurfaceTextureUpdated111")
            }
        }
        binding.remoteVideoViewContainer1.addView(remoteSurfaceView1)
        agoraEngine!!.setupRemoteVideo(
            VideoCanvas(
                remoteSurfaceView1,
                VideoCanvas.RENDER_MODE_FIT,
                uid
            )
        )
        // Display RemoteSurfaceView.
        remoteSurfaceView1?.setVisibility(View.VISIBLE)
    }

    private fun setupLocalVideo() {
        // Create a SurfaceView object and add it as a child to the FrameLayout.
        localSurfaceView = TextureView(baseContext)
        localSurfaceView?.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
                Log.e("localSurfaceView", "onSurfaceTextureAvailable")
            }

            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {
                Log.e("localSurfaceView", "onSurfaceTextureSizeChanged")
            }

            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
                Log.e("localSurfaceView", "onSurfaceTextureDestroyed")
                return true
            }

            override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
                Log.e("localSurfaceView", "onSurfaceTextureUpdated")
            }
        }
        binding.localVideoViewContainer.addView(localSurfaceView)
        // Pass the SurfaceView object to Agora so that it renders the local video.
        agoraEngine!!.setupLocalVideo(
            VideoCanvas(
                localSurfaceView,
                VideoCanvas.RENDER_MODE_HIDDEN,
                0
            )
        )
    }


    fun joinChannel(view: View?) {
//        if (true) {
//            val options = ChannelMediaOptions()
//            // For Live Streaming, set the channel profile as LIVE_BROADCASTING.
//            options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING
//            // Set the client role as BROADCASTER or AUDIENCE according to the scenario.
//            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
//            // Display LocalSurfaceView.
//            setupLocalVideo()
//            localSurfaceView!!.visibility = View.VISIBLE
//            // Start local preview.
//            agoraEngine!!.startPreview()
//            agoraEngine!!.joinChannel(
//                "007eJxTYAhJzvr44kSH+vp2ubrF/1gaWt8+fDHR6KPbwyOGPtdK389SYDBNNk9NNE01MElJSjJJTjO1sEg0Nk02NDZLM08zMUoxS/R7n9wQyMjge8GcmZEBAkF8VoaS1OISYwYGAHB5IvI=",
//                "test3",
//                1,
//                options
//            )
//            // Disable the switch
//            // Join the channel with a temp token.
//            // You need to specify the user ID yourself, and ensure that it is unique in the channel.
//            //agoraEngine!!.joinChannel(token, channelName, uid, options)
//        } else {
//            Toast.makeText(applicationContext, "Permissions was not granted", Toast.LENGTH_SHORT)
//                .show()
////        }
//        }
        startActivity(Intent(this, ListStreamActivity::class.java))

    }


    override fun onRtcJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
        Log.e("onRtcJoinChannelSuccess", uid.toString())

    }

    override fun onRtcRemoteVideoStateChanged(uid: Int, state: Int, reason: Int, elapsed: Int) {
    }

    override fun onRtcStats(stats: IRtcEngineEventHandler.RtcStats?) {
    }

    override fun onRtcChannelMediaRelayStateChanged(state: Int, code: Int) {
    }

    override fun onRtcChannelMediaRelayEvent(code: Int) {
    }

    override fun onRtcAudioVolumeIndication(
        speakers: Array<out IRtcEngineEventHandler.AudioVolumeInfo>?,
        totalVolume: Int
    ) {
    }

    override fun onRtcAudioRouteChanged(routing: Int) {
    }

    override fun onUserJoin(uid: Int) {
        Log.e("onUserJoinMain", uid.toString())
        runOnUiThread { setupRemoteVideo(uid) }
    }

    override fun onLeaveChannel(status: IRtcEngineEventHandler.RtcStats?) {
    }
}