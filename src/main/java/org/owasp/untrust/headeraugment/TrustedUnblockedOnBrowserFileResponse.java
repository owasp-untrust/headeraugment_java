package org.owasp.untrust.headeraugment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Documents that a browser-served file response was reviewed as trusted enough
 * not to require the restrictive policy applied by {@link UntrustedFileResponse}.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TrustedUnblockedOnBrowserFileResponse {
    String justification();
}
