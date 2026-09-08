package org.owasp.untrust.headeraugment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a browser-served file response as reviewed and trusted enough to be
 * exempt from the default restrictive policy.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TrustedUnblockedOnBrowserFileResponse {
    String justification();
}
