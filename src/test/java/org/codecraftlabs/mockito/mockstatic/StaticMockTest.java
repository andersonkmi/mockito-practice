package org.codecraftlabs.mockito.mockstatic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.exceptions.verification.WantedButNotInvoked;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

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

    @Test
    void testStaticMockWithRealMethodCall() {
        try (MockedStatic<Dummy> dummy = mockStatic(Dummy.class)) {
            dummy.when(Dummy::foo).thenCallRealMethod();
            assertEquals("foo", Dummy.foo());
            dummy.verify(Dummy::foo);
        }
    }

    @Test
    void testStaticMockReset() {
        try (MockedStatic<Dummy> dummy = mockStatic(Dummy.class)) {
            dummy.when(Dummy::foo).thenReturn("bar");
            dummy.reset();
            assertNull(Dummy.foo());
        }
    }

    @Test
    void testStaticMockClear() {
        try (MockedStatic<Dummy> dummy = mockStatic(Dummy.class)) {
            dummy.when(Dummy::foo).thenReturn("bar");
            assertEquals("bar", Dummy.foo());
            dummy.clearInvocations();
            dummy.verifyNoInteractions();
        }
    }

    @Test
    void testStaticMockDoesNotAffectDifferentThread() throws InterruptedException {
        try (MockedStatic<Dummy> dummy = mockStatic(Dummy.class)) {
            dummy.when(Dummy::foo).thenReturn("bar");
            assertEquals("bar", Dummy.foo());
            dummy.verify(Dummy::foo);
            AtomicReference<String> reference = new AtomicReference<>();
            Thread thread = new Thread(() -> reference.set(Dummy.foo()));
            thread.start();
            thread.join();
            assertEquals("foo", reference.get());
            dummy.when(Dummy::foo).thenReturn("bar");
            assertEquals("bar", Dummy.foo());
            dummy.verify(Dummy::foo, times(2));
        }
    }

    @Test
    void testStaticMockCanCoexistWithMockInDifferentThread() throws InterruptedException {
        try (MockedStatic<Dummy> dummy = mockStatic(Dummy.class)) {
            dummy.when(Dummy::foo).thenReturn("bar");
            assertEquals("bar", Dummy.foo());
            dummy.verify(Dummy::foo);
            AtomicReference<String> reference = new AtomicReference<>();
            Thread thread = new Thread(() -> {
                try (MockedStatic<Dummy> dummy2 = mockStatic(Dummy.class)) {
                    dummy2.when(Dummy::foo).thenReturn("qux");
                    reference.set(Dummy.foo());
                }
            });
            thread.start();
            thread.join();
            assertEquals("qux", reference.get());
            dummy.when(Dummy::foo).thenReturn("bar");
            assertEquals("bar", Dummy.foo());
            dummy.verify(Dummy::foo, times(2));
        }
    }

    void testStaticMockMustBeExclusiveInScopeWithinThread() {

        try {
            try (MockedStatic<Dummy> dummy = mockStatic(Dummy.class);
                 MockedStatic<Dummy> duplicate = mockStatic(Dummy.class)) {

                fail("Not supposed to allow duplicates");
            }

        } catch (Exception e) {
            assertEquals(MockitoException.class, e.getClass());
        }
    }

}
