package com.fz.service.mahout;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.fz.action.MahoutClusterAction;
import com.fz.util.HadoopUtils;

public  class JobRunnable implements Runnable {

	public Tool job;
	public String[] args;
	
	public JobRunnable(Tool job,String[] args){
		this.job=job;
		this.args=args;
	}
	@Override
	public void run() {
		try {
			Configuration conf =HadoopUtils.getConf();
			ToolRunner.run(conf, job,args);
		} catch (Exception e) {
//			e.printStackTrace();
			MahoutClusterAction.log.info("调用KmeansDriver()出错");
			MahoutClusterAction.log.info(e.getMessage());
			HadoopUtils.RUNNING=false;
		}
	}

}
