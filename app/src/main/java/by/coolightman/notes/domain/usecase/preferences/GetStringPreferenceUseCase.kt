package by.coolightman.notes.domain.usecase.preferences

import by.coolightman.notes.domain.repository.PreferencesRepository
import javax.inject.Inject

class GetStringPreferenceUseCase @Inject constructor(
    private val repository: PreferencesRepository
) {
    operator fun invoke(key: String) = repository.getString(key)
}