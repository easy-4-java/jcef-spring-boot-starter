package com.microsoft.playwright.spring.boot.pool;

import com.microsoft.playwright.BrowserContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

@Slf4j
public class JxBrowserPool extends GenericObjectPool<BrowserContext> {

    public JxBrowserPool(PooledObjectFactory<BrowserContext> factory) {
        super(factory);
    }

    public JxBrowserPool(PooledObjectFactory<BrowserContext> factory, GenericObjectPoolConfig<BrowserContext> config) {
        super(factory, config);
    }

    public JxBrowserPool(PooledObjectFactory<BrowserContext> factory, GenericObjectPoolConfig<BrowserContext> config, AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }
}
