package com.cmc.alarm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlarmAdapter(
    private val alarmList: List<Alarm>,
    private val alarmToggle: (Alarm, Boolean) -> Unit
) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val time: TextView = itemView.findViewById(R.id.time)
        val switchButton: Switch = itemView.findViewById(R.id.switsh)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timer, parent, false)
        return AlarmViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = alarmList[position]
        holder.time.text = alarm.time
        holder.switchButton.isChecked = alarm.switsh

        holder.switchButton.setOnCheckedChangeListener { _, isChecked ->
            alarmToggle(alarm, isChecked)
        }
    }

    override fun getItemCount(): Int = alarmList.size

}
