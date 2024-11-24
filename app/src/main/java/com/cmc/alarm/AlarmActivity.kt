package com.cmc.alarm

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AlarmActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        // Play alarm sound
        mediaPlayer = MediaPlayer.create(this, android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI)
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        // Stop button
        findViewById<Button>(R.id.stop_button).setOnClickListener {
            mediaPlayer.stop()
            finish() // Close the activity
        }

        // Snooze button (optional)
        findViewById<Button>(R.id.snooze_button).setOnClickListener {
            mediaPlayer.stop()
            snoozeAlarm()
            finish() // Close the activity
        }
    }

    private fun snoozeAlarm() {
        // Add snooze logic (e.g., schedule alarm for 5 minutes later)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
        }
        mediaPlayer.release()
    }
}
