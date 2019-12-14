package com.tbb.demo.dao;

import com.tbb.demo.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TicketsRepository extends MongoRepository<Ticket,Long> {
    List<Ticket> findAllByUserId(String userId);
 }
