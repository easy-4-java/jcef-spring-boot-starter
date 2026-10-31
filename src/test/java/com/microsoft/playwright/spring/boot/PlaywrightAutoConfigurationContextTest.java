package com.microsoft.playwright.spring.boot;

import com.microsoft.playwright.spring.boot.pool.BrowserContextPool;
import com.microsoft.playwright.spring.boot.pool.BrowserContextPooledObjectFactory;
import com.microsoft.playwright.spring.boot.pool.BrowserPagePool;
import com.microsoft.playwright.spring.boot.pool.BrowserPagePooledObjectFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PlaywrightAutoConfigurationContextTest {
    @Test
    void bootFourDiscoversStarterConfiguration() {
        assertTrue(ImportCandidates.load(AutoConfiguration.class, getClass().getClassLoader())
                .getCandidates().contains(PlaywrightAutoConfiguration.class.getName()));
    }

    @Test
    void createsEmptyPoolsByDefaultWithoutLaunchingBrowser() {
        String previousHost = System.getProperty("PLAYWRIGHT_DOWNLOAD_HOST");
        try {
            new ApplicationContextRunner()
                    .withUserConfiguration(PlaywrightAutoConfiguration.class)
                    .run(context -> {
                        assertTrue(context.isRunning());
                        BrowserPagePool pages = context.getBean(BrowserPagePool.class);
                        BrowserContextPool contexts = context.getBean(BrowserContextPool.class);
                        assertEquals(0, pages.getMinIdle());
                        assertEquals(0, contexts.getMinIdle());
                        assertEquals(0, pages.getCreatedCount());
                        assertEquals(0, contexts.getCreatedCount());
                        assertSame(context.getBean(BrowserPagePooledObjectFactory.class), pages.getFactory());
                        assertSame(context.getBean(BrowserContextPooledObjectFactory.class), contexts.getFactory());
                    });
        } finally {
            if (previousHost == null) {
                System.clearProperty("PLAYWRIGHT_DOWNLOAD_HOST");
            } else {
                System.setProperty("PLAYWRIGHT_DOWNLOAD_HOST", previousHost);
            }
        }
    }

    @Test
    void preservesUserSuppliedPoolsAndFactories() {
        PlaywrightProperties properties = new PlaywrightProperties();
        BrowserPagePooledObjectFactory pageFactory = new BrowserPagePooledObjectFactory(properties);
        BrowserContextPooledObjectFactory contextFactory = new BrowserContextPooledObjectFactory(properties);
        BrowserPagePool pagePool = new BrowserPagePool(pageFactory);
        BrowserContextPool contextPool = new BrowserContextPool(contextFactory);
        new ApplicationContextRunner()
                .withUserConfiguration(PlaywrightAutoConfiguration.class)
                .withBean(BrowserPagePooledObjectFactory.class, () -> pageFactory)
                .withBean(BrowserContextPooledObjectFactory.class, () -> contextFactory)
                .withBean(BrowserPagePool.class, () -> pagePool)
                .withBean(BrowserContextPool.class, () -> contextPool)
                .run(context -> {
                    assertTrue(context.isRunning());
                    assertSame(pageFactory, context.getBean(BrowserPagePooledObjectFactory.class));
                    assertSame(contextFactory, context.getBean(BrowserContextPooledObjectFactory.class));
                    assertSame(pagePool, context.getBean(BrowserPagePool.class));
                    assertSame(contextPool, context.getBean(BrowserContextPool.class));
                });
    }
}
