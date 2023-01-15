package com.example.demo.persistence.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@With
public class TodoEntryEty {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "todo_entry_id")
    @SequenceGenerator(name = "todo_entry_id", sequenceName = "todo_entry_id", allocationSize = 1)
    private Long id;
    private String title;
    private String description;
    private boolean completed;
}
