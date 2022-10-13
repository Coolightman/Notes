package by.coolightman.notes.domain.usecase.tasks

import by.coolightman.notes.domain.repository.TaskRepository
import javax.inject.Inject

class SearchTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(key: String) = repository.searchTask(key)
}