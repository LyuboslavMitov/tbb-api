package com.tbb.demo.dao;

import com.tbb.demo.model.Route;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoutesRepository extends MongoRepository<Route,Long> {
}
