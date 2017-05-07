package com.ibm.insite.kafkareader;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaPairInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import kafka.serializer.StringDecoder;

@SpringBootApplication
public class KafkaSparkStreamingReaderApplication implements CommandLineRunner {

	@Value("${kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${topic.name.ibm.adopt}")
	private String ibmAdoptTopic;

	@Value("${hdfs.folder.location}")
	private String OUTPUT_FILE_BASE_LOCATION;

	@Value("${output.filename.prefix}")
	private String OUTPUT_FILENAME_PREFIX;

	private final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");

	public static void main(String[] args) {
		SpringApplication.run(KafkaSparkStreamingReaderApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		SparkConf conf = new SparkConf().setAppName("IBM_Spark_Streaming_Kafka_Reader").setMaster("local[*]");
		conf.set("spark.streaming.stopGracefullyOnShutdown", "true");

		//JavaStreamingContext ssc = new JavaStreamingContext(conf, new Duration(1000 * 60));
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaStreamingContext ssc = new JavaStreamingContext(sc, new Duration(1000 * 60));
		
		Map<String, String> kafkaParams = new HashMap<>();
		kafkaParams.put("metadata.broker.list", bootstrapServers);
		Set<String> topics = new HashSet<>(Arrays.asList(ibmAdoptTopic.split(",")));

		JavaPairInputDStream<String, String> directKafkaStream = KafkaUtils.createDirectStream(ssc, String.class,
				String.class, StringDecoder.class, StringDecoder.class, kafkaParams, topics);

		directKafkaStream.foreachRDD(rdd -> {
			if (!rdd.isEmpty()) {
				String fileSuffix = DATE_TIME_FORMAT.format(new Date());
				JavaRDD<String> jrdd = rdd.map(x -> x._2);
				jrdd.saveAsTextFile(OUTPUT_FILE_BASE_LOCATION + OUTPUT_FILENAME_PREFIX + fileSuffix);

				// rdd.saveAsTextFile(OUTPUT_FILE_BASE_LOCATION +
				// OUTPUT_FILENAME_PREFIX + fileSuffix);
				// rdd.foreach(record -> System.out.println(record._2));
				// jrdd.foreach(record -> System.out.println(record));
			}
		});

		ssc.start();
		ssc.awaitTermination();
	}
}
