package org.codecraftlabs.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ConfigureThreadingUtilTest {
    @Mock
    private MyApplication myApplication;

    @Test
    void ensureThatThreadPoolCanBeConfigured() {
        ConfigureThreadingUtil.configureThreadPool(myApplication);
        verify(myApplication, only()).getNumberOfThreads();
    }
}
