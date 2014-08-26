package org.apache.hadoop.remoteconnection;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.mahout.common.HadoopUtil;

import com.fz.service.mahout.CanopyRunnable;

public class CanopyTest {
	public static void main(String[] args) throws Exception {
		Configuration conf = new YarnConfiguration();
		conf.set("fs.defaultFS", "hdfs://master:8020");
		conf.set("mapreduce.framework.name", "yarn");
		conf.set("yarn.resourcemanager.address", "master:8032");
		 conf.set("mapred.jar", "jars\\jar.jar");

		String input = "hdfs://master:8020/kmeans/traindata/part-m-00000";
		String output = "hdfs://master:8020/canopy/output";

		String[] arg = { "-i", input, "-o", output, "-dm",
				"org.apache.mahout.common.distance.EuclideanDistanceMeasure",
				"-t1", "80", "-t2", "55", "-cl" };

		
		HadoopUtil.delete(conf, new Path(output));
		
//		 ToolRunner.run(conf,new CanopyDriver(),arg);

		CanopyRunnable canopy = new CanopyRunnable(arg);
		Thread thread = new Thread(canopy);
		thread.start();
	}
}
