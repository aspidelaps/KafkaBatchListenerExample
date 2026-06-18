package org.example.KafkaBatchListenerExample.config;

import io.micrometer.context.ContextRegistry;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracingMdcConfig {

    @PostConstruct
    void init() {
        // Explicitly tells Micrometer to populate Slf4j MDC with trace tracking info
        ContextRegistry.getInstance()
                .registerThreadLocalAccessor(new io.micrometer.context.ThreadLocalAccessor<String>() {
                    @Override
                    public Object key() {
                        return "traceId";
                    }

                    @Override
                    public String getValue() {
                        return org.slf4j.MDC.get("traceId");
                    }

                    @Override
                    public void setValue(String value) {
                        org.slf4j.MDC.put("traceId", value);
                    }

                    @Override
                    public void reset() {
                        org.slf4j.MDC.remove("traceId");
                    }
                });
    }
}

