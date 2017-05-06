package com.ibm.insite.kafkaetlprocessor.repository;

import org.springframework.data.repository.CrudRepository;

import com.ibm.insite.kafkaetlprocessor.dataobject.ResultStats;

public interface ResultStatsRepository extends CrudRepository<ResultStats, String> {

}
