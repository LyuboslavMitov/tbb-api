package com.tbb.demo.dao;

import com.tbb.demo.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketsRepository extends MongoRepository<Ticket,String> {
    List<Ticket> findAllByUserId(String userId);
 }
