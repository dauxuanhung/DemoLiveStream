package com.example.demolivestream

import android.graphics.SurfaceTexture
import android.util.Log
import android.view.TextureView
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.suspendCancellableCoroutine

data class TextureViewState(val available: Boolean = false, val destroyed: Boolean = false)

suspend fun TextureView.awaitAvailability() =
    callbackFlow {
        if (isAvailable) {
            trySend((TextureViewState(available = true)))
        }

        val listener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(p0: SurfaceTexture, p1: Int, p2: Int) {
                trySend((TextureViewState(available = true)))
            }

            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture, p1: Int, p2: Int) {
            }

            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture): Boolean {
                trySend(((TextureViewState(destroyed = true))))
                return true
            }

            override fun onSurfaceTextureUpdated(p0: SurfaceTexture) {
            }
        }
        surfaceTextureListener = listener
        awaitClose {
            surfaceTextureListener = null
        }
    }