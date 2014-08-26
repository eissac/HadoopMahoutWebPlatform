package com.fz.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.fz.util.HadoopUtils;
import com.fz.util.Utils;

public class IsRunningFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		if(HadoopUtils.RUNNING){  //  应该在filter中定义，此处暂时
			// 向Writer中输出，集群正在运行任务，请稍后重试；
			// 在最后一个监控获取任务状态完成时，设置为false
			Utils.stringToWriter("running",(HttpServletResponse)response); 
			return;
		}
		HadoopUtils.RUNNING=true;  // 每个算法调用前都需设置此参数  
		 // 在filter中设置
		HadoopUtils.setFinished(false);
		chain.doFilter(request, response);
		

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
