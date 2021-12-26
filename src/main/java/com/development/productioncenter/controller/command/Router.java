package com.development.productioncenter.controller.command;

public class Router {
    public enum RouterType {
        FORWARD, REDIRECT
    }

    private final String page;
    private final RouterType type;

    public Router(String page, RouterType type) {
        this.page = page;
        this.type = type;
    }

    public String getPage() {
        return page;
    }

    public RouterType getType() {
        return type;
    }
}