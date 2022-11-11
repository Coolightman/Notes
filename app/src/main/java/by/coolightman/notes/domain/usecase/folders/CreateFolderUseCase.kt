package by.coolightman.notes.domain.usecase.folders

import by.coolightman.notes.domain.model.Folder
import by.coolightman.notes.domain.repository.FolderRepository
import javax.inject.Inject

class CreateFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository
) {
    suspend operator fun invoke(folder: Folder) = folderRepository.insert(folder)
}