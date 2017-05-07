package com.ibm.insite.kafkaetlprocessor;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${spark.sql.warehouse.dir}")
	private String SPARK_SQL_WAREHOUSE_DIR;
	
	@Value("${hdfs.folder.location}")
	private String HDFS_FOLDER_LOCATION;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SparkDataProcessorApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SparkDataProcessorApplication.class, args);
	}

	/**
	 * This method provides an alternative solution using SPARK SQL for performing the same action.
	 * @param arg0
	 * @throws Exception
	 */
/*    public void alternativeMethodUnused(String... arg0) throws Exception {
		System.setProperty("spark.sql.warehouse.dir", SPARK_SQL_WAREHOUSE_DIR);

		final SparkSession sparkSession = SparkSession.builder().appName("HDFS ETL Processor").master("local[2]")
				.getOrCreate();

		while (true) {
			final Dataset<Row> jsonDataFrame = sparkSession.read().json(HDFS_FOLDER_LOCATION);
			jsonDataFrame.printSchema();

			jsonDataFrame.createOrReplaceTempView("laptop");
			sparkSession.sql("SELECT productbrand, count(*) as numberoforders FROM laptop group by productbrand")
					.show();

			Thread.sleep(1000 * 60);
		}
	}*/

	@SuppressWarnings({ "resource" })
	@Override
	public void run(String... arg0) throws Exception {
		SparkConf conf = new SparkConf().setAppName("HDFS ETL Processor").setMaster("local[*]");
		conf.set("spark.streaming.stopGracefullyOnShutdown", "true");

		JavaSparkContext sc = new JavaSparkContext(conf);

		/**
		 * For POC purpose, I am running this method every minute. In production environment, this will vary.
		 */
		while (true) {
			//Extract (E) the data from HDFS
			JavaRDD<String> data = sc.textFile(HDFS_FOLDER_LOCATION);

			//START: Transform (T) the database read from HDFS
			JavaRDD<KafkaInputOrderMessage> resultRecords = data.map(line -> {
				Gson objGson = new Gson();
				KafkaInputOrderMessage rd = objGson.fromJson(line, KafkaInputOrderMessage.class);
				return rd;
			});

			JavaPairRDD<GroupByFields, Integer> rddBrandCount = resultRecords
					.mapToPair(e -> new Tuple2<GroupByFields, Integer>(new GroupByFields(e.getProductbrand(), e.getProductname()), 1));

			JavaPairRDD<GroupByFields, Integer> rddGroupByKeyResult = rddBrandCount.reduceByKey((a, b) -> a + b);
			Map<GroupByFields, Integer> resultMap = rddGroupByKeyResult.collectAsMap();
			//END: Transform (T) the database read from HDFS

			Iterator<Entry<GroupByFields, Integer>> it = resultMap.entrySet().iterator();
			
			//START: Load (L) the transformed data into destination.
			//Here I am saving the transformed result into H2 database. This H2 database is queried by the controller.
			while (it.hasNext()) {
				Entry<GroupByFields, Integer> pair = it.next();
				GroupByFields key = (GroupByFields) pair.getKey();
				Integer value = Integer.parseInt(pair.getValue().toString());

				ResultStats rs = respository.findByProductbrandAndProductname(key.productbrand, key.productname);
				if (rs != null) {
					rs.setNumberoforders(value);
				}
				else {
					rs = new ResultStats(key.productbrand, key.productname, value);
				}
				respository.save(rs);
			}
			//END: Load (L) the transformed data into destination.
			
			Thread.sleep(1000 * 60);
		}
	}
}

class GroupByFields implements Serializable {
	private static final long serialVersionUID = 1L;

	public String productbrand;
	public String productname;
	
	public GroupByFields(String productbrand, String productname) {
		super();
		this.productbrand = productbrand;
		this.productname = productname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((productbrand == null) ? 0 : productbrand.hashCode());
		result = prime * result + ((productname == null) ? 0 : productname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GroupByFields other = (GroupByFields) obj;
		if (productbrand == null) {
			if (other.productbrand != null)
				return false;
		} else if (!productbrand.equals(other.productbrand))
			return false;
		if (productname == null) {
			if (other.productname != null)
				return false;
		} else if (!productname.equals(other.productname))
			return false;
		return true;
	}
	
}
