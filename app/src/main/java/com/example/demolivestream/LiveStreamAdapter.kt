package com.example.demolivestream

import android.graphics.SurfaceTexture
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.TextureView.SurfaceTextureListener
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.demolivestream.databinding.ItemLiveStreamBinding
import io.agora.rtc2.ChannelMediaOptions
import io.agora.rtc2.Constants
import io.agora.rtc2.IRtcEngineEventHandler
import io.agora.rtc2.RtcEngine
import io.agora.rtc2.video.VideoCanvas

class LiveStreamAdapter(val rtcEngine: RtcEngine?, val listChanel: List<ChanelData>) :

    RecyclerView.Adapter<LiveStreamAdapter.LiveStreamHolder>() {

    class LiveStreamHolder(val binding: ItemLiveStreamBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveStreamHolder {
        return LiveStreamHolder(
            ItemLiveStreamBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listChanel.size
    }


    override fun onBindViewHolder(holder: LiveStreamHolder, position: Int) {
        val myTexture = TextureView(holder.binding.root.context)
        if (position == 0) {
            holder.binding.remoteVideoViewContainer.setBackgroundColor(
                ContextCompat.getColor(
                    holder.binding.root.context,
                    R.color.purple_500
                )
            )
        } else
            if (position == 1) {
                holder.binding.remoteVideoViewContainer.setBackgroundColor(
                    ContextCompat.getColor(
                        holder.binding.root.context,
                        com.google.android.material.R.color.m3_ref_palette_dynamic_neutral10
                    )
                )
            } else {

            }
        myTexture.surfaceTextureListener = object : SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
                Log.e("onSurfaceTextureAvailable", holder.absoluteAdapterPosition.toString())
                val options = ChannelMediaOptions()
                options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING
                options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
                rtcEngine!!.startPreview()
                rtcEngine.addHandler(object : IRtcEngineEventHandler() {
                    override fun onUserJoined(uid: Int, elapsed: Int) {
                        Log.e("onUserJoined", holder.absoluteAdapterPosition.toString())
                        super.onUserJoined(uid, elapsed)
                        rtcEngine!!.setupRemoteVideo(
                            VideoCanvas(
                                myTexture,
                                VideoCanvas.RENDER_MODE_FIT,
                                uid
                            )
                        )
                        // Display RemoteSurfaceView.
                        myTexture?.setVisibility(View.VISIBLE)
                    }

                    override fun onError(err: Int) {
                        super.onError(err)
                        Log.e("onError", holder.absoluteAdapterPosition.toString())
                    }
                })
                rtcEngine?.leaveChannel()
                rtcEngine.joinChannel(
                    listChanel[holder.absoluteAdapterPosition].token,
                    listChanel[holder.absoluteAdapterPosition].data,
                    0,
                    options
                )

            }

            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {
                Log.e("onSurfaceTextureSizeChanged", holder.absoluteAdapterPosition.toString())
            }

            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
                Log.e("onSurfaceTextureDestroyed", "onSurfaceTextureDestroyed")
                rtcEngine?.stopPreview()
                rtcEngine?.leaveChannel()
                return true
            }

            override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
                Log.e("onSurfaceTextureUpdated", holder.absoluteAdapterPosition.toString())
            }

        }

    }

}