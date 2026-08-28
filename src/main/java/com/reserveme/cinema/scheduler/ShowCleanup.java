package com.reserveme.cinema.scheduler;

import com.reserveme.cinema.model.Shows;
import com.reserveme.cinema.repository.ShowsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ShowCleanup {

    private final ShowsRepository showsRepository;

    @Autowired
    public ShowCleanup(ShowsRepository showsRepository) {
        this.showsRepository = showsRepository;
    }

    // Run every 15 minutes
    @Scheduled(cron = "*/10 * * * * *")
    public void showCleanup() {
        System.out.println("SCHEDULER RUNNING");
        LocalDateTime now = LocalDateTime.now();

        // delete returns Mono<Void>
        showsRepository.findAll()
                .filter(show -> {
                    LocalDateTime end = show.getShowTime().plusMinutes(show.getRunLength());
                    boolean expired = end.isBefore(now) || end.isEqual(now);
                    if (expired) {
                        System.out.println("Show to be removed for: " + show.getShowName() + " end=" + end);
                    }
                    return expired;
                })
                .flatMap(showsRepository::delete)
                .doOnError(err -> System.err.println("Error removing shows: " + err.getMessage()))
                .subscribe();
    }
}
