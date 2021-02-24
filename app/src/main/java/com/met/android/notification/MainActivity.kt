package com.met.android.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.met.android.notification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object {
        const val CHANNEL_ID_HIGH_IMPORTANCE = "notification_app_channel_high_importance"

        const val NOTIFICATION_ID = 1

        const val BROADCAST_TRIGGER = "broadcastTrigger"

        const val KEY_TEXT_REPLY = "key_text_reply"
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
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)) {
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
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    fun tapAction() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_HIGH_IMPORTANCE)
            .setSmallIcon(R.drawable.bell)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    fun actionButtons() {
        val intent = Intent(this, ActionButtonBroadcast::class.java).apply {
            action = BROADCAST_TRIGGER
        }
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID_HIGH_IMPORTANCE)
            .setSmallIcon(R.drawable.bell)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .addAction(
                R.drawable.emails,
                "Toast",
                pendingIntent
            )
            .addAction(
                R.drawable.emails,
                "Another",
                pendingIntent
            )

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    fun getSilentNotificationForMarketMonitoring(): Notification? {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID_HIGH_IMPORTANCE)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Remittance")
            .setContentText("Remittance")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true)
            .setAutoCancel(false)
            .setNotificationSilent()

        return builder.build()
    }

    fun replyButton() {
        /*var replyLabel: String = "reply"
        var remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
            setLabel(replyLabel)
            build()
        }

        var replyPendingIntent: PendingIntent =
            PendingIntent.getBroadcast(applicationContext,
             123,
             ,
                PendingIntent.FLAG_UPDATE_CURRENT)

        var action: NotificationCompat.Action =
            NotificationCompat.Action.Builder(R.drawable.ic_reply_icon,
                getString(R.string.label), replyPendingIntent)
                .addRemoteInput(remoteInput)
                .build()

        // Build the notification and add the action.
        val newMessageNotification = Notification.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_message)
            .setContentTitle(getString(R.string.title))
            .setContentText(getString(R.string.content))
            .addAction(action)
            .build()

        with(NotificationManagerCompat.from(this)) {
            notificationManager.notify(notificationId, newMessageNotification)
        }*/
    }
}
