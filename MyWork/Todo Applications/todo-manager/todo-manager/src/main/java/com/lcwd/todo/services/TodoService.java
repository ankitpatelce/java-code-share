package com.lcwd.todo.services;

import com.lcwd.todo.models.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoService {
    Logger logger = LoggerFactory.getLogger(TodoService.class);

    List<Todo> todos= new ArrayList<>();

    //create todo method
    public Todo createTodo(Todo todo){
        //create...
        todos.add(todo);
        logger.info("Todos : {}", this.todos.toString());
        return todo;
    }

    public List<Todo> getAllTodos() {
        return this.todos;
    }

    public Todo getTodo(int todoId) {
        Todo todo1=todos.stream().filter(todo -> todoId==todo.getId()).findAny().get();
        logger.info("TODO1 {}", todo1);
        return  todo1;
    }

    public Todo updateTodo(int todoId, Todo todo) {
        List<Todo> newUpdatedList = todos.stream().map(t -> {
            if(t.getId() == todoId){
                // Perform Update
                t.setTitle(todo.getTitle());
                t.setContent(todo.getContent());
                t.setStatus(todo.getStatus());
                return t;
            }else{
                return t;
            }

        }).collect(Collectors.toList());

        todos=newUpdatedList;

        todo.setId(todoId);
        return todo;
    }
    public void deleteTodo(int todoId){
        logger.info(" delete Todo service called");
        List<Todo> newList = todos.stream().filter(t -> t.getId() != todoId).collect(Collectors.toList());
        todos=newList;
    }
}
