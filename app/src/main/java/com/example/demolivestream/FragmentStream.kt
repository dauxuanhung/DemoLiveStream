package com.example.demolivestream

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.demolivestream.databinding.ItemLiveStreamBinding
import com.google.gson.Gson
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.LeaveChannelOptions
import io.agora.rtc2.video.VideoCanvas
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FragmentStream : Fragment(), RtcEventHandler {
    private var chanelData: ChanelData? = null
    private lateinit var itemLiveStreamBinding: ItemLiveStreamBinding
    private val job: Job? = null
    private val tag = FragmentStream::class.java.name

    companion object {
        private const val KEY_DATA = "data"
        fun getInstance(chanelData: ChanelData) = FragmentStream().apply {
            arguments = bundleOf(KEY_DATA to Gson().toJson(chanelData))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chanelData = Gson().fromJson(arguments?.getString(KEY_DATA), ChanelData::class.java)
        Log.e("onCreate", chanelData?.toString().orEmpty())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    Log.e("launchWhenResumed", "launchWhenResumed")
                    itemLiveStreamBinding.remoteVideoViewContainer.awaitAvailability().collect {
                        if (it.available) {
                            Log.e("available", "available")
                            AppApplication.instance.rtcEngine?.stopPreview()
                            AppApplication.instance.rtcEngine?.leaveChannel(LeaveChannelOptions())
                            val options = ChannelMediaOptions()
                            options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING
                            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
                            AppApplication.instance.rtcEngine?.startPreview()
                            AppApplication.instance.rtcEngine?.joinChannel(
                                chanelData?.token,
                                chanelData?.data,
                                0,
                                options
                            )

                        }
                    }
                }
            }

        }
        Log.e("onViewCreated", "onViewCreated")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.e("onDestroyView", "onDestroyView")
    }

    override fun onDetach() {
        super.onDetach()
        Log.e("onDetach", "onDetach")
    }

    override fun onPause() {
        super.onPause()
        Log.e("onPause","onPause")
        AppApplication.instance.removeRtcHandler(this)

    }

    override fun onResume() {
        super.onResume()
        AppApplication.instance.registerRtcHandler(this)
    }
    override fun onStart() {
        super.onStart()
        Log.e("onStart", "onStart")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        itemLiveStreamBinding =
            ItemLiveStreamBinding.inflate(LayoutInflater.from(context), null, false)
        return itemLiveStreamBinding.root
    }

    override fun onRtcJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {

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
        Log.e("onUserJoin", uid.toString() + "+" + this.getTag().orEmpty())
        AppApplication.instance.rtcEngine?.setupRemoteVideo(
            VideoCanvas(
                itemLiveStreamBinding.remoteVideoViewContainer,
                VideoCanvas.RENDER_MODE_FIT,
                uid
            )
        )
    }

    override fun onLeaveChannel(status: IRtcEngineEventHandler.RtcStats?) {

    }
}