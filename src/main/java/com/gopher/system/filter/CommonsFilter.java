package com.gopher.system.filter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
@WebFilter(urlPatterns = "/*" )
public class CommonsFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger("[PARAM IN]");
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        try {
            logger.info("=============================================================================");
            StringBuffer msg = new StringBuffer(200);
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            msg.append("HOST : ");
            msg.append(request.getRemoteHost());
            msg.append("  ");
            msg.append("URL : ");
            msg.append(httpRequest.getRequestURL());
            msg.append("  ");
            msg.append("METHOD : ");
            msg.append(httpRequest.getMethod());
            msg.append("  ");
            Map<String, String[]> params = request.getParameterMap();
            StringBuffer paramstr = null;
            if (null != params && !params.isEmpty()) {
                paramstr = new StringBuffer(100);
                paramstr.append("{");
                Iterator<String> keys = params.keySet().iterator();
                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    String[] value = params.get(key);
                    StringBuffer val = new StringBuffer(20);
                    if (value != null) {
                        for (int i = 0; i < value.length; i++) {
                            val.append(value[i]);
                        }
                    }
                    paramstr.append(key);
                    paramstr.append(":");
                    paramstr.append(val);
                    paramstr.append(",");
                }
                paramstr.append("}");
            }
            if (null != paramstr) {
                msg.append("PARAM IN ");
                msg.append(paramstr.toString());
            }
            logger.info(msg.toString());
            logger.info("=============================================================================");
        } catch (Throwable t) {
            logger.info(t.getMessage(),t);
        }
        chain.doFilter(request, response);

    }
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    public void destroy() {

    }
}

