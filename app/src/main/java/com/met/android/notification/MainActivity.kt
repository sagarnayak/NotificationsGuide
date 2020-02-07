package com.met.android.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.met.android.notification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val CHANNEL_ID_HIGH_IMPORTANCE = "notification_app_channel_high_importance"

        const val NOTIFICATION_ID = 1
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        binding.context = this

        setSupportActionBar(binding.toolbar)

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "High Importance"
            val descriptionText = "this is the high importance notification channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID_HIGH_IMPORTANCE, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun simpleNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID_HIGH_IMPORTANCE)
            .setSmallIcon(R.drawable.bell)
            .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .setContentTitle("Title")
            .setContentText("This is the content, this is the longer content which might not be shown to the user with a simple notification.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    fun simpleNotificationWithLongContent() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID_HIGH_IMPORTANCE)
            .setSmallIcon(R.drawable.bell)
            .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .setContentTitle("Title")
            .setContentText("This is the content, this is the longer content which might not be shown to the user with a simple notification. but as this is a Bug Style you can see this.")
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(
                    "This is the content, this is the longer content which might not be shown to the user with a simple notification. but as this is a Bug Style you can see this."
                )
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            // notificationId is a unique int for each notification that you must define
            notify(NOTIFICATION_ID, builder.build())
        }
    }
}
