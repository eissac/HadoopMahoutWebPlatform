package com.fz.service.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

import com.fz.action.MahoutClusterAction;
import com.fz.util.HadoopUtils;

public class Txt2VecRunnable implements Runnable {
	
	private String[] args;
	public Txt2VecRunnable(String[] args){
		this.args=args;
	}
	@Override
	public void run() {
		try {
			ToolRunner.run(new Configuration(), new Text2SeqVectorJob(),args);
		} catch (Exception e) {
//			e.printStackTrace();
			MahoutClusterAction.log.info("调用KmeansDriver()出错");
			MahoutClusterAction.log.info(e.getMessage());
			HadoopUtils.RUNNING=false;
		}
	}

}
