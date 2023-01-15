package com.example.demo.common;

import com.example.demo.persistence.entity.TodoEntryEty;
import com.example.demo.rest.dto.TodoEntryDto;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public TodoEntryDto mapTodoEntryEtyToTodoEntryDto(TodoEntryEty todoEntryEty) {
        return new TodoEntryDto()
                .withCompleted(todoEntryEty.isCompleted())
                .withTitle(todoEntryEty.getTitle())
                .withDescription(todoEntryEty.getDescription())
                .withId(todoEntryEty.getId());
    }

    public TodoEntryEty mapTodoEntryDtoToTodoEntryEty(TodoEntryDto todoEntryDto) {
        return new TodoEntryEty()
                .withCompleted(todoEntryDto.isCompleted())
                .withTitle(todoEntryDto.getTitle())
                .withDescription(todoEntryDto.getDescription())
                .withId(todoEntryDto.getId());
    }
}

