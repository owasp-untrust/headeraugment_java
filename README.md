# Header Augmentation

`HeaderAugmentation` adds restrictive response headers to Spring MVC handlers
that serve untrusted files.

## Dependency

```kotlin
dependencies {
    implementation("org.owasp.untrust:headeraugmentation:0.1.0")
}
```

Spring Boot discovers the library through
`META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`.
No application component scan or explicit configuration import is needed.
The auto-configuration activates only for Servlet-based Spring web
applications.

## Usage

```java
import org.owasp.untrust.headeraugment.UntrustedFileResponse;

@UntrustedFileResponse
@GetMapping("/image")
public ResponseEntity<Resource> image() {
    // ...
}
```

The annotation can also be placed on a controller class. Matching responses
receive `Content-Security-Policy: sandbox; default-src 'none'; base-uri 'none';
form-action 'none'` and `X-Content-Type-Options: nosniff`. The interceptor sets
these headers before the handler writes its response; a handler that needs a
different security policy should not use this annotation.

## Trusted Browser Files

Use `@TrustedUnblockedOnBrowserFileResponse` only when a browser-served file
has been deliberately reviewed as trusted content. Its required justification
makes that exceptional decision visible in source code.

```java
@TrustedUnblockedOnBrowserFileResponse(
    justification = "The application generates this fixed SVG from server-owned data."
)
@GetMapping("/branding/logo.svg")
public ResponseEntity<Resource> logo() {
    // ...
}
```

The annotation is an audit marker. It does not add headers or override a
separately configured response policy.

For local composite-build development:

```kotlin
// settings.gradle.kts
includeBuild("../HeaderAugmentation")
```
"# headeraugment_java" 
