package org.codecraftlabs.mockito.mockstatic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class MyStaticDemoTest {
    @Test
    void testStaticMockVoid() {
        try (MockedStatic<Dummy> dummy = Mockito.mockStatic(Dummy.class)) {
            dummy.when(Dummy::foo).thenReturn("mocked");
            dummy.when(() -> Dummy.foo(anyString())).thenReturn("mockedValue");

            assertEquals("mocked", Dummy.foo());
            assertEquals("mockedValue", Dummy.foo("para"));
            dummy.verify(Dummy::foo);
            dummy.verify(() -> Dummy.foo(anyString()));
        }
    }

    static final class Dummy {
        public int testing() {
            return var1.length();
        }

        static String var1;

        static String foo() {
            return "foo";
        }

        static String foo(String var2) {
            var1 = var2;
            return "SUCCESS";
        }
    }
}
