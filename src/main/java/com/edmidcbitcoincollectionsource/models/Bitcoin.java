package com.edmidcbitcoincollectionsource.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
@Builder
public class Bitcoin {
    private Date obtainedDate;
    private Date createdDate;
    private String currency;
    private String name;
    private String symbol;
    private Integer rank;
    private Double volumeUsd24Hr;
    private Double price;
}
