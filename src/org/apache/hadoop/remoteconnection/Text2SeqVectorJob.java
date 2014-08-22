package org.apache.hadoop.remoteconnection;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.ToolRunner;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.mahout.common.AbstractJob;
import org.apache.mahout.math.VectorWritable;

/**
 * 文本二维数据转换为序列向量数据
 * 
 * @author fansy
 *
 */

public class Text2SeqVectorJob extends AbstractJob {
	public enum Records {
		BAD_RECORDS
	}

	@Override
	public int run(String[] args) throws Exception {
		addInputOption();
		addOutputOption();
		// 增加向量之间的分隔符
		addOption("splitCharacter", "sc",
				"Vector split character,default is comma", ",");

		if (parseArguments(args) == null) {
			return -1;
		}
		String sc = getOption("splitCharacter");
		Configuration conf = getConf();

		// System.out.println(conf.get("fs.defaultFS"));
		Path in = getInputPath();
		Path out = getOutputPath();
		out.getFileSystem(conf).delete(out, true);
		conf.set("sc", sc);
		Job job = Job.getInstance(conf, "text to seq vector job");
		job.setJarByClass(Text2SeqVectorJob.class);

		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);

		job.setMapperClass(Text2SeqMapper.class);
		job.setMapOutputKeyClass(LongWritable.class);
		job.setMapOutputValueClass(VectorWritable.class);
		job.setOutputKeyClass(LongWritable.class);
		job.setOutputValueClass(VectorWritable.class);
		job.setNumReduceTasks(0);
		// System.out.println(job.getConfiguration().get("mapreduce.job.reduces"));
		// System.out.println(conf.get("mapreduce.job.reduces"));
		FileInputFormat.setInputPaths(job, in);
		FileOutputFormat.setOutputPath(job, out);

		return job.waitForCompletion(true) ? 0 : -1;
	}

	public static void main(String[] args) throws Exception {
		 Configuration conf = new Configuration();
		 conf.set("fs.defaultFS", "hdfs://master:8020");
		 conf.set("mapreduce.framework.name", "yarn");
		 conf.set("yarn.resourcemanager.address", "master:8032");

//		Configuration conf = new Configuration();
//		conf.set("fs.defaultFs", "hdfs://master:8020");

		
		
		String[] arg = { "-i", "hdfs://master:8020/kmeans/test.txt", "-o",
				"hdfs://master:8020/kmeans/traindata" };

		ToolRunner.run(conf, new Text2SeqVectorJob(), arg);
	}

}
