package com.reserveme.cinema.repository;

import com.reserveme.cinema.model.Shows;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowsRepository extends ReactiveMongoRepository<Shows, String> {
}
