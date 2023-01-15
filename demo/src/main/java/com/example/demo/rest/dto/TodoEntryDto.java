package com.example.demo.rest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class TodoEntryDto {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
}
