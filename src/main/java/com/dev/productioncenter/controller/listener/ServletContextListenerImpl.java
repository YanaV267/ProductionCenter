package com.dev.productioncenter.controller.listener;

import com.dev.productioncenter.model.connection.ConnectionPool;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;

/**
 * @project Production Center
 * @author YanaV
 * The type Servlet context listener.
 */
@WebListener
public class ServletContextListenerImpl implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyPool();
    }
}
