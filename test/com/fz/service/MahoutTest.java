package com.fz.service;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.cf.taste.hadoop.item.RecommenderJob;
import org.apache.mahout.classifier.df.DecisionForest;
import org.apache.mahout.classifier.df.data.DescriptorException;
import org.apache.mahout.classifier.df.mapreduce.BuildForest;
import org.apache.mahout.classifier.df.mapreduce.TestForest;
import org.apache.mahout.classifier.df.tools.Describe;
import org.apache.mahout.classifier.naivebayes.training.TrainNaiveBayesJob;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.fz.util.HadoopUtils;
import com.fz.util.MahoutUtils;

public class MahoutTest {
	private Configuration conf;
	@Before
	public void  initial(){
		 conf =HadoopUtils.getConfLocal();
	}
	@Test
	@Ignore
	public void testKmeans() throws Exception{
//		String input="";
//		String output="";
		String[] args={"-i","hdfs://node33:8020/user/Administrator/input/wine_001",
				"-c","hdfs://node33:8020/user/Administrator/center/",
				"-o","hdfs://node33:8020/user/Administrator/output/001",
				"-k","3",
				"-dm",EuclideanDistanceMeasure.class.getName(),
				"-x","2",
				"-cl",
				"-xm","mapreduce"};
		HadoopUtils.getLocalFs().delete(new Path("hdfs://node33:8020/user/Administrator/output/001"), true);
		ToolRunner.run(conf, new KMeansDriver(), args);
	}
	@Test
//	@Ignore
	public void testCluterDumper() throws Exception{
		String input="hdfs://node33:8020/user/Administrator/output/001/clusters-2-final/";
//		String[] args={"-i",input,"-o",""};
//		String[] args={};
		System.out.println(MahoutUtils.readCenter(new Configuration(), input));
	}
	@Test
	@Ignore
	public void testBayes() throws Exception{  //贝叶斯算法,针对数值型数据需要进行处理
		String[] args={};
		ToolRunner.run(conf, new TrainNaiveBayesJob(), args);
	}
	@Test
	@Ignore
	public void testDescribe() throws IOException, DescriptorException{ // RandomForest 
		String datasetPath = "hdfs://node33:8020/output/forest/glass.info";
		String[] args0={"-p","hdfs://node33:8020/input/glass.dat",
				"-f",datasetPath,
				"-d","I","9","N","L"};
		HadoopUtils.getLocalFs().delete(new Path(datasetPath),true);
		Describe.main(args0);
		
	}
	@Test
	@Ignore
	public void testBuildForest() throws Exception{
		Configuration conf = HadoopUtils.getConfLocal();
		String[] args={"-d","hdfs://node33:8020/input/glass.dat",
				"-ds","hdfs://node33:8020/output/forest/glass.info",
				"-sl","3",
				"-ms","3",
				"-t","10",
				"-p",
				"-o","hdfs://node33:8020/user/Administrator/001"};
		// 此输出路径只认最后一个名字,即002
		ToolRunner.run(conf, new BuildForest(),args);
	}
	@Test
	@Ignore
	public void testTestForest() throws Exception{
		Configuration conf = HadoopUtils.getConfLocal();
//		String[] args={};
		String[] args={"-i","hdfs://node33:8020/input/glass.dat",
				"-ds","hdfs://node33:8020/output/forest/glass.info",
				"-m","hdfs://node33:8020/user/Administrator/001",
				"-a",
				"-mr",
				"-o","hdfs://node33:8020/output/forest/001"};
		ToolRunner.run(conf, new TestForest(),args);
	}
	@Test
	@Ignore
	public void testLoadForest() throws IOException{
		Configuration conf = HadoopUtils.getConfLocal();
		Path file=new Path("hdfs://node33:8020/user/Administrator/001");
		DecisionForest forest = DecisionForest.load(conf, file);
		System.out.println(forest.toString());
	}
	
	@Test
	@Ignore
	public void testRecommend() throws Exception{
		// 9个MR
		String output="hdfs://node33:8020/user/Administrator/output/001";
		String[] args ={
				"-i","hdfs://node33:8020/user/Administrator/input/user.txt",
				"-o",output,
				"-n","3",
				"-b","false",
				"-s","SIMILARITY_EUCLIDEAN_DISTANCE",
				"--tempDir","tmp"
		};
//		[SIMILARITY_COOCCURRENCE,    
//         SIMILARITY_LOGLIKELIHOOD,     
//         SIMILARITY_TANIMOTO_COEFFICIEN
//         T, SIMILARITY_CITY_BLOCK,     
//         SIMILARITY_COSINE,            
//         SIMILARITY_PEARSON_CORRELATION
//         ,                             
//         SIMILARITY_EUCLIDEAN_DISTANCE]
		HadoopUtils.getLocalFs().delete(new Path("tmp"), true);
		HadoopUtils.getLocalFs().delete(new Path(output), true);
		ToolRunner.run(conf, new RecommenderJob(), args);
	}
	
}
