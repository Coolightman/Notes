package by.coolightman.notes.domain.model

import androidx.annotation.StringRes
import by.coolightman.notes.R

enum class RepeatType(
    @StringRes val text: Int,
    @StringRes val shortText: Int
) {
    NO(R.string.repeat_type_no, R.string.repeat_type_no_short),
    DAY(R.string.repeat_type_day, R.string.repeat_type_day_short),
    WEEK(R.string.repeat_type_week, R.string.repeat_type_week_short),
    MONTH(R.string.repeat_type_month, R.string.repeat_type_month_short),
    YEAR(R.string.repeat_type_year, R.string.repeat_type_year_short)
}
