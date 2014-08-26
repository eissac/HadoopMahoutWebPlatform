package com.fz.service.mahout;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.mahout.clustering.canopy.CanopyDriver;

import com.fz.action.MahoutClusterAction;
import com.fz.util.HadoopUtils;

public  class CanopyRunnable implements Runnable {

	public String[] args;
	
	public CanopyRunnable(String[] args){
		this.args=args;
	}
	@Override
	public void run() {
		Configuration conf = HadoopUtils.getConf();
		try {
			ToolRunner.run(conf, new CanopyDriver(), args);
		} catch (Exception e) {
			e.printStackTrace();
			MahoutClusterAction.log.info("调用Canopy()算法出错");
			MahoutClusterAction.log.info(e.getMessage());
		} finally{
			HadoopUtils.RUNNING=false;
		}
	}

}
