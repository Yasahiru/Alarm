package com.cmc.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast

class AlarmReciver: BroadcastReceiver() {

    override fun onReceive(context: Context,intent: Intent?){

        Toast.makeText(context, "Alarm Triggered!", Toast.LENGTH_LONG).show()

        val mediaPlayer = MediaPlayer.create(context, android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI)
        mediaPlayer.start()?: Log.d("AlarmReceiver", "Failed to play sound")

        val alarmIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(alarmIntent)

    }

}