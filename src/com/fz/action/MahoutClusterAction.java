package com.fz.action;

import java.io.IOException;




import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.cf.taste.hadoop.item.RecommenderJob;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fz.service.hadoop.Text2SeqVectorJob;
import com.fz.service.mahout.JobRunnable;
import com.fz.service.mahout.RandomForestRunnable;
import com.fz.util.HadoopUtils;
import com.opensymphony.xwork2.ActionSupport;
@Component("mahoutCluster")
@Scope("prototype")
public class MahoutClusterAction extends ActionSupport {

	public static Logger log = LoggerFactory.getLogger(MahoutClusterAction.class);
	private Configuration conf=HadoopUtils.getConf();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String inputpath;
	private String outputpath;
	private String centerpath;
	private String knum;
	private String iter;
	private String distance;
	
	// build random forest
	private String datasetpath;
	private String descriptor;
	private String m;
	private String ntree;
	
	// test random forest
	private String modelpath;
	
	// text2vector
	private String sc;
	
	// recommend 
	private String recommendNum;
	
	/**
	 * kmeans算法调用
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	public void kmeans() throws IllegalArgumentException, IOException {
		
		/*HadoopUtils.RUNNING=true;  // 每个算法调用前都需设置此参数  , 只支持单浏览器，单客户端
		 // 可以在filter中设置即可
		HadoopUtils.setFinished(false);*/
		// kmeans算法一共调用1+iter个MR任务
		HadoopUtils.initialCurrentJobs(1+Integer.parseInt(iter));
		String [] args={"-i",inputpath,
				"-c",centerpath,
				"-o",outputpath,
				"-k",knum,
				"-dm",distance,
				"-x",iter,
				"-cl",
				"-xm","mapreduce"};
		HadoopUtils.getFs().delete(new Path(outputpath), true);
		System.out.println(outputpath);
		new Thread(new JobRunnable(new KMeansDriver(),args)).start();
	}
	/**
	 * 随机森林建树 
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void buildrf() throws IllegalArgumentException, IOException {
		HadoopUtils.initialCurrentJobs(1);
		String[] tmpDescriptor = descriptor.split(" ");
		String [] args0=new String[5+tmpDescriptor.length];
		args0[0]="-p";
		args0[1]=inputpath;
		args0[2]="-f";
		args0[3]=datasetpath;
		args0[4]="-d";
		for(int i=0;i<tmpDescriptor.length;i++){
			args0[i+5]=tmpDescriptor[i];
		}
		String[] args1={"-d",inputpath,
				"-ds",datasetpath,
				"-sl",m,
				"-t",ntree,
				"-p",
				"-o",outputpath};
		HadoopUtils.getFs().delete(new Path(datasetpath), true);
		HadoopUtils.getFs().delete(new Path(outputpath), true);
		new Thread(new RandomForestRunnable(conf, args0, args1)).start();
	}
	/**
	 * 随机森林预测，非MR算法,直接返回模糊矩阵等信息(可调用MR算法，暂时不做)
	 * 放在Hadoop模块
	 * @throws IllegalArgumentException
	 * @throws IOException
	 *//*
	public void testrf() throws IllegalArgumentException, IOException {
		String [] args={
				"-i",inputpath,
				"-o",outputpath,
				"-sc",""
		};
		HadoopUtils.getFs().delete(new Path(outputpath), true);
		new Thread().start();
		
	}*/
	/**
	 * kmeans算法输入数据预处理模块
	 * @throws Exception 
	 */
	public void text2vector() throws Exception{
		HadoopUtils.initialCurrentJobs(1);
		String [] args={
				"-i",inputpath,
				"-o",outputpath,
				"-sc",sc
		};
		HadoopUtils.getFs().delete(new Path(outputpath), true);
		System.out.println("kmeans算法输入数据预处理模块");
		new Thread(new JobRunnable(new Text2SeqVectorJob(), args)).start();
	}
	
	public void recommend() throws Exception{
		HadoopUtils.initialCurrentJobs(9);
		String[] args ={
				"-i",inputpath,
				"-o",outputpath,
				"-n",recommendNum,
				"-b","false",
				"-s",distance,
				"--tempDir","tmp"
		};
		HadoopUtils.getFs().delete(new Path("tmp"), true);
		HadoopUtils.getFs().delete(new Path(outputpath), true);
		new Thread(new JobRunnable(new RecommenderJob(),args)).start();
	}
	public String getInputpath() {
		return inputpath;
	}
	public void setInputpath(String inputpath) {
		this.inputpath = inputpath;
	}
	public String getOutputpath() {
		return outputpath;
	}
	public void setOutputpath(String outputpath) {
		this.outputpath = outputpath;
	}
	public String getCenterpath() {
		return centerpath;
	}
	public void setCenterpath(String centerpath) {
		this.centerpath = centerpath;
	}
	public String getKnum() {
		return knum;
	}
	public void setKnum(String knum) {
		this.knum = knum;
	}
	public String getIter() {
		return iter;
	}
	public void setIter(String iter) {
		this.iter = iter;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getDatasetpath() {
		return datasetpath;
	}
	public void setDatasetpath(String datasetpath) {
		this.datasetpath = datasetpath;
	}
	public String getDescriptor() {
		return descriptor;
	}
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}
	public String getM() {
		return m;
	}
	public void setM(String m) {
		this.m = m;
	}
	public String getNtree() {
		return ntree;
	}
	public void setNtree(String ntree) {
		this.ntree = ntree;
	}
	public String getModelpath() {
		return modelpath;
	}
	public void setModelpath(String modelpath) {
		this.modelpath = modelpath;
	}
	public String getSc() {
		return sc;
	}
	public void setSc(String sc) {
		this.sc = sc;
	}
	public String getRecommendNum() {
		return recommendNum;
	}
	public void setRecommendNum(String recommendNum) {
		this.recommendNum = recommendNum;
	}
}
