package org.owasp.untrust.headeraugment;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

final class UntrustedFileResponseInterceptorTest {
    private final UntrustedFileResponseInterceptor interceptor =
            new UntrustedFileResponseInterceptor();

    @Test
    void appliesRestrictiveHeadersToAnUnannotatedResourceHandler() throws Exception {
        MockHttpServletResponse response = applyHeadersFor("unannotated");

        assertEquals(
                "sandbox; default-src 'none'; base-uri 'none'; form-action 'none'",
                response.getHeader("Content-Security-Policy"));
        assertEquals("nosniff", response.getHeader("X-Content-Type-Options"));
    }

    @Test
    void appliesRestrictiveHeadersToAResourceResponseEntityHandler() throws Exception {
        MockHttpServletResponse response = applyHeadersFor("responseEntity");

        assertEquals(
                "sandbox; default-src 'none'; base-uri 'none'; form-action 'none'",
                response.getHeader("Content-Security-Policy"));
        assertEquals("nosniff", response.getHeader("X-Content-Type-Options"));
    }

    @Test
    void doesNotApplyRestrictiveHeadersToANonResourceHandler() throws Exception {
        MockHttpServletResponse response = applyHeadersFor("json");

        assertNull(response.getHeader("Content-Security-Policy"));
        assertNull(response.getHeader("X-Content-Type-Options"));
    }

    @Test
    void doesNotApplyRestrictiveHeadersToAnExplicitlyTrustedHandler() throws Exception {
        MockHttpServletResponse response = applyHeadersFor("trusted");

        assertNull(response.getHeader("Content-Security-Policy"));
        assertNull(response.getHeader("X-Content-Type-Options"));
    }

    private MockHttpServletResponse applyHeadersFor(String methodName) throws Exception {
        Method method = TestController.class.getDeclaredMethod(methodName);
        MockHttpServletResponse response = new MockHttpServletResponse();
        interceptor.preHandle(
                new MockHttpServletRequest(),
                response,
                new HandlerMethod(new TestController(), method));
        return response;
    }

    private static final class TestController {
        Resource unannotated() {
            return new ByteArrayResource(new byte[0]);
        }

        ResponseEntity<Resource> responseEntity() {
            return ResponseEntity.ok(new ByteArrayResource(new byte[0]));
        }

        String json() {
            return "{}";
        }

        @TrustedUnblockedOnBrowserFileResponse(justification = "Fixed server-owned test content.")
        Resource trusted() {
            return new ByteArrayResource(new byte[0]);
        }
    }
}
