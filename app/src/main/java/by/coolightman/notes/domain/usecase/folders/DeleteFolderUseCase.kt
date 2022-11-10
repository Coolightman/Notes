package by.coolightman.notes.domain.usecase.folders

import by.coolightman.notes.domain.repository.FolderRepository
import by.coolightman.notes.domain.usecase.notes.DeleteNoteUseCase
import javax.inject.Inject

class DeleteFolderUseCase @Inject constructor(
    private val folderRepository: FolderRepository,
    private val getInnerFoldersUseCase: GetInnerFoldersUseCase,
    private val getInnerNotesUseCase: GetInnerNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) {
    suspend operator fun invoke(folderId: Long) {
        deleteFolder(folderId)
    }

    private suspend fun deleteFolder(folderId: Long) {
        getInnerNotesUseCase(folderId).forEach {
            deleteNoteUseCase(it.id)
        }
        getInnerFoldersUseCase(folderId).forEach {
            deleteFolder(it.id)
        }
        folderRepository.delete(folderId)
    }

}