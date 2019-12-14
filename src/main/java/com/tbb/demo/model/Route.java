package com.tbb.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("routes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    @Id
    private Long id;
    private String startPoint;
    private String endPoint;
    private List<String> stops;
    private String duration;
    private Double price;
    private Integer distance;
    private String company;
    private String companyId;
}
