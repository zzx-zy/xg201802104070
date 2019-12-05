package filter;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "Filter 3", urlPatterns = {"/*"})
public class Filter3_IsLogined implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Filter 3 - judge begins");
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        //获得请求路径
        String path= httpServletRequest.getRequestURI();
        if (path.contains("/login")){
            System.out.println("直接执行下一个过滤器或原请求");
            //执行其他过滤器，若其他过滤器执行完毕则执行原请求
            chain.doFilter(request,response);
        }else {//若路径符合条件，则首先设置响应对象字符编码格式为utf8
            HttpSession session = httpServletRequest.getSession(false);
            JSONObject message = new JSONObject();
            if (session == null || session.getAttribute("currentUser") == null) {
                message.put("message", "您没有登录，请登录");
                response.getWriter().println(message);
            } else {
                //执行其他过滤器，若其他过滤器执行完毕则执行原请求
                chain.doFilter(request, response);
            }
        }
        System.out.println("Filter 3 - judge ends");
    }
}
