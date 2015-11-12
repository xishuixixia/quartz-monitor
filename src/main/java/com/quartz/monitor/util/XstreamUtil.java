package com.quartz.monitor.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.quartz.monitor.conf.QuartzConfig;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class XstreamUtil {

	private  static Logger log = Logger.getLogger(XstreamUtil.class);
	
	private static XStream xs;
	
	static {
		xs = new XStream(new  StaxDriver());
		xs.alias("config", QuartzConfig.class);
	}
	
	public static void  object2XML(QuartzConfig config) throws IOException{
		
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"quartz-config/";
		File file = new File(path + "quartz-config-"+config.getUuid()+".xml");
		file.createNewFile();
		FileOutputStream fs = new FileOutputStream(file);
		log.info("create xml file : "+fs.toString());
		xs.toXML(config,fs);
	}
	public static QuartzConfig  xml2Object(String path) throws FileNotFoundException{
		
		log.info("load config from  xml file : "+ path);
		//FileOutputStream fs = new FileOutputStream(path);
		QuartzConfig config = new QuartzConfig();
		config = (QuartzConfig) xs.fromXML(new FileReader(path));
		return config;
	}
	
	public static boolean removeXml(String uuid) {
		
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"quartz-config/";
		path = path +"quartz-config-" + uuid +".xml";
		File file = new File(path);
		log.info("remove xml file : "+ path);
		return file.delete();
	}
}
