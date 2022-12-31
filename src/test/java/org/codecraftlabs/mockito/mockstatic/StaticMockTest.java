package org.codecraftlabs.mockito.mockstatic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
public class StaticMockTest {
    static class Dummy {
        static String var1 = null;

        static String foo() {
            return "foo";
        }

        static void fooVoid(String var2) {
            var1 = var2;
        }
    }

    @Test
    void testStaticMockSimple() {
        assertEquals("foo", Dummy.foo());
        try (MockedStatic<Dummy> ignored = mockStatic(Dummy.class)) {
            assertNull(Dummy.foo());
        }
        assertEquals("foo", Dummy.foo());
    }
}
