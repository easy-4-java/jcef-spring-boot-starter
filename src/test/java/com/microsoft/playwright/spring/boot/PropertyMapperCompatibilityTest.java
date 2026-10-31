package com.microsoft.playwright.spring.boot;

import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.spring.boot.options.BrowserConnectOptions;
import com.microsoft.playwright.spring.boot.pool.BrowserPagePoolConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PropertyMapperCompatibilityTest {
    @Test
    void skipsNullBrowserOptionsButCopiesConfiguredValues() {
        BrowserType.ConnectOptions defaults = new BrowserConnectOptions()
                .setHeaders(null).setSlowMo(null).setTimeout(null).toOptions();
        assertNull(defaults.headers);
        assertNull(defaults.slowMo);
        assertNull(defaults.timeout);

        BrowserType.ConnectOptions configured = new BrowserConnectOptions()
                .setHeaders(Collections.singletonMap("X-Test", "value"))
                .setSlowMo(25.0).setTimeout(1000.0).toOptions();
        assertEquals("value", configured.headers.get("X-Test"));
        assertEquals(25.0, configured.slowMo);
        assertEquals(1000.0, configured.timeout);
    }

    @Test
    void keepsPoolDefaultsForNullsAndMapsConfiguredDurations() {
        BrowserPagePoolConfig input = new BrowserPagePoolConfig();
        input.setMaxWaitDuration(null);
        GenericObjectPoolConfig<?> defaults = input.toPoolConfig();
        assertEquals(GenericObjectPoolConfig.DEFAULT_MAX_WAIT, defaults.getMaxWaitDuration());

        input.setMaxWaitDuration(Duration.ofSeconds(3));
        assertEquals(Duration.ofSeconds(3), input.toPoolConfig().getMaxWaitDuration());
    }
}
