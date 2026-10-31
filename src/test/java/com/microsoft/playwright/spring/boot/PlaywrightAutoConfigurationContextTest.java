package com.microsoft.playwright.spring.boot;

import com.microsoft.playwright.spring.boot.pool.BrowserContextPool;
import com.microsoft.playwright.spring.boot.pool.BrowserContextPooledObjectFactory;
import com.microsoft.playwright.spring.boot.pool.BrowserPagePool;
import com.microsoft.playwright.spring.boot.pool.BrowserPagePooledObjectFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PlaywrightAutoConfigurationContextTest {
    @Test
    void bootFourDiscoversStarterConfiguration() {
        assertTrue(ImportCandidates.load(AutoConfiguration.class, getClass().getClassLoader())
                .getCandidates().contains(PlaywrightAutoConfiguration.class.getName()));
    }

    @Test
    void startsWithExistingPoolsWithoutLaunchingBrowser() {
        new ApplicationContextRunner()
                .withUserConfiguration(PlaywrightAutoConfiguration.class)
                .withBean(BrowserPagePool.class, () -> new BrowserPagePool(new BrowserPagePooledObjectFactory(new PlaywrightProperties())))
                .withBean(BrowserContextPool.class, () -> new BrowserContextPool(new BrowserContextPooledObjectFactory(new PlaywrightProperties())))
                .run(context -> {
                    assertTrue(context.isRunning());
                    assertTrue(context.containsBean("browserPooledObjectFactory"));
                    assertTrue(context.containsBean("browserContextPooledObjectFactory"));
                });
    }
}
