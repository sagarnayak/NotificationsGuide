package com.met.android.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class ActionButtonBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            Toast.makeText(
                it,
                "Message here",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}