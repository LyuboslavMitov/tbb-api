package com.tbb.demo.dao;

import com.tbb.demo.model.Route;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RoutesRepository extends MongoRepository<Route,Long> {
    List<Route> findAllByCompanyId(String companyId);
}
