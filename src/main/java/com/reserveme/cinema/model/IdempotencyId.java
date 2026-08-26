package com.reserveme.cinema.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "idempotency-ids")
@Data
public class IdempotencyId {
    @Id
    private String id;
    @Indexed(unique=true)
    private String idempotencyId;
    private LocalDateTime creationDateTime;
}
