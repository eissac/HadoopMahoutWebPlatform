package com.fz.util;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.iterator.ClusterWritable;
import org.apache.mahout.common.iterator.sequencefile.PathType;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileDirValueIterable;

public class MahoutUtils {
	/**
	 * write the given cluster center to the given file
	 * @return
	 * @throws FileNotFoundException 
	 */
	public static Log log=LogFactory.getLog(MahoutUtils.class);
	
	/*public static boolean writeCenterToLocal(Configuration conf,String outputPath,String centerPathDir) throws FileNotFoundException{
		
		Writer writer=null;
		try {
			File outputFile=new File(outputPath);
			writer = Files.newWriter(outputFile, Charsets.UTF_8);
			writeTxtCenter(writer, 
					new SequenceFileDirValueIterable<Cluster>(new Path(centerPathDir, "part-*"), PathType.GLOB, conf));
	//				new SequenceFileDirValueIterable<Writable>(new Path(centerPathDir, "part-r-00000"), PathType.LIST,
					//		PathFilters.partFilter(),conf));
			writer.flush();
		} catch (IOException e) {
			log.info("write error:\n"+e.getMessage());
			return false;
		}finally{
			try {
				if(writer!=null){
					writer.close();
				}
			} catch (IOException e) {
				log.info("close writer error:\n"+e.getMessage());
			}
		}
		return true;
	}
	*//**
	 * write the cluster to writer
	 * @param writer
	 * @param cluster
	 * @return
	 * @throws IOException 
	 *//*
	private static boolean writeTxtCenter(Writer writer,Iterable<Cluster> clusters) throws IOException{
		
		for(Cluster cluster:clusters){
			String fmtStr = cluster.asFormatString(null);
			System.out.println("fmtStr:"+fmtStr);
			writer.write(fmtStr);
			writer.write("\n");
		}
		return true;
	}*/
	/**
	 * 读取聚类中心向量
	 * @param conf
	 * @param centerPathDir
	 * @return
	 * @throws IOException
	 */
	public static String readCenter(Configuration conf,String centerPathDir) throws IOException{
		StringBuffer buff = new StringBuffer();
		Path input = new Path(centerPathDir);
		if(!HadoopUtils.getFs().exists(input)){
			return input+" not exist ,please check the input";
		}
		for(ClusterWritable cl:new SequenceFileDirValueIterable<ClusterWritable>(input, PathType.GLOB, conf)){
			buff.append(cl.getValue().asFormatString(null)).append("\n");
		}
		return buff.toString();
	}
}
