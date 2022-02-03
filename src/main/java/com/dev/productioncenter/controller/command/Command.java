package com.dev.productioncenter.controller.command;

import jakarta.servlet.http.HttpServletRequest;

/**
 * @project Production Center
 * @author YanaV
 * The interface Command.
 */
@FunctionalInterface
public interface Command {
    /**
     * Execute router.
     *
     * @param request the request
     * @return the router
     */
    Router execute(HttpServletRequest request);
}
