package com.fz.service.mahout;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.clustering.kmeans.KMeansDriver;

import com.fz.action.MahoutClusterAction;
import com.fz.util.HadoopUtils;

public class KMeansRunnable implements Runnable {
	String[] args;
	public KMeansRunnable(String[] args){
		this.args=args;
	}
	
	@Override
	public void run() {
		Configuration conf =HadoopUtils.getConf();
		try {
			ToolRunner.run(conf, new KMeansDriver(), args);
		} catch (Exception e) {
//			e.printStackTrace();
			MahoutClusterAction.log.info("调用KmeansDriver()出错");
			MahoutClusterAction.log.info(e.getMessage());
			HadoopUtils.RUNNING=false;
		}
	}

}
