package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Calendar;


@WebFilter(filterName = "Filter 1",
        urlPatterns = "/*"/*仅所有资源进行过滤*/)
public class Filter1 implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        System.out.println("Filter 1 begins");
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        String path = request.getRequestURI();
        System.out.println(path + "@" + year + "/" + month + "/" + date + " " +hour + ":" +minute + ":" + second);
        chain.doFilter(req,resp);
        System.out.println("Filter 1 ends");
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
