package com.smupdates.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PropertyLoaderListner implements ServletContextListener{
    private static final Logger LOG = LoggerFactory.getLogger(PropertyLoaderListner.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOG.info("Context Intialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
