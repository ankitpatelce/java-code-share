package com.lcwd.todo.controllers;

import com.lcwd.todo.models.Todo;
import com.lcwd.todo.services.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/todos")
public class TodoController {

    Logger logger = LoggerFactory.getLogger(TodoController.class);

    @Autowired
    private TodoService todoService;

    Random random = new Random();

//    @PostMapping
//    public Todo createTodoHandler(@RequestBody Todo todo) {
//        // Create todo
//        int id = random.nextInt(999999);
//        todo.setId(id);
//        logger.info("Create Todo : {} ", todo.toString());
//        // Create service to create to do
//        Todo todo1 = todoService.createTodo(todo);
//
//        return todo1;
//    }

    @PostMapping
    public ResponseEntity<Todo> createTodoHandler(@RequestBody Todo todo) {
        // Create todo
        int id = random.nextInt(999999);
        todo.setId(id);

        Date currentDate = new Date();
        logger.info("Current Date : {} | Todo date: {} ", currentDate, todo.getTodoDate());
        todo.setAddedDate(currentDate);

        logger.info("Create Todo : {} ", todo.toString());
        // Create service to create to do
        Todo todo1 = todoService.createTodo(todo);

        return new ResponseEntity<>(todo1, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos(){
        List<Todo> allTodos =todoService.getAllTodos();
        return new ResponseEntity<>(allTodos,HttpStatus.OK);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<Todo> getTodo(@PathVariable int todoId){
        Todo todo = todoService.getTodo(todoId);
        //return new ResponseEntity<>(todo, HttpStatus.OK);
        return ResponseEntity.ok(todo);
    }

    // Updae todo
    @PutMapping("/{todoId}")
    public ResponseEntity<Todo> updateTodo(@RequestBody Todo newTodoDetails,@PathVariable int todoId){
      Todo  updatedTodo=todoService.updateTodo(todoId,newTodoDetails);
      return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping("/{todoId}")
    public ResponseEntity<String> deleteTodo(@PathVariable int todoId){
        todoService.deleteTodo(todoId);
        return ResponseEntity.ok("This Todo is delete sucessfully");
    }
}