# Header Augmentation

`HeaderAugmentation` adds restrictive response headers by default to Spring MVC
handlers that return Spring `Resource` values, including `ResponseEntity<Resource>`.
This makes browser-served files untrusted unless they are explicitly reviewed and
marked otherwise. JSON, HTML, and other non-resource handlers are unchanged.

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

Responses receive `Content-Security-Policy: sandbox; default-src 'none'; base-uri 'none';
form-action 'none'` and `X-Content-Type-Options: nosniff`. The interceptor sets
these headers before the handler writes its response.

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

This annotation exempts the matching handler (or its controller class) from the
library's default restrictive headers. It does not override a separately
configured response policy.

For local composite-build development:

```kotlin
// settings.gradle.kts
includeBuild("../HeaderAugmentation")
```
"# headeraugment_java" 
