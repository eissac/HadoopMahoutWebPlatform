package com.fz.service.hadoop;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.ClusterStatus;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.util.LineReader;
import org.apache.mahout.classifier.ClassifierResult;
import org.apache.mahout.classifier.RegressionResultAnalyzer;
import org.apache.mahout.classifier.ResultAnalyzer;
import org.apache.mahout.classifier.df.DFUtils;
import org.apache.mahout.classifier.df.DecisionForest;
import org.apache.mahout.classifier.df.data.DataConverter;
import org.apache.mahout.classifier.df.data.Dataset;
import org.apache.mahout.classifier.df.data.Instance;
import org.apache.mahout.common.RandomUtils;
import org.springframework.stereotype.Service;

import com.fz.action.HadoopAction;
import com.fz.util.HadoopUtils;
import com.google.common.collect.Lists;
import com.google.common.io.Closeables;

@Service("hadoopService")  
public class HadoopService {
	/**
	 * 
	 * @param fs
	 * @param rm
	 * @return
	 * 0: fs and rm do not online
	 * 1: fs does not online
	 * 2: rm does not online
	 * 3: fs and rm online
	 * if the return status is 3 ,then HadoopUtils.setConf(),HadoopUtils.setFs();
	 * @throws IOException 
	 */
	public int checkConnection(String fsStr,String rm) throws IOException{
		if(HadoopUtils.getConf() != null){
			HadoopUtils.setConf(null);
		}
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", fsStr);
		conf.set("yarn.resourcemanager.address", rm);
		conf.set("mapreduce.framework.name", "yarn"); 
		conf.set("mapred.jar", "jar.jar");
		FileSystem fs = FileSystem.get(conf);
		boolean fsOnline=fs.exists(new Path("/"));
		if(!fsOnline){
			return 1;
		}
		JobClient jc = new JobClient(conf);
		ClusterStatus cs = jc.getClusterStatus();
		if(!"RUNNING".equals(cs.getJobTrackerStatus().toString())){
			return 0;
		}
		// 集群验证成功
		HadoopUtils.setConf(conf);
		HadoopUtils.setFs(fs);
		// 通过判断Hadoop.getConf()是否为null来确定是否已经配置过集群
		return 3;
	}
	/**
	 * 上传一个本地文件
	 * @param input
	 * @param hdfsOutput
	 * @return
	 * @throws IOException 
	 */
	public String upload(String input ,String hdfsOutput) throws IOException{
		/*if(HadoopUtils.getConf()==null){
			return "未配置集群!";
		}*/
		FileSystem fs= HadoopUtils.getFs();
		Path inputPath= new Path(input);
		/*if(!fs.exists(inputPath)){ // 本地文件不存在
			return flag;
		}*/
		Path outputPath = new Path(hdfsOutput);
		
		// hdfs文件存在则删除
		if(fs.exists(new Path(outputPath.toString()+inputPath.getName()))){
			fs.delete(new Path(outputPath.toString()+inputPath.getName()), true);
		}
		try{
			fs.copyFromLocalFile(inputPath, outputPath);
		}catch(IOException e){
		//	e.printStackTrace();
			System.out.println(e.getMessage().split("\n")[0]);
			return "文件上传失败\n"+e.getMessage().split("\n")[0];
		}
		return "文件上传成功";
	}
	
	/**
	 * 下载一个HDFS文件到本地 
	 * @param input
	 * @param hdfsOutput
	 * @return
	 * @throws IOException 
	 */
	public String download(String hdfsOutput ,String input) throws IOException{
		/*if(HadoopUtils.getConf()==null){
			return "未配置集群!";
		}*/
		FileSystem fs= HadoopUtils.getFs();
		Path inputPath= new Path(hdfsOutput);
		/*if(!fs.exists(inputPath)){ // 本地文件不存在
			return flag;
		}*/
		Path outputPath = new Path(input);
		
		// 本地文件存在则删除
		if(fs.exists(outputPath)){
			fs.delete(outputPath, true);
		}
		try{
			fs.copyToLocalFile(inputPath, outputPath);
		}catch(IOException e){
		//	e.printStackTrace();
			System.out.println(e.getMessage().split("\n")[0]);
			return "文件下载失败\n"+e.getMessage().split("\n")[0];
		}
		return "文件下载成功";
	}
	/**
	 * 读取HDFS文件内容
	 * @param path
	 * @param lines
	 * @return
	 * @throws IOException 
	 */
	public String readHDFS(String path,int lines) throws IOException{
		StringBuffer buff = new StringBuffer();
		FSDataInputStream hdfsInStream = null;
		try {
		// 打开文件流
			hdfsInStream = HadoopUtils.getFs().open(new Path(path));
		} catch(Exception e) {
			e.printStackTrace();
			return "找不到文件！";
		}
		// 按行读取
		LineReader inLine = new LineReader(hdfsInStream, HadoopUtils.getConf());
		Text txtLine = new Text();
		int iResult = inLine.readLine(txtLine); //读取第一行
		buff.append(txtLine.toString());
		buff.append("\n");
		int iDataRowCounter = 1;
		while (iResult > 0 && iDataRowCounter <= lines) {
			iDataRowCounter++;
			iResult = inLine.readLine(txtLine);
			buff.append(txtLine.toString());
			buff.append("\n");
		}
		
		hdfsInStream.close();
		return buff.toString();
	}
	/**
	 *  随机森林算法测试
	 *  返回模型对数据的测试结果，包含模糊矩阵，正确率等
	 * @param input
	 * @param dataset
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	public  String testrf(String input,String datasetp,String model) throws IOException{
		Path dataPath = new Path(input);
		Path datasetPath = new Path(datasetp);
		Path modelPath = new Path(model);
		Path outputPath = new Path("tmp"); // 临时目录 需删除
		HadoopUtils.getFs().delete(outputPath, true);
		HadoopAction.log.info("Loading the forest...");
	    DecisionForest forest = DecisionForest.load(HadoopUtils.getConf(), modelPath);
	    if (forest == null) {
	      HadoopAction.log.error("No Decision Forest found!");
	      return "no";
	    }

	    // load the dataset
	    Dataset dataset = Dataset.load(HadoopUtils.getConf(), datasetPath);
	    DataConverter converter = new DataConverter(dataset);

	    HadoopAction.log.info("Sequential classification...");
	    long time = System.currentTimeMillis();

	    Random rng = RandomUtils.getRandom();

	    List<double[]> resList = Lists.newArrayList();
	    if (HadoopUtils.getFs().getFileStatus(dataPath).isDirectory()) {
	      //the input is a directory of files
	      testDirectory(outputPath, converter, forest, dataset, resList, rng,dataPath);
	    }  else {
	      // the input is one single file
	      testFile(dataPath, outputPath, converter, forest, dataset, resList, rng);
	    }

	    time = System.currentTimeMillis() - time;
	    HadoopAction.log.info("Classification Time: {}", DFUtils.elapsedTime(time));
	    String returninfo="";
	    if (true) {
	      if (dataset.isNumerical(dataset.getLabelId())) {
	        RegressionResultAnalyzer regressionAnalyzer = new RegressionResultAnalyzer();
	        double[][] results = new double[resList.size()][2];
	        regressionAnalyzer.setInstances(resList.toArray(results));
	        returninfo=regressionAnalyzer.toString();
	        HadoopAction.log.info("{}", regressionAnalyzer);
	      } else {
	        ResultAnalyzer analyzer = new ResultAnalyzer(Arrays.asList(dataset.labels()), "unknown");
	        for (double[] r : resList) {
	          analyzer.addInstance(dataset.getLabelString(r[0]),
	            new ClassifierResult(dataset.getLabelString(r[1]), 1.0));
	        }
	        HadoopAction.log.info("{}", analyzer);
	        returninfo= analyzer.toString();
	      }
	    }
	    
		return returninfo;
	}
	
	
	
	
	/**
	 * 随机森林预测分析函数
	 */
	private void testDirectory(Path outPath, DataConverter converter,
			DecisionForest forest, Dataset dataset,
			Collection<double[]> results, Random rng,Path dataPath) throws IOException {
		Path[] infiles = DFUtils.listOutputFiles(HadoopUtils.getFs(), dataPath);

		for (Path path : infiles) {
			HadoopAction.log.info("Classifying : {}", path);
			Path outfile = outPath != null ? new Path(outPath, path.getName())
					.suffix(".out") : null;
			testFile(path, outfile, converter, forest, dataset, results, rng);
		}
	}
	/**
	 * 随机森林预测分析函数
	 */
	private void testFile(Path inPath, Path outPath, DataConverter converter,
			DecisionForest forest, Dataset dataset,
			Collection<double[]> results, Random rng) throws IOException {
		// create the predictions file
		FSDataOutputStream ofile = null;

		if (outPath != null) {
			ofile = HadoopUtils.getFs().create(outPath);
		}

		FSDataInputStream input = HadoopUtils.getFs().open(inPath);
		try {
			Scanner scanner = new Scanner(input, "UTF-8");

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.isEmpty()) {
					continue; // skip empty lines
				}

				Instance instance = converter.convert(line);
				double prediction = forest.classify(dataset, rng, instance);

				if (ofile != null) {
					ofile.writeChars(Double.toString(prediction)); // write the
																	// prediction
					ofile.writeChar('\n');
				}

				results.add(new double[] { dataset.getLabel(instance),
						prediction });
			}

			scanner.close();
		} finally {
			Closeables.close(input, true);
		}
	}
}
