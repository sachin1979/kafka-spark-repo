package com.ibm.insite.kafkaetlprocessor.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ibm.insite.kafkaetlprocessor.dataobject.ResultStats;
import com.ibm.insite.kafkaetlprocessor.repository.ResultStatsRepository;

@Controller
public class ResultDashboardController {

	@Autowired
	private ResultStatsRepository respository;

	@RequestMapping(value = {"/", "/dashboard"}, method = RequestMethod.GET)
	public ModelAndView orderLaptop(HttpServletRequest request) throws JsonProcessingException {
		
		Iterable<ResultStats> result = respository.findAll();
		
		List<ResultStats> resultList = new ArrayList<>();
		for (ResultStats resultStats : result) {
			resultList.add(resultStats);
		}
		
		ModelAndView view = new ModelAndView("dashboard");
		view.addObject("result", resultList);
		return view;
	}

}
