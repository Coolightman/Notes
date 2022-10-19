package by.coolightman.notes.util

import by.coolightman.notes.ui.model.ItemColor

fun List<Boolean>.toPreferenceString(): String {
    val result = StringBuilder()
    this.forEach {
        if (it) result.append(1)
        else result.append(0)
    }
    return result.toString()
}

fun convertPrefStringToFilterSelectionList(prefString: String): List<Boolean> {
    return if (prefString.isNotEmpty()) {
        prefString.toList().map { it.digitToInt() }.map { it != 0 }
    } else {
        ItemColor.values().map { false }
    }
}
