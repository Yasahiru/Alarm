package com.cmc.alarm

import android.app.AlarmManager
import android.provider.Settings
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.ImageView
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val rv = findViewById<RecyclerView>(R.id.rv)

        val alarms = mutableListOf(
            Alarm("06:30", true),
            Alarm("08:00", true)
        )

        val adapter = AlarmAdapter(alarms) { alarm, isEnabled ->
            alarm.switsh = isEnabled
            Toast.makeText(
                this,
                "${alarm.time} is ${if (isEnabled) "Enabled" else "Disabled"}",
                Toast.LENGTH_SHORT
            ).show()
        }

        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        val add = findViewById<ImageView>(R.id.add)

        fun scheduleAlarm(hour: Int, min: Int) {
            val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, AlarmReciver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, min)
                set(Calendar.SECOND, 0)
            }

            if (calendar.timeInMillis <= System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }

            alarmManager.setExactAndAllowWhileIdle( AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent )

            Toast.makeText(this, "Alarm set for ${calendar.time}", Toast.LENGTH_SHORT).show()
        }

        add.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                val time = String.format("%02d:%02d", hour, minute)

                val newAlarm = Alarm(time, true)
                alarms.add(newAlarm)
                adapter.notifyItemInserted(alarms.size - 1)

                scheduleAlarm(selectedHour, selectedMinute)

            }, hour, minute, true).show()
        }
    }
}