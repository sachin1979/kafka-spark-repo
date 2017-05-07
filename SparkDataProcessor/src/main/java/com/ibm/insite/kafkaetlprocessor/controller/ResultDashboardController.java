package com.ibm.insite.kafkaetlprocessor.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ibm.insite.kafkaetlprocessor.dataobject.ResultStats;
import com.ibm.insite.kafkaetlprocessor.repository.ResultStatsJdbcRepository;

@Controller
public class ResultDashboardController {

	@Autowired
	private ResultStatsJdbcRepository respository;

	@RequestMapping(value = { "/", "/dashboard" }, method = RequestMethod.GET)
	public ModelAndView orderLaptop(HttpServletRequest request) throws JsonProcessingException {
		List<ResultStats> resultList = respository.findAll();

		ModelAndView view = new ModelAndView("dashboard");
		view.addObject("result", resultList);
		return view;
	}
}
