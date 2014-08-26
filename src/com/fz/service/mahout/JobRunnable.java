package com.fz.service.mahout;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.fz.util.HadoopUtils;

public class JobRunnable implements Runnable{
	public String[] args;
	public Tool job;

	public JobRunnable(Tool job, String[] args) {
		this.job = job;
		this.args = args;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Configuration conf = HadoopUtils.getConf();
		try {
			ToolRunner.run(conf, job, args);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			HadoopUtils.RUNNING =false;
		}
	}
}
