package com.dev.productioncenter.controller.command;

public class Router {
    public enum RouterType {
        FORWARD, REDIRECT
    }

    private String page = PagePath.HOME;
    private RouterType type = RouterType.FORWARD;

    public Router(String page, RouterType type) {
        if (page != null) {
            this.page = page;
        }
        if (type != null) {
            this.type = type;
        }
    }

    public String getPage() {
        return page;
    }

    public RouterType getType() {
        return type;
    }
}