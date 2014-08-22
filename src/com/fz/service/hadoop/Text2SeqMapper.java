package com.fz.service.hadoop;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

import com.fz.service.hadoop.Text2SeqVectorJob.Records;

public class Text2SeqMapper extends
		Mapper<LongWritable, Text,LongWritable , VectorWritable> {
	private String sc=null;
	
	@Override
	protected void setup(Context cxt){
		sc= cxt.getConfiguration().get("sc");
	}
	
	@Override
	public void map(LongWritable key,Text value, Context cxt)throws IOException,InterruptedException{
		String [] vector=value.toString().split(sc);
		if(vector.length<=0){
			// 坏记录
			cxt.getCounter(Records.BAD_RECORDS).increment(1L);
			return ;
		}
		Vector v=new RandomAccessSparseVector(vector.length);  
        for(int i=0;i<vector.length;i++){  
            double item=0;  
            try{  
                item=Double.parseDouble(vector[i]);  
            }catch(Exception e){  
                return; // 如果不可以转换，说明输入数据有问题  
            }  
            v.setQuick(i, item);  
        }  
        VectorWritable vw=new VectorWritable(v);  
        cxt.write(key, vw);
	}
	
}
