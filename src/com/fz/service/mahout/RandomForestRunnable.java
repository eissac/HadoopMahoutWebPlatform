package com.fz.service.mahout;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.classifier.df.data.DescriptorException;
import org.apache.mahout.classifier.df.mapreduce.BuildForest;
import org.apache.mahout.classifier.df.tools.Describe;

import com.fz.action.MahoutClusterAction;
import com.fz.util.HadoopUtils;

public class RandomForestRunnable implements Runnable{

	private String[] args0;
	private String[] args1;
	private Configuration conf;
	public RandomForestRunnable(Configuration conf,String[] args0,String[] args1){
		this.args0=args0;
		this.args1=args1;
		this.conf=conf;
	}
	@Override
	public void run() {
		// describe 
		try {
			Describe.main(args0);
		} catch (IOException e) {
			HadoopUtils.RUNNING=false;
			HadoopUtils.setFinished(true);
			MahoutClusterAction.log.info("调用Describe.main出错");
			MahoutClusterAction.log.info(e.getMessage());
			e.printStackTrace();
		} catch (DescriptorException e) {
			HadoopUtils.RUNNING=false;
			HadoopUtils.setFinished(true);
			MahoutClusterAction.log.info("调用Describe.main出错");
			MahoutClusterAction.log.info(e.getMessage());
			e.printStackTrace();
		}
		
		// build forest
		
		try {
			ToolRunner.run(conf, new BuildForest(),args1);
		} catch (Exception e) {
			HadoopUtils.RUNNING=false;
			HadoopUtils.setFinished(true);
			MahoutClusterAction.log.info("调用BuildForest出错");
			MahoutClusterAction.log.info(e.getMessage());
		}
	}

}
