package my.webframework.security.service.springmvc.helper;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 控制会话权限拦截器。
 * 
 * @author 陈瑞军
 */
public class SessionHandlerInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOGGER = Logger.getLogger(SessionHandlerInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
//		String url = request.getRequestURI();
//		if (url.contains("/Resources/"))
//			return true;
//		// spring mvc 重复提交表单，会造成登录时候二次登录，因此，采用提交表单不使用，而使用$.post方式等异步调用方式
//		if (url.endsWith("login.html") || url.endsWith("login")) {
//			return true;
//		}
		LOGGER.debug("++++++++++++++++++++++++++++++++++拦截开始");
		if (request.getSession().getAttribute("currentEmployee") == null) {
			// 此处跳转后，应该return false 不然，首先渲染界面，然后跳转，必然会出现异常
//			response.sendRedirect(request.getContextPath() + "/login.html");
			PrintWriter pw = response.getWriter();
			pw.write("用户没有登录，请首先登录");
			LOGGER.debug("++++++++++++++++++++++++++++++++++拦截完成");
			return false;
		}
		return true;
	}
}