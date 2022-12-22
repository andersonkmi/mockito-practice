package org.codecraftlabs.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServiceDatabaseIdTest {
    @Mock
    private Database databaseMock;

    @Test
    void ensureMockitoReturnsTheConfiguredValue() {
        // define return value for method getUniqueId()
        when(databaseMock.getUniqueId()).thenReturn(42);

        Service service = new Service(databaseMock);
        assertEquals(service.toString(), "Using database with id: 42");
    }
}
