package com.example.noteskotlinroom.enums

import androidx.annotation.StringRes
import com.example.noteskotlinroom.R

enum class DayOfWeek(@StringRes val nameRes: Int) {
    ANY_DAY(R.string.day_any),
    MONDAY(R.string.day_monday),
    TUESDAY(R.string.day_tuesday),
    WEDNESDAY(R.string.day_wednesday),
    THURSDAY(R.string.day_thursday),
    FRIDAY(R.string.day_friday),
    SATURDAY(R.string.day_saturday),
    SUNDAY(R.string.day_sunday);
}