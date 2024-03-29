package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(filterName = "Filter2",urlPatterns = "/*"/*仅所有资源进行过滤*/)
public class Filter2 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        System.out.println("Filter 2 begins");
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        System.out.println("设置响应字符编码");
        String method = request.getMethod();
        chain.doFilter(req,resp);//执行其他过滤器，如果过滤器已经执行完毕，则执行原请求
        System.out.println("Filter 2 - encoding ends");
    }


    @Override
    public void destroy() {

    }
}
