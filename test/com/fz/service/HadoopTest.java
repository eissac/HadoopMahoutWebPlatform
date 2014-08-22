package com.fz.service;

import org.apache.hadoop.util.ToolRunner;
import org.junit.Test;

import com.fz.service.hadoop.Text2SeqVectorJob;
import com.fz.util.HadoopUtils;

public class HadoopTest {

	@Test
	public void testText2SeqVectorJob() throws Exception{
		String input ="hdfs://master:8020/user/Administrator/input/wine.csv";
		String output="hdfs://master:8020/user/Administrator/input/wine_001";
		String sc=",";
		String [] args={"-i",input,"-o",output,"-sc",sc};
		ToolRunner.run(HadoopUtils.getConfLocal(), new Text2SeqVectorJob(), args);
	}
}
