package com.example.anirban.accessibilitybrowser

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat

/**
 * Created by: anirban on 30/11/17.
 */

const val PACKAGE_NAME_TO_TRIGGER = "com.infinitestudent"

fun sendUpdateAppNotification(context: Context, messageBody: String, packageManager: PackageManager) {


    val intent =
            packageManager.getLaunchIntentForPackage(PACKAGE_NAME_TO_TRIGGER) ?:
                    Intent(android.content.Intent.ACTION_VIEW,
                            android.net.Uri.parse("http://play.google.com/store/apps/details?id=" + PACKAGE_NAME_TO_TRIGGER))




    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

    setBigTextNotificationBuilder(context, intent, "Open App", messageBody,
            "Notify")
}

fun setBigTextNotificationBuilder(mContext: Context, mIntent: Intent, mTitle: String, message: String,
                                  notificationType: String) {

    val mPendingIntent = PendingIntent.getActivity(mContext, 10,
            mIntent, PendingIntent.FLAG_UPDATE_CURRENT)

    val builder = NotificationCompat.Builder(mContext, notificationType)
            .setContentIntent(mPendingIntent)
            .setColor(ContextCompat.getColor(mContext, R.color.notification_icon))
            .setSmallIcon(android.R.drawable.sym_def_app_icon)
            .setContentTitle(mTitle)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setContentText(message)

    val mNotificationManager = mContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setAutoCancel(true)

    val notification = builder.build()
    notification.flags = Notification.FLAG_AUTO_CANCEL

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        mNotificationManager.createNotificationChannel(NotificationChannel(
                notificationType, "Notify", NotificationManager.IMPORTANCE_HIGH).apply {
            lightColor = Color.BLUE
            enableLights(true)
            enableVibration(true)
        })
    }

    mNotificationManager.notify(10, notification)
}


