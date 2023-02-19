package com.example.demolivestream

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.demolivestream.databinding.TestActivityBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.suspendCoroutine

class TestActivity : AppCompatActivity() {
    var job: Job? = null
    private lateinit var testBinding: TestActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        testBinding = TestActivityBinding.inflate(LayoutInflater.from(this))
        setContentView(testBinding.root)
        lifecycleScope.launch {
            val result = syncClick()
            Log.e("result", result)
        }
    }

    suspend fun syncClick(): String = suspendCancellableCoroutine { cont ->
        testBinding.bt.setOnClickListener {
            cont.resumeWith(Result.success("Clicked"))
        }
        cont.invokeOnCancellation {
            Log.e("ASd", "ASd")
        }
    }
}