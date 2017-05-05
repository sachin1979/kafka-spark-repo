package com.ibm.insite.kafkasender.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ibm.insite.kafkasender.dataobject.KafkaInputOrderMessage;
import com.ibm.insite.kafkasender.service.KafkaSenderService;

@Controller
public class IBMInsightKafkaProducerController {

	@Autowired
	private KafkaSenderService kafkaSenderService;

	@RequestMapping(value = {"/", "/getOrderForm"}, method = RequestMethod.GET)
	public ModelAndView orderLaptop(HttpServletRequest request) throws JsonProcessingException {
		ModelAndView view = new ModelAndView("orderLaptop");
		return view;
	}

	@RequestMapping(value = "/placeOrder", method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	public ResponseEntity<Map<String, Object>> placeOrder(@RequestBody KafkaInputOrderMessage kakfaInputOrderMessage,
			final HttpServletRequest request, final HttpServletResponse response)
			throws ServletRequestBindingException, SQLException, IOException, InterruptedException, ExecutionException {
		
		kafkaSenderService.sendMessageToKafkaTopic(kakfaInputOrderMessage);
		Map<String, Object> returnValues = new HashMap<>();
		returnValues.put("STATUS", "SUCCESS");
		return new ResponseEntity<Map<String, Object>>(returnValues, HttpStatus.OK);
	}
}
