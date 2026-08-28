package com.reserveme.cinema.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "shows")
@Data
public class Shows {
    @Id
    private String showId;
    private String showName;
    private LocalDateTime showTime;
    private long runLength;
}
