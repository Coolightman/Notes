package by.coolightman.notes.domain.model

sealed class SortNoteBy {
    object Color : SortNoteBy()
    object ColorDesc : SortNoteBy()
    object EditDate : SortNoteBy()
    object EditDateDesc : SortNoteBy()
    object CreateDate : SortNoteBy()
    object CreateDateDesc : SortNoteBy()
    object Title : SortNoteBy()
    object TitleDesc : SortNoteBy()
}
