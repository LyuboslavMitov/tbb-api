package com.tbb.demo.dao;

import com.tbb.demo.model.Ticket;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketsRepository extends MongoRepository<Ticket,Long> {
}
