package com.ibm.insite.kafkaetlprocessor.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ibm.insite.kafkaetlprocessor.dataobject.ResultStats;

@Repository
public class ResultStatsJdbcRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<ResultStats> findAll() {
		List<ResultStats> result = jdbcTemplate.query("SELECT * FROM ResultStats",
				(rs, rowNum) -> new ResultStats(rs.getString("productbrand"), rs.getString("productname"), rs.getInt("numberoforders")));
		return result;
	}
}
