package com.dev.productioncenter.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * @project Production Center
 * @author YanaV
 * The type Pages tag.
 */
public class PagesTag extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger();
    private int page;
    private String command;
    private boolean isLast;

    /**
     * Sets page.
     *
     * @param page the page
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Sets command.
     *
     * @param command the command
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * Sets last.
     *
     * @param last the last
     */
    public void setLast(boolean last) {
        isLast = last;
    }

    @Override
    public int doStartTag() throws JspException {
        String contextPath = pageContext.getServletContext().getContextPath();
        StringBuilder pageTag = new StringBuilder();
        pageTag.append("<div id=\"pages\">");
        pageTag.append("<input value=\"<\" ");
        pageTag.append("onclick=\"location.href='").append(contextPath);
        pageTag.append("/controller?command=").append(command);
        pageTag.append("&page=").append(page - 1).append("'\" ");
        if (page == 1) {
            pageTag.append("disabled");
        }
        pageTag.append("/><div>").append(page).append("</div>");
        pageTag.append("<input value=\">\" ");
        pageTag.append("onclick=\"location.href='").append(contextPath);
        pageTag.append("/controller?command=").append(command);
        pageTag.append("&page=").append(page + 1).append("'\" ");
        if (isLast) {
            pageTag.append("disabled");
        }
        pageTag.append("/></div>");
        try {
            pageContext.getOut().write(pageTag.toString());
        } catch (IOException exception) {
            LOGGER.error("Error has occurred while writing tag into stream: " + exception);
            throw new JspTagException("Error has occurred while writing tag into stream: ", exception);
        }
        return SKIP_BODY;
    }
}
