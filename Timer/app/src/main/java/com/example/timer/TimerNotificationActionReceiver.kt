package com.example.timer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.timer.util.NotificationUtil
import com.example.timer.util.PrefUtil

class TimerNotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
    when (intent.action){
        AppConstans.ACTION_STOP -> {
            MainActivity.removeAlarm(context)
            PrefUtil.setTimerState(MainActivity.TimerState.Stopped, context)
            NotificationUtil.hideTimerNotification(context)
        }
        AppConstans.ACTION_PAUSE -> {
            var secondsRemaining = PrefUtil.getSecondsRemaining(context)
            val alarmSetTime = PrefUtil.getAlarmSetTime(context)
            val nowSeconds = MainActivity.nowSeconds

            secondsRemaining -= nowSeconds - alarmSetTime
            PrefUtil.setSecondsRemaining(secondsRemaining, context)

            MainActivity.removeAlarm(context)
            PrefUtil.setTimerState(MainActivity.TimerState.Paused, context)
            NotificationUtil.showTimerPaused(context)
        }
        AppConstans.ACTION_RESUME -> {
            val secondsRemaining = PrefUtil.getSecondsRemaining(context)
            val wakeUpTime = MainActivity.setAlarm(context, MainActivity.nowSeconds, secondsRemaining)
            PrefUtil.setTimerState(MainActivity.TimerState.Running, context)
            NotificationUtil.showTimerRunning(context, wakeUpTime)
        }
        AppConstans.ACTION_START -> {
            val minutesRemaining = PrefUtil.getTimerLength(context)
            val secondsRemaining = minutesRemaining * 60L
            val wakeUpTime = MainActivity.setAlarm(context, MainActivity.nowSeconds, secondsRemaining)
            PrefUtil.setTimerState(MainActivity.TimerState.Running,context)
            PrefUtil.setSecondsRemaining(secondsRemaining, context)
            NotificationUtil.showTimerRunning(context,wakeUpTime)
        }
    }

    }
}