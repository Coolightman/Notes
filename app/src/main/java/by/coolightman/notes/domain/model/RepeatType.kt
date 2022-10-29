package by.coolightman.notes.domain.model

import androidx.annotation.StringRes
import by.coolightman.notes.R

enum class RepeatType(@StringRes val text: Int) {
    NO(R.string.repeat_type_no),
    DAY(R.string.repeat_type_day),
    WEEK(R.string.repeat_type_week),
    MONTH(R.string.repeat_type_month),
    YEAR(R.string.repeat_type_year)
}
