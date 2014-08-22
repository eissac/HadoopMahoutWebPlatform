package com.fz.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.minlog.Log;
import com.fz.util.HadoopUtils;
import com.fz.util.Utils;


public class ConfigFilter implements Filter {
	private static Logger log = LoggerFactory.getLogger(ConfigFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest re= (HttpServletRequest)request;
		String url =re.getRequestURI();
		System.out.println(url);
		if(url!=null||!"".equals(url)){
			System.out.println(HadoopUtils.getConf() == null);
			if(url.contains("/hadoop_action/hadoop_setup")){
				
				chain.doFilter(request, response);
				
			}else if(HadoopUtils.getConf()!=null){
				
				chain.doFilter(request, response);
				
			}else{
				// 返回 信息,应该使用json打印数据,直接往流里面写数据，需要写html
				Utils.stringToWriter("请先连接Hadoop集群!",(HttpServletResponse)response); //			
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
//		System.out.println("initial ConfigFilter");
		log.info("initial ConfigFilter");
	}

}
