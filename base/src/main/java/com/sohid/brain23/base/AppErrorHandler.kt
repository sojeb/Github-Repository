package com.sohid.brain23.base

import android.content.Context
import android.util.Log
import android.widget.Toast

class AppErrorHandler(private val context: Context) : ErrorHandler {

  override fun handleError(error: Throwable) {
    val message = "error: ${error.message}"
    //Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    Log.d(TAG, message)
  }

  companion object {

    private const val TAG = "AppErrorHandler"
  }
}
