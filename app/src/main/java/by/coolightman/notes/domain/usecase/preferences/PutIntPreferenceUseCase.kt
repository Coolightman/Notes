package by.coolightman.notes.domain.usecase.preferences

import by.coolightman.notes.domain.repository.PreferencesRepository
import javax.inject.Inject

class PutIntPreferenceUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    suspend operator fun invoke(key: String, value: Int) = repository.putInt(key, value)
}
