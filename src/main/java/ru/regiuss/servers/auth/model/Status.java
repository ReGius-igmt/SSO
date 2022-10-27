package ru.regiuss.servers.auth.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Status {
    private String name;
    private Long startDate;
}
