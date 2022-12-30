package org.codecraftlabs.mockito.inject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ArticleManagerTest {
    @Mock
    private ArticleDatabase articleDatabase;

    @Mock
    private User user;

    @InjectMocks
    private ArticleManager articleManager;

    @Test
    void ensureInjectMocks() {
        articleManager.initialize();

        verify(articleDatabase).addListener(any(ArticleListener.class));
        verify(articleDatabase).setUser(user);
    }
}
