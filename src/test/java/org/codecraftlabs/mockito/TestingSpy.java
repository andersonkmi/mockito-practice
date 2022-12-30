package org.codecraftlabs.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class TestingSpy {
    @Spy
    private List<String> list = new ArrayList<>();

    @Test
    void ensureToLearnAboutSpy() {
        int highNumber = 10000000;
        doReturn("Big list").when(list).get(highNumber);
        assertEquals("Big list", list.get(highNumber));
    }
}
