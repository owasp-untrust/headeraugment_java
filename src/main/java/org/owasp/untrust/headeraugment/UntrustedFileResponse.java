package org.owasp.untrust.headeraugment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Compatibility marker for a response that receives the library's default
 * restrictive browser-file policy.
 *
 * <p>The policy is now applied by default, so this annotation is optional.
 * Use {@link TrustedUnblockedOnBrowserFileResponse} only for a deliberately
 * reviewed exception.</p>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UntrustedFileResponse {
}
