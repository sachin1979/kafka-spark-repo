package com.ibm.insite.kafkasender.service;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.insite.kafkasender.dataobject.KafkaInputOrderMessage;

@Service
public class KafkaSenderService {

	@Value("${topic.name.ibm.adopt}")
	private String ibmAdoptTopic;

	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessageToKafkaTopic(KafkaInputOrderMessage kakfaInputOrderMessage) throws JsonProcessingException, InterruptedException, ExecutionException {
		String jsonMessage = objectMapper.writeValueAsString(kakfaInputOrderMessage);
		ListenableFuture<SendResult<String, String>> kafkaResult = kafkaTemplate.send(ibmAdoptTopic, jsonMessage);
		SendResult<String, String> sendResult = kafkaResult.get();
		System.out.printf("Message: %s has been sent to topic %s ", sendResult.getProducerRecord(), ibmAdoptTopic);
	}
}
