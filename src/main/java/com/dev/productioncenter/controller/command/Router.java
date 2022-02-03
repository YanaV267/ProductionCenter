package com.dev.productioncenter.controller.command;

/**
 * @project Production Center
 * @author YanaV
 * The type Router.
 */
public class Router {
    /**
     * The enum Router type.
     */
    public enum RouterType {
        /**
         * Forward router type.
         */
        FORWARD,
        /**
         * Redirect router type.
         */
        REDIRECT
    }

    private String page = PagePath.HOME;
    private RouterType type = RouterType.FORWARD;

    /**
     * Instantiates a new Router.
     *
     * @param page the page
     * @param type the type
     */
    public Router(String page, RouterType type) {
        if (page != null) {
            this.page = page;
        }
        if (type != null) {
            this.type = type;
        }
    }

    /**
     * Gets page.
     *
     * @return the page
     */
    public String getPage() {
        return page;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public RouterType getType() {
        return type;
    }
}