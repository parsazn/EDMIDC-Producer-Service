package com.edmidcbitcoincollectionsource.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ErrorStatus {
    private int status;
    private String message;
    private String topic;
}
