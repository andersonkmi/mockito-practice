package org.codecraftlabs.mockito.mockstatic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.exceptions.verification.WantedButNotInvoked;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @Test
    void testStaticMockWithVerification() {
        try (MockedStatic<Dummy> dummy = mockStatic(Dummy.class)) {
            dummy.when(Dummy::foo).thenReturn("bar");
            assertEquals("bar", Dummy.foo());
            dummy.verify(Dummy::foo);
        }
    }

    @Test
    void testStaticMockWithVerificationFailed() {
        try (MockedStatic<Dummy> dummy = mockStatic(Dummy.class)) {
            assertThrows(WantedButNotInvoked.class, () -> dummy.verify(Dummy::foo));
        }
    }

    @Test
    void testStaticMockWithNoInteractions() {
        try (MockedStatic<Dummy> dummy = mockStatic(Dummy.class)) {
            dummy.when(Dummy::foo).thenReturn("bar");
            dummy.verifyNoInteractions();
        }
    }

    @Test
    void testStaticMockWithNoInteractionsFailed() {
        try (MockedStatic<Dummy> dummy = mockStatic(Dummy.class)) {
            dummy.when(Dummy::foo).thenReturn("bar");
            assertEquals("bar", Dummy.foo());
            assertThrows(NoInteractionsWanted.class, dummy::verifyNoInteractions);
        }
    }

    @Test
    void testStaticMockWithNoMoreInteractions() {
        try (MockedStatic<Dummy> dummy = mockStatic(Dummy.class)) {
            dummy.when(Dummy::foo).thenReturn("bar");
            assertEquals("bar", Dummy.foo());
            dummy.verify(Dummy::foo);
            dummy.verifyNoMoreInteractions();
        }
    }

    @Test
    void testStaticMockWithNoMoreInteractionsFailed() {
        try (MockedStatic<Dummy> dummy = mockStatic(Dummy.class)) {
            dummy.when(Dummy::foo).thenReturn("bar");
            assertEquals("bar", Dummy.foo());
            assertThrows(NoInteractionsWanted.class, dummy::verifyNoMoreInteractions);
        }
    }

    @Test
    void testStaticMockWithDefaultAnswer() {
        try (MockedStatic<Dummy> dummy = mockStatic(Dummy.class, invocation -> "bar")) {
            assertEquals("bar", Dummy.foo());
            dummy.verify(Dummy::foo);
        }
    }
}
