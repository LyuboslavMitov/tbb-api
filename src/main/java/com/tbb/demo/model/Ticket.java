package com.tbb.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
    @Id
    private Long id;
    private String startPoint;
    private String endPoint;
    private List<String> stops;
    private String duration;
    private Double price;
    private String distance;
    private String company;
    private String date;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userId;

}
