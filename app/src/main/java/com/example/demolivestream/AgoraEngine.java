package com.example.demolivestream;

import androidx.annotation.NonNull;

import io.agora.rtc2.RtcEngine;


public class AgoraEngine {

    private RtcEngine mRtcEngine;
    private AgoraRtcHandler mRtcEventHandler = new AgoraRtcHandler();


    public AgoraEngine(@NonNull AppApplication application, String appId) {
        try {
            mRtcEngine = RtcEngine.create(application, appId, mRtcEventHandler);
            mRtcEngine.enableVideo();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public RtcEngine rtcEngine() {
        return mRtcEngine;
    }


    public void registerRtcHandler(RtcEventHandler handler) {
        if (mRtcEventHandler != null) mRtcEventHandler.registerEventHandler(handler);
    }

    public void removeRtcHandler(RtcEventHandler handler) {
        if (mRtcEventHandler != null) mRtcEventHandler.removeEventHandler(handler);
    }

    public void release() {
        if (mRtcEngine != null) RtcEngine.destroy();
    }
}
