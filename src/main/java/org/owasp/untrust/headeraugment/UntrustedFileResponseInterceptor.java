// TODO: Should only change headers if they are not manually set to something else
// So should check if there's already a nosniff policy in place
// Questions is will SPRING's default (to put nosniff) happen before this interceptor
// works and can I ensure it comes after? Or check if SPRING is about
// to set it and then not set it? Or just check if it's already set and don't override it?
package org.owasp.untrust.headeraugment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

public final class UntrustedFileResponseInterceptor
        implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) {

        if (!(handler instanceof HandlerMethod method)) {
            return true;
        }

        boolean annotated =
                AnnotatedElementUtils.hasAnnotation(
                        method.getMethod(),
                        UntrustedFileResponse.class)
                || AnnotatedElementUtils.hasAnnotation(
                        method.getBeanType(),
                        UntrustedFileResponse.class);

        if (annotated) {
            response.setHeader(
                    "Content-Security-Policy",
                    "sandbox; default-src 'none'; base-uri 'none'; form-action 'none'");
            response.setHeader("X-Content-Type-Options", "nosniff");
        }

        return true;
    }
}
