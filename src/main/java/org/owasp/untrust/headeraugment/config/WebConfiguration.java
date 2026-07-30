package org.owasp.untrust.headeraugment.config;

/**
 * Interceptor registration lives in {@link UntrustedFileResponseAutoConfiguration}.
 * This package-private marker keeps the original source location from becoming a
 * second component-scanned registration path.
 */
final class WebConfiguration {
    private WebConfiguration() {
    }
}
