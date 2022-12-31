package org.codecraftlabs.mockito.mockstatic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    }
}
