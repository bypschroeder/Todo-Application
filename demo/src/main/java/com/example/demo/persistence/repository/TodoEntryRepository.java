package com.example.demo.persistence.repository;

import com.example.demo.persistence.entity.TodoEntryEty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoEntryRepository extends JpaRepository<TodoEntryEty, Long> {
    Optional<List<TodoEntryEty>> findAllByTitle(String title);
    Optional<List<TodoEntryEty>> findAllByCompleted(boolean completed);

    List<TodoEntryEty> findAllByTitleAndCompleted(String title, boolean completed);
}
