package com.reserveme.cinema.repository;

import com.reserveme.cinema.model.IdempotencyId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface IdempotencyIdRepository extends ReactiveMongoRepository<IdempotencyId, String> {
    Mono<IdempotencyId> findByIdempotencyId(String idempotencyId);
}
