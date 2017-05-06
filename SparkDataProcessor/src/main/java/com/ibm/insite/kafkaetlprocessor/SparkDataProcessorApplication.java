package com.ibm.insite.kafkaetlprocessor;

import java.util.Iterator;
import java.util.Map;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.google.gson.Gson;
import com.ibm.insite.kafkaetlprocessor.dataobject.KafkaInputOrderMessage;
import com.ibm.insite.kafkaetlprocessor.dataobject.ResultStats;
import com.ibm.insite.kafkaetlprocessor.repository.ResultStatsRepository;

import scala.Tuple2;

@SpringBootApplication
@EnableJpaRepositories
public class SparkDataProcessorApplication extends SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	private ResultStatsRepository respository;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SparkDataProcessorApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SparkDataProcessorApplication.class, args);
	}

	public void run2(String... arg0) throws Exception {
		System.setProperty("spark.sql.warehouse.dir", "file:///C:/EclipseWorkSpaces/SparkETLProcessorResult/");

		final SparkSession sparkSession = SparkSession.builder().appName("Spark SQL Demo").master("local[5]")
				.getOrCreate();

		while (true) {
			// Load JSON file data into DataFrame using SparkSession
			final Dataset<Row> jsonDataFrame = sparkSession.read()
					.json("file:///C:/EclipseWorkSpaces/KafkaSparkStreamingOutput/*");
			// Print Schema to see column names, types and other metadata
			jsonDataFrame.printSchema();

			jsonDataFrame.createOrReplaceTempView("laptop");
			sparkSession.sql("SELECT productbrand, count(*) as numberoforders FROM laptop group by productbrand")
					.show();

			Thread.sleep(1000 * 60);
		}
	}

	@SuppressWarnings({ "resource", })
	@Override
	public void run(String... arg0) throws Exception {
		SparkConf conf = new SparkConf().setAppName("IBM_Spark_Streaming_Kafka_Reader").setMaster("local[*]");
		conf.set("spark.streaming.stopGracefullyOnShutdown", "true");

		JavaSparkContext sc = new JavaSparkContext(conf);

		while (true) {
			JavaRDD<String> data = sc.textFile("file:///C:/EclipseWorkSpaces/KafkaSparkStreamingOutput/*");

			JavaRDD<KafkaInputOrderMessage> resultRecords = data.map(line -> {
				Gson objGson = new Gson();
				KafkaInputOrderMessage rd = objGson.fromJson(line, KafkaInputOrderMessage.class);
				return rd;
			});

			JavaPairRDD<String, Integer> rddBrandCount = resultRecords
					.mapToPair(e -> new Tuple2<String, Integer>(e.getProductbrand(), 1));

			JavaPairRDD<String, Integer> rddGroupByKeyResult = rddBrandCount.reduceByKey((a, b) -> a + b);
			Map<String, Integer> resultMap = rddGroupByKeyResult.collectAsMap();
			System.out.println(resultMap);

			Iterator it = resultMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				String key = (String) pair.getKey();
				Integer value = Integer.parseInt(pair.getValue().toString());

				ResultStats rs = respository.findByProductbrand(key);
				if (rs != null) {
					rs.setNumberoforders(value);
					System.out.println("Found: Updating: " + rs);
				}
				else {
					rs = new ResultStats(key, value);
					System.out.println("Not Found: Saving: " + rs);
				}
				respository.save(rs);
			}
			
			Thread.sleep(1000 * 60);
		}
	}
}
