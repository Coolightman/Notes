package by.coolightman.notes.domain.usecase.preferences

import by.coolightman.notes.domain.repository.PreferencesRepository
import javax.inject.Inject

class PutBooleanPreferenceUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    suspend operator fun invoke(key: String, value: Boolean) = repository.putBoolean(key, value)
}
