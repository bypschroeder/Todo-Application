package com.example.demo.rest.impl;

import com.example.demo.business.TodoService;
import com.example.demo.common.Mapper;
import com.example.demo.rest.dto.TodoEntryDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todo-controller")
@CrossOrigin("*")
public class TodoControllerImpl {
    private final TodoService todoService;
    private final Mapper mapper;

    public TodoControllerImpl(TodoService todoService, Mapper mapper) {
        this.todoService = todoService;
        this.mapper = mapper;
    }

    @GetMapping("todos")
    @Operation(
            tags = {"TodoItems"},
            operationId = "todos",
            summary = "This is the summary",
            description = "this is the description",
            externalDocs = @ExternalDocumentation(url = "http://todo.com/docs", description = "For more details check the link"),
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TodoEntryDto.class)), mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(name = "Success 1", value = "Object"),
                                    @ExampleObject(name = "Success 2", value = "Object")}),
                    description = "Response successful")}
    )
    public ResponseEntity<List<TodoEntryDto>> todos() {
        List<TodoEntryDto> todos = todoService
                .getTodos()
                .stream()
                .map(mapper::mapTodoEntryEtyToTodoEntryDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(todos);
    }

    @GetMapping("todo/{id}")
    @Operation(
            tags = {"TodoItems"},
            operationId = "todo",
            summary = "This is the summary",
            description = "this is the description",
            parameters = {@Parameter(name = "id", description = "The path variable.", example = "1",
                    in = ParameterIn.PATH)},
            externalDocs = @ExternalDocumentation(url = "http://todo.com/docs", description = "For more details check the link"),
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = TodoEntryDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(name = "Success 1", value = "Object"),
                                    @ExampleObject(name = "Success 2", value = "Object")}),
                    description = "Response successful")}
    )
    public ResponseEntity<TodoEntryDto> todo(@PathVariable Long id) {
        var todoEty = todoService.getTodo(id);
        return ResponseEntity.ok(mapper.mapTodoEntryEtyToTodoEntryDto(todoEty));
    }

    @GetMapping("todos/criteria")
    @Operation(
            tags = {"TodoItems"},
            operationId = "getTodosByCriteria",
            summary = "This is the summary",
            description = "this is the description",
            parameters = {  @Parameter(name = "title", description = "The Query variable.", example = "Homework", in = ParameterIn.QUERY),
                            @Parameter(name = "completed", description = "The Query variable", example = "true", in = ParameterIn.QUERY)},
            externalDocs = @ExternalDocumentation(url = "http://todo.com/docs", description = "For more details check the link"),
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = TodoEntryDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(name = "Success 1", value = "Object"),
                                    @ExampleObject(name = "Success 2", value = "Object")}),
                    description = "Response successful")}
    )
    public ResponseEntity<List<TodoEntryDto>> getTodosByCriteria(@RequestParam String title, @RequestParam boolean completed) {
        var todoEtys = todoService
                .getTodosByCriteria(title, completed)
                .stream()
                .map(mapper::mapTodoEntryEtyToTodoEntryDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(todoEtys);
    }

    @GetMapping("todos/title")
    @Operation(
            tags = {"TodoItems"},
            operationId = "getTodoByTitle",
            summary = "This is the summary",
            description = "this is the description",
            parameters = {  @Parameter(name = "title", description = "The Query variable.", example = "Homework", in = ParameterIn.QUERY)},
            externalDocs = @ExternalDocumentation(url = "http://todo.com/docs", description = "For more details check the link"),
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = TodoEntryDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(name = "Success 1", value = "Object"),
                                    @ExampleObject(name = "Success 2", value = "Object")}),
                    description = "Response successful")}
    )
    public ResponseEntity<List<TodoEntryDto>> getTodosByTitle(@RequestParam String title) {
        var todoEty = todoService
                .getTodosByTitle(title)
                .stream()
                .map(mapper::mapTodoEntryEtyToTodoEntryDto)
                .collect(Collectors.toList());;
        return ResponseEntity.ok(todoEty);
    }

    @GetMapping("todos/completed")
    @Operation(
            tags = {"TodoItems"},
            operationId = "getTodoByCompleted",
            summary = "This is the summary",
            description = "this is the description",
            parameters = {  @Parameter(name = "completed", description = "The Query variable.", example = "true", in = ParameterIn.QUERY)},
            externalDocs = @ExternalDocumentation(url = "http://todo.com/docs", description = "For more details check the link"),
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = TodoEntryDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(name = "Success 1", value = "Object"),
                                    @ExampleObject(name = "Success 2", value = "Object")}),
                    description = "Response successful")}
    )
    public ResponseEntity<List<TodoEntryDto>> getTodoByCompleted(@RequestParam boolean completed) {
        var todoEty = todoService
                .getTodoByCompleted(completed)
                .stream()
                .map(mapper::mapTodoEntryEtyToTodoEntryDto)
                .collect(Collectors.toList());;
        return ResponseEntity.ok(todoEty);
    }

    @PostMapping("todo")
    @Operation(
            tags = {"TodoItems"},
            operationId = "createTodo",
            summary = "This is the summary",
            description = "this is the description",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "This is the requestBody description",
                    content = @Content(schema = @Schema(implementation = TodoEntryDto.class))),
            externalDocs = @ExternalDocumentation(url = "http://todo.com/docs", description = "For more details check the link"),
            responses = {@ApiResponse(responseCode = "201",
                    content = @Content(schema = @Schema(implementation = TodoEntryDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = {@ExampleObject(name = "Success 1", value = "Object"),
                                    @ExampleObject(name = "Success 2", value = "Object")}),
                    description = "Response successful")}
    )
    public ResponseEntity<TodoEntryDto> createTodo(@RequestBody TodoEntryDto todoEntryDto) {
        var ety = todoService.createTodo(mapper.mapTodoEntryDtoToTodoEntryEty(todoEntryDto));
        var dto = mapper.mapTodoEntryEtyToTodoEntryDto(ety);
        return ResponseEntity
                .created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(dto.getId())
                                .toUri()
                )
                .body(dto);
    }

    @DeleteMapping(path = "todo/{id}")
    @Operation(
            tags = {"TodoItems"},
            operationId = "deleteTodo",
            summary = "This is the summary",
            description = "this is the description",
            parameters = {@Parameter(name = "id", description = "The path variable.", example = "1",
                    in = ParameterIn.PATH)},
            externalDocs = @ExternalDocumentation(url = "http://todo.com/docs", description = "For more details check the link"),
            responses = {@ApiResponse(responseCode = "204")}
    )
    public ResponseEntity<Void> deleteTodo(@PathVariable("id") Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "todo/{id}")
    @Operation(
            tags = {"TodoItems"},
            operationId = "updateTodo",
            summary = "This is the summary",
            description = "this is the description",
            parameters = {@Parameter(name = "id", description = "The path variable.", example = "1",
            in = ParameterIn.PATH)},
            externalDocs = @ExternalDocumentation(url = "http://todo.com/docs", description = "For more details check the link"),
            responses = {@ApiResponse(responseCode = "200",
                    content = @Content(schema = @Schema(implementation = TodoEntryDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE,
                    examples = {@ExampleObject(name = "Success 1", value = "Object"),
                                @ExampleObject(name = "Success 2", value = "Object")}),
                    description = "Response successful")}
    )
    public ResponseEntity<TodoEntryDto> updateTodo(
            @PathVariable("id") Long id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) boolean completed
    ) {
    var todoEty = todoService.updateTodo(id, title, description, completed);
    var todoDto = mapper.mapTodoEntryEtyToTodoEntryDto(todoEty);
    return ResponseEntity
            .ok(todoDto);
    }
}
