package io.github.adsa06.presentation.ui.translations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.github.adsa06.data.repository.SettingsRepository;

public class TranslationManagerTest {

    private TranslationManager translationManager;

    @BeforeEach
    void setUp() {
        SettingsRepository settingsRepository = mock(SettingsRepository.class);
        when(settingsRepository.findSettings()).thenReturn(Locale.ENGLISH);

        translationManager = new TranslationManager(settingsRepository);
    }
    
    @Test
    void shouldReturnEnglishTranslation() {
        assertEquals("Counter: 0", translationManager.getString("counter", 0));
    }

    @Test
    void shouldChangeLocaleAndUpdateBundle() {
        translationManager.setLocale("es");

        assertEquals("Contador: 0", translationManager.getString("counter", 0));
    }
}
