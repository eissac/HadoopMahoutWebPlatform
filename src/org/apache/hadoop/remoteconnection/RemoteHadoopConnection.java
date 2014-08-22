package org.apache.hadoop.remoteconnection;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.yarn.conf.YarnConfiguration;

public class RemoteHadoopConnection {
	
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new YarnConfiguration();
		conf.set("fs.defaultFS", "hdfs://master:8020");
		conf.set("mapreduce.framework.name", "yarn");
		conf.set("yarn.resourcemanager.address", "master:8032");
		
		JobClient jobclient = new JobClient(conf);
		String[] arg = {
				"-i","hdfs://master:8020/kmeans/test.txt",
				"-o","hdfs://master:8020/kmeans/traindata"
		};
		ToolRunner.run(conf, new Text2SeqVectorJob(),arg);
	}
}


