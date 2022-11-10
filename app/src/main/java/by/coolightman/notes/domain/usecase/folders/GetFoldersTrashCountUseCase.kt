package by.coolightman.notes.domain.usecase.folders

import by.coolightman.notes.domain.repository.FolderRepository
import javax.inject.Inject

class GetFoldersTrashCountUseCase @Inject constructor(
    private val repository: FolderRepository
) {
    operator fun invoke() = repository.getTrashCount()
}
