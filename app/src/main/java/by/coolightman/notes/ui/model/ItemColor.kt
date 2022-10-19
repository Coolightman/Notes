package by.coolightman.notes.ui.model

import by.coolightman.notes.ui.theme.BlueItem
import by.coolightman.notes.ui.theme.GrayItem
import by.coolightman.notes.ui.theme.GreenItem
import by.coolightman.notes.ui.theme.NavyItem
import by.coolightman.notes.ui.theme.RedItem
import by.coolightman.notes.ui.theme.YellowItem

enum class ItemColor(val color: ULong) {
    RED(RedItem.value),
    YELLOW(YellowItem.value),
    GREEN(GreenItem.value),
    NAVY(NavyItem.value),
    BLUE(BlueItem.value),
    GRAY(GrayItem.value)
}
