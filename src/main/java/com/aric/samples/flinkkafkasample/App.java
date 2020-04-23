package com.aric.samples.flinkkafkasample;

import java.util.Objects;
import java.util.Properties;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aric.samples.flinkkafkasample.agg.CustomerAggregatorByCountry;
import com.aric.samples.flinkkafkasample.model.Customer;

public class App {
	private static final Logger LOG = LoggerFactory.getLogger(App.class);
	private static final ObjectMapper OM = new ObjectMapper();

	public static void main(String[] args) throws Exception {

		final StreamExecutionEnvironment see = StreamExecutionEnvironment.getExecutionEnvironment();

		Properties properties = new Properties();
		properties.setProperty("bootstrap.servers", "localhost:9092");
		properties.setProperty("group.id", "customerAnalytics");
		LOG.info("Properties set {}", properties);

		FlinkKafkaConsumer<String> kafkaSource = new FlinkKafkaConsumer<>("test", new SimpleStringSchema(), properties);
		DataStream<String> stream = see.addSource(kafkaSource);

		stream.print();
	}

}
