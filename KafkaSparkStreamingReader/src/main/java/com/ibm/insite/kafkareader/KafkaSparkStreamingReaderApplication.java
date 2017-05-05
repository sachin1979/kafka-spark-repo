package com.ibm.insite.kafkareader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.spark.SparkConf;
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

	public static void main(String[] args) {
		SpringApplication.run(KafkaSparkStreamingReaderApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		SparkConf conf = new SparkConf().setAppName("IBM_Spark_Streaming_Kafka_Reader").setMaster("local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);
		JavaStreamingContext ssc = new JavaStreamingContext(sc, new Duration(2000));

		Map<String, String> kafkaParams = new HashMap<>();
		kafkaParams.put("metadata.broker.list", bootstrapServers);
		Set<String> topics = Collections.singleton(ibmAdoptTopic);

		JavaPairInputDStream<String, String> directKafkaStream = KafkaUtils.createDirectStream(ssc, String.class,
				String.class, StringDecoder.class, StringDecoder.class, kafkaParams, topics);

		directKafkaStream.foreachRDD(rdd -> {
			rdd.foreach(record -> System.out.println(record._2));
		});

		ssc.start();
		ssc.awaitTermination();
	}
}
