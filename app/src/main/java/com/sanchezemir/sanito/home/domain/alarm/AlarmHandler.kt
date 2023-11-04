package com.sanchezemir.sanito.home.domain.alarm

import com.sanchezemir.sanito.home.domain.models.Habit

interface AlarmHandler {
    fun setRecurringAlarm(habit: Habit)
    fun cancel(habit: Habit)
}