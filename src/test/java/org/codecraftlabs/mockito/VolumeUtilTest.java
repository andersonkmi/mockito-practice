package org.codecraftlabs.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VolumeUtilTest {
    @Mock
    private AudioManager audioManager;

    @Test
    void testNormalRingerIsMaximized() {
        // 1. script mock behaviour
        when(audioManager.getRingerMode()).thenReturn(RINGER_MODE.RINGER_MODE_NORMAL);
        when(audioManager.getStreamMaxVolume()).thenReturn(100);

        // 2. Test the code of interest
        VolumeUtil.maximizeVolume(audioManager);

        // 3. verify that we saw exactly what we wanted.
        verify(audioManager).setStreamVolume(100);
    }

    @Test
    void testSilentRingerIsNotDisturbed() {
        // 1. Script mock behaviour
        AudioManager audioManager = mock(AudioManager.class);
        when(audioManager.getRingerMode()).thenReturn(RINGER_MODE.RINGER_MODE_SILENT);

        // 2. Test the code of interest
        VolumeUtil.maximizeVolume(audioManager);

        // 3. Validate that we saw exactly what we wanted
        verify(audioManager).getRingerMode();
        verifyNoMoreInteractions(audioManager);
    }
}
