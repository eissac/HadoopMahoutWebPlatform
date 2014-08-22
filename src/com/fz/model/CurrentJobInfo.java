package com.fz.model;

import org.apache.hadoop.mapred.JobID;

import com.fz.util.HadoopUtils;

public class CurrentJobInfo {

	private String jobName;
	private String jobIdStr;
	private JobID jobId;
	private String mapProgress;  // 保持两位小数，百分比
	private String redProgress;  // 同上；
	private String state;       // 状态 ，包括（未定义undefined，运行中running，失败fail，成功success）
	
	public CurrentJobInfo(){
		this.mapProgress="";
		this.redProgress="";
		this.state="";
		this.jobName="";
	}
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public JobID getJobId() {
		return jobId;
	}
	public void setJobId(JobID jobId) {
		this.jobId = jobId;
	}
	public String getMapProgress() {
		if("SUCCEEDED".equals(state)||"FAILED".equals(state)){
			mapProgress="100%";
		}
		return mapProgress;
	}
	public void setMapProgress(String mapProgress) {
		if("SUCCEEDED".equals(state)||"FAILED".equals(state)){
			mapProgress="100%";
		}
		this.mapProgress = mapProgress;
	}
	public String getRedProgress() {
		if("SUCCEEDED".equals(state)||"FAILED".equals(state)){
			redProgress="100%";
		}
		return redProgress;
	}
	public void setRedProgress(String redProgress) {
		if("SUCCEEDED".equals(state)||"FAILED".equals(state)){
			redProgress="100%";
		}
		this.redProgress = redProgress;
	}
	public String getState() {
		if("RUNNING".equals(state)||"PREP".equals(state)){
			return state+HadoopUtils.getDot();
		}
		return state;
	}
	public void setState(String state) {
		
		this.state = state;
	}
	
	@Override
	public String toString(){
		return "jobID:"+this.jobId.toString()+",jobName:"+this.jobName+",mapProgress:"+
				getMapProgress()+",redProcess:"+getRedProgress()+",state:"+this.state;
				
	}

	public String getJobIdStr() {
		return jobIdStr;
	}

	public void setJobIdStr(String jobIdStr) {
		this.jobIdStr = jobIdStr;
	}
}
