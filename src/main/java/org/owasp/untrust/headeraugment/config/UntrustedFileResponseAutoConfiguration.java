package org.owasp.untrust.headeraugment.config;

import org.owasp.untrust.headeraugment.UntrustedFileResponseInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public final class UntrustedFileResponseAutoConfiguration {

    @Bean
    UntrustedFileResponseInterceptor untrustedFileResponseInterceptor() {
        return new UntrustedFileResponseInterceptor();
    }

    @Bean
    WebMvcConfigurer untrustedFileResponseWebConfigurer(
            UntrustedFileResponseInterceptor interceptor) {

        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(interceptor);
            }
        };
    }
}
