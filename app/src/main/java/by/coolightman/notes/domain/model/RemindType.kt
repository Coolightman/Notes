package by.coolightman.notes.domain.model

enum class RemindType(val minutes: Int) {
    FIVE_MIN(5),
    TEN_MIN(10),
    FIFTEEN_MIN(15),
    TWENTY_MIN(20),
    THIRTY_MIN(30)
}
