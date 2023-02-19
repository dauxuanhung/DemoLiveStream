package com.example.demolivestream

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.example.demolivestream.databinding.ActivityListStreamBinding
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.RtcEngineConfig

class ListStreamActivity : AppCompatActivity() {
    private val appId = "5c7ea5e04dbb4cf588a35c136f7f42d6"

    // Fill the channel name.
    private val channelName = "test"

    // Fill the temp token generated on Agora Console.
    private val token =
        "007eJxTYLielmqe3flNLW/RY6XUteL/HyY4pxXfX3ry6bQpLG+VFOoVGEyTzVMTTVMNTFKSkkyS00wtLBKNTZMNjc3SzNNMjFLMytnfJTcEMjIIVs5kZmSAQBCfhaEktbiEgQEAp10gWQ=="
    private lateinit var listStreamBinding: ActivityListStreamBinding

    override fun onDestroy() {
        super.onDestroy()
        Log.e("onDestroy", "onDestroy")
    }

    override fun finish() {
        super.finish()
        Log.e("finish", "finish")
    }

    private var agoraEngine: RtcEngine? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        listStreamBinding = ActivityListStreamBinding.inflate(LayoutInflater.from(this))
        setContentView(listStreamBinding.root)
        //setupVideoSDKEngine()
        listStreamBinding.viewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                Log.e("onPageScrollStateChanged", state.toString())
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                Log.e("onPageScrolled", position.toString())
                Log.e("positionOffset", positionOffset.toString())
                Log.e("positionOffsetPixels", positionOffsetPixels.toString())
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.e("onPageSelected", position.toString())
            }
        })
        listStreamBinding.viewPager.adapter = ViewPagerAdapter(
            arrayListOf(
                ChanelData(
                    "test3",
                    "007eJxTYAhJzvr44kSH+vp2ubrF/1gaWt8+fDHR6KPbwyOGPtdK389SYDBNNk9NNE01MElJSjJJTjO1sEg0Nk02NDZLM08zMUoxS/R7n9wQyMjge8GcmZEBAkF8VoaS1OISYwYGAHB5IvI="
                ),
                ChanelData(
                    "test4",
                    "007eJxTYAgoE+mMnK0RIP3TZHeR3Ikp3knbsn1vObjuE/rpfCDB67MCg2myeWqiaaqBSUpSkklymqmFRaKxabKhsVmaeZqJUYrZG7/3yQ2BjAxborNZGBkgEMRnZShJLS4xYWAAADM3H7M="
                ),
                ChanelData(
                    "test4",
                    "007eJxTYAgoE+mMnK0RIP3TZHeR3Ikp3knbsn1vObjuE/rpfCDB67MCg2myeWqiaaqBSUpSkklymqmFRaKxabKhsVmaeZqJUYrZG7/3yQ2BjAxborNZGBkgEMRnZShJLS4xYWAAADM3H7M="
                )
            ), this
        )
        listStreamBinding.viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
    }


    override fun onRestart() {
        super.onRestart()
    }

    private val mRtcEventHandler: IRtcEngineEventHandler = object : IRtcEngineEventHandler() {
        // Listen for the remote host joining the channel to get the uid of the host.
        override fun onUserJoined(uid: Int, elapsed: Int) {
            Log.e("onUserJoined", "onUserJoined")
        }

        override fun onJoinChannelSuccess(channel: String, uid: Int, elapsed: Int) {
        }

        override fun onUserOffline(uid: Int, reason: Int) {

        }
    }


}