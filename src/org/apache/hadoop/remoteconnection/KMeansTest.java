package org.apache.hadoop.remoteconnection;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.mahout.clustering.kmeans.KMeansDriver;

public class KMeansTest {
	public static void main(String[] args) throws Exception{
		
		Configuration conf = new YarnConfiguration();
		conf.set("fs.defaultFS", "hdfs://master:8020");
		conf.set("mapreduce.framework.name", "yarn");
		conf.set("yarn.resourcemanager.address", "master:8032");
		conf.set("mapred.jar", "jars\\jar.jar");
		
		String input= "hdfs://master:8020/kmeans/traindata/part-m-00000";
		String output = "hdfs://master:8020/kmeans/output";
		
		
		String [] arg={"-i",input,
				"-c",output,
				"-o",output,
				"-k","3",
				"-x","5",
				"-cl",
				"-xm","mapreduce"};
		
		
		ToolRunner.run(conf,new KMeansDriver(),arg);
		
	}
}
