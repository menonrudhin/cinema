package com.reserveme.cinema.filter;

import com.reserveme.cinema.error.ErrorUtils;
import com.reserveme.cinema.model.IdempotencyId;
import com.reserveme.cinema.repository.IdempotencyIdRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Component
public class IdempotencyFilter implements WebFilter, Ordered {

    private final IdempotencyIdRepository idempotencyIdRepository;

    private ErrorUtils errorUtils;

    public IdempotencyFilter(@NonNull IdempotencyIdRepository idempotencyIdRepository, @NonNull ErrorUtils errorUtils) {
        this.idempotencyIdRepository = idempotencyIdRepository;
        this.errorUtils = errorUtils;
    }

    @Override
    public int getOrder() {
        return -1;
    }

    @Override
    public @NonNull Mono<Void> filter(@NonNull ServerWebExchange exchange, @NonNull WebFilterChain chain) {
        String idempotencyId = exchange.getRequest().getHeaders().getFirst("Idempotency-Key");

        if (!StringUtils.hasText(idempotencyId)) {
            return chain.filter(exchange);
        }

        return idempotencyIdRepository.findByIdempotencyId(idempotencyId)
                .flatMap(existingId -> errorUtils.buildConflictResponse(exchange, idempotencyId))
                .switchIfEmpty(Mono.defer(() -> {
                    IdempotencyId id = new IdempotencyId();
                    id.setIdempotencyId(idempotencyId);
                    id.setCreationDateTime(LocalDateTime.now());
                    return idempotencyIdRepository.save(id)
                            .then(chain.filter(exchange))
                            .onErrorResume(DuplicateKeyException.class, e -> errorUtils.buildConflictResponse(exchange, idempotencyId));
                }));
    }


}
