package com.fz.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.fz.model.CurrentJobInfo;
import com.fz.service.hadoop.HadoopService;
import com.fz.util.HadoopUtils;
import com.fz.util.MahoutUtils;
import com.fz.util.Utils;
import com.opensymphony.xwork2.ActionSupport;
@Component("hadoop")
@Scope("prototype")
public class HadoopAction extends ActionSupport {

	/**
	 * 
	 */
	public static Logger log = LoggerFactory.getLogger(HadoopAction.class);
	private static final long serialVersionUID = 1L;
	// 集群参数
	private String fs;
	private String rm;
	// 上传参数
	private String input;
	private String hdfsOutput;
	
	// 读取参数
	private String lines;
	
	// 随机森林测试
	private String model;
	private String dataset;
	
	
	@Resource  
	private HadoopService hadoopService;
	/**
	 * 设置集群参数，并验证是否可以连通
	 * @throws IOException 
	 */
	public void setup() throws IOException, InterruptedException{
		// 初始化集群状态；
		
		HadoopUtils.RUNNING=false;
		HadoopUtils.setFinished(true);
		log.info("fs:"+fs+",rm:"+rm);
		int returnInt=hadoopService.checkConnection(fs, rm);
		System.out.println(returnInt);
		String returnInfo=String.valueOf(returnInt);
		Utils.stringToWriter(returnInfo);
		log.info(returnInfo);
	} 
	/**
	 * 上传文件
	 * @throws IOException
	 */
	public void upload() throws IOException{
		log.info("input:"+input+",hdfsOutput:"+hdfsOutput);
		String returnInfo  = hadoopService.upload(input, hdfsOutput);
		Utils.stringToWriter(returnInfo);
		log.info("上传文件完成");
	}
	/**
	 * 下载文件
	 * @throws IOException
	 */
	public void download() throws IOException{
		log.info("input:"+hdfsOutput+",hdfsOutput:"+input);
		String returnInfo  = hadoopService.download( hdfsOutput,input);
		Utils.stringToWriter(returnInfo);
		log.info("上传文件完成");
	}
	/**
	 * 读取HDFS文件
	 * 连续两次读取会有问题, response 要每次都获取一次才行，不然就会出现此种问题
	 * @throws IOException 
	 */
	public void read() throws IOException{
	/*	HttpServletRequest request = ServletActionContext.getRequest();  
		System.out.println(request.getRequestURI());*/
		log.info("input"+input+",lines:"+lines);
		int linesNum=Integer.parseInt(lines);
		Utils.stringToWriter(hadoopService.readHDFS(input, linesNum));
		log.info(" 完成对"+input+" 的读取");
	}
	
	public void readClusterCenter() throws IOException{
		log.info("中心向量路径为："+input);
		Utils.stringToWriter(MahoutUtils.readCenter(HadoopUtils.getConf(), input));
		log.info(" 完成对"+input+" 中心向量的读取");
	}
	
	/**
	 * 获取监控信息
	 * @throws IOException
	 */
	public void monitor() throws IOException{
		System.out.println("it is here");
		if(!HadoopUtils.RUNNING){// 说明没有初始化，没有监控，所以不需要返回信息
			return;
		}
		// 已经在别的地方初始化了，在这里只需获取任务状态信息，以及判断是否已经运行完成，完成则设置RUNNING为false
		List<CurrentJobInfo>  list = HadoopUtils.getCurrentJobs();
		System.out.println(list.size());
		if(HadoopUtils.checkJobsFinished()){
			HadoopUtils.RUNNING=false;
		}
		Utils.stringToWriter(JSON.toJSONString(list));
	}
	
	/**
	 * 随机森林预测，非MR算法,直接返回模糊矩阵等信息(可调用MR算法，暂时不做)
	 * 放在Hadoop模块
	 * @throws IllegalArgumentException
	 * @throws IOException
	 */
	public void testrf() throws IllegalArgumentException, IOException {
		/*String [] args={
				"-i",input,
				"-m",model,
				"-ds",dataset
		};*/
		String info = hadoopService.testrf(input, dataset, model);
		Utils.stringToWriter(info);
	}
	
	@Override
	public String execute(){
		System.out.println();
		return SUCCESS;
	}
	public String test(){
		System.out.println();
		return null;
	}

	public String getFs() {
		return fs;
	}

	public void setFs(String fs) {
		this.fs = fs;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getHdfsOutput() {
		return hdfsOutput;
	}

	public void setHdfsOutput(String hdfsOutput) {
		this.hdfsOutput = hdfsOutput;
	}
	
	
	public String getLines() {
		return lines;
	}
	public void setLines(String lines) {
		this.lines = lines;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getDataset() {
		return dataset;
	}
	public void setDataset(String dataset) {
		this.dataset = dataset;
	}
}
