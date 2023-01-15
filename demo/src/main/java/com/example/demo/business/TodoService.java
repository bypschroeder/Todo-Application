package com.example.demo.business;

import com.example.demo.exceptions.NoDataFoundException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.persistence.entity.TodoEntryEty;
import com.example.demo.persistence.repository.TodoEntryRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TodoService {

    private final TodoEntryRepository todoEntryRepository;

    public TodoService(TodoEntryRepository todoEntryRepository) {
        this.todoEntryRepository = todoEntryRepository;
    }

    public List<TodoEntryEty> getTodos() {
        return todoEntryRepository.findAll();
    }

    public TodoEntryEty getTodo(Long id) {
        var optional = todoEntryRepository.findById(id);
        if (!optional.isPresent()) {
            throw new ResourceNotFoundException("Todo is not found for the id:" + id);
        }
        return optional.orElse(null);
    }

    public List<TodoEntryEty> getTodosByTitle(String title) {
        var optional = todoEntryRepository.findAllByTitle(title);
        return optional.orElse(null);
    }

    public List<TodoEntryEty> getTodoByCompleted(boolean completed) {
        var optional = todoEntryRepository.findAllByCompleted(completed);
        return optional.orElse(null);
    }

    public List<TodoEntryEty> getTodosByCriteria(String title, boolean completed) {
        return todoEntryRepository.findAllByTitleAndCompleted(title, completed);
    }

    public TodoEntryEty createTodo(TodoEntryEty todoEntryEty) {
        if (todoEntryEty.getTitle() != null) {
            return todoEntryRepository.save(todoEntryEty);
        }
        throw new NoDataFoundException("No title found");
    }

    public void deleteTodo(Long id) {
        boolean exists = todoEntryRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("todo with id " + id + " does not exist");
        }
        todoEntryRepository.deleteById(id);
    }

    @Transactional
    public TodoEntryEty updateTodo(Long id, String title, String description, boolean completed) {
        TodoEntryEty todoEntryEty = todoEntryRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("todo with id " + id + " does not exist"));
        if (title != null && title.length() > 0 && !title.contentEquals(todoEntryEty.getTitle())) {
            todoEntryEty.setTitle(title);
        }

        if (todoEntryEty.getDescription() == null || (description != null && description.length() > 0 && !description.contentEquals(todoEntryEty.getDescription()))) {
            todoEntryEty.setDescription(description);
        }

//        if (todoEntryEty.isCompleted() != completed) {
            todoEntryEty.setCompleted(completed);
//        }
        return todoEntryRepository.save(todoEntryEty);
    }
}
