package com.fz.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobID;
import org.apache.hadoop.mapred.JobStatus;
import org.apache.hadoop.mapred.RunningJob;
import org.apache.hadoop.mapreduce.MRConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fz.model.CurrentJobInfo;

public class HadoopUtils {

	private static Logger log =LoggerFactory.getLogger(HadoopUtils.class);
	private static Configuration conf=null;
	private static FileSystem fs=null;
	private static List<CurrentJobInfo> list = new ArrayList<CurrentJobInfo> ();
	private static JobClient jc =null ;
	private static boolean finished= false;
	private static int dot=1;
	private static StringBuffer buff = null;
	public static String getDot(){
		buff = new StringBuffer();
		
		if(dot==6){
			dot=1;
		}
		for(int i=0;i<dot;i++){
			buff.append(".");
		}
		dot++;
		return buff.toString();
	}
	
	public static  boolean RUNNING=false;
	//算法运行的步数
	private static int steps =0;
	public static Configuration getConfLocal(){
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://master:8020");
        conf.set("yarn.resourcemanager.address", "master:8032"); 
        conf.set("mapreduce.framework.name", "yarn");  
        conf.set("mapred.jar", "jar.jar");
        conf.set(MRConfig.MAPREDUCE_APP_SUBMISSION_CROSS_PLATFORM, "true");

        return conf;
	}
	public static FileSystem getLocalFs() throws IOException{
		FileSystem fs = FileSystem.get(getConfLocal());
		return fs;
	}
	public static List<CurrentJobInfo> getCurrentJobs() throws IOException{
		
		
		for(int i=0;i<list.size();i++){
			CurrentJobInfo iJob = list.get(i);
			RunningJob runningJob =findGivenJob(iJob.getJobId().toString());
			if(runningJob==null){
				break;
			}
			if(i==list.size()-1){ // 放在设置的前面
				finished=runningJob.isComplete();
			}
			iJob.setJobName(runningJob.getJobName());
			iJob.setJobIdStr(runningJob.getJobStatus().getJobID().toString());
			iJob.setMapProgress(Utils.toPercent(runningJob.mapProgress(),2));
			iJob.setRedProgress(Utils.toPercent(runningJob.reduceProgress(), 2));
			iJob.setState(JobStatus.getJobRunState(runningJob.getJobState()));  // 有时map和reduce都到1时，此值仍是Running，需处理
		}
		return list;
	}
	/**
	 * 获取给定的job
	 * @param string
	 * @return
	 * @throws IOException 
	 */
	private static RunningJob findGivenJob(String string) throws IOException {
		
		RunningJob runningJob = null;
		JobStatus[] jbs=getJc().getAllJobs();
		for(int i=0;i<jbs.length;i++){
			if(string.equals(jbs[i].getJobID().toString())){
				runningJob = getJc().getJob(jbs[i].getJobID());
			}
		}
		return runningJob;
	}

	public static void initialCurrentJobs(int nextJobNum) throws IOException{
		/*if(list!=null&&list.size()==10){
			list.clear();
		}*/
		list.clear(); // 清空上次遗留
		JobStatus[] jbs=getJc().getAllJobs();
		JobID jID = findLastJob(jbs).getJobID();
		if(jID==null){
			// the first time start the cluster , will be fixed next time 
			
			// TODO fix the bug
			log.info("The cluster is started before and not running any job !!!");
		}
		log.info("The last job id is :{}", jID.toString());
		for(int i=1;i<=nextJobNum;i++){
			CurrentJobInfo cj = new CurrentJobInfo();
			cj.setJobId(new JobID(jID.getJtIdentifier(),jID.getId()+i));
			list.add(cj);
		}
	}
	
	private static JobStatus findLastJob(JobStatus[] jbs) {
		long time =0L;
		JobStatus jobId=null;
		for(int i=0;i<jbs.length;i++){
			/*if("job_1398958404577_0132".equals(jbs[i].getJobID().toString())){
				System.out.println("***************************");
			}*/
			if(jbs[i].getStartTime()>time){
				time=jbs[i].getStartTime();
				jobId=jbs[i];
			}
		}
		return jobId;
	}

	public static boolean checkJobsFinished() throws IOException{
		return finished;
	}
	
	/**
	 * 先调用setConf之后才能调用getConf()
	 * @return
	 */
	public static Configuration getConf(){
		return conf;
	}
	
	public static void setConf(Configuration config){
		conf= config;
	}

	public static FileSystem getFs() {
		return fs;
	}

	public static void setFs(FileSystem fs) {
		HadoopUtils.fs = fs;
	}
	
	public static void main(String[] args) throws IOException, InterruptedException{
//		float a= 0.22322323f;
//		System.out.println(Utils.toPercent(a, 2));
		printJobsStatus();
	}

	public static JobClient getJc() throws IOException {
//		JobClient jc=null;
		if(jc==null){// jc 不一定要每次获取
			jc= new JobClient(conf);   // 正式环境  
//			jc = new JobClient(getConfLocal());  // 测试环境
		}
		return jc;
	}

	public static void setJc(JobClient jc) {
		HadoopUtils.jc = jc;
	}
	
	/**
	 * 获取任务监控的调用方法
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static void printJobsStatus() throws IOException, InterruptedException{
		initialCurrentJobs(2);
		while(!checkJobsFinished()){
			Thread.sleep(1000);
			List<CurrentJobInfo>  list = getCurrentJobs();
			for(int i=0;i<list.size();i++){
				System.out.println(list.get(i).toString());
			}
			System.out.println("================================================");
		}
	}

	public static int getSteps() {
		return steps;
	}

	public static void setSteps(int steps) {
		HadoopUtils.steps = steps;
	}

	public static void setFinished(boolean finished) {
		HadoopUtils.finished = finished;
	}
}
