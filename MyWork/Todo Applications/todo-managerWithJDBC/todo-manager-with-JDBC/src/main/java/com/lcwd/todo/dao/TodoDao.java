package com.lcwd.todo.dao;

import com.lcwd.todo.Helper.Helper;
import com.lcwd.todo.models.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TodoDao {
    Logger logger= LoggerFactory.getLogger(TodoDao.class);

    JdbcTemplate jdbcTemplate;
    public TodoDao(@Autowired JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        // Create table if not exists.

        String createTableQuery =
                "create table IF NOT EXISTS todos(id int AUTO_INCREMENT primary key, title varchar(100) not null, content varchar(4500) not null,status varchar(100) not null, addedDate datetime, todoDate datetime) ";
        int rows = jdbcTemplate.update(createTableQuery);
        logger.info("TODO TABLE CREATED : {}", rows);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Save todo in database
    public Todo saveTodo(Todo todo){
        String insertQuery ="insert into todos(id,title,content,status,addedDate,todoDate) values (?,?,?,?,?,?)";
        int rows = jdbcTemplate.update(insertQuery, todo.getId(), todo.getTitle(),todo.getContent(),todo.getStatus(),todo.getAddedDate(),todo.getTodoDate());
        logger.info("JDBC OPERETION: {} inserted", rows);
        return todo;
    }

    // Get single item from todo datebase
    public Todo getTodo(int id) {
        String selectQuery = "select * from todos WHERE id = ?";
        Map<String, Object> todoData = jdbcTemplate.queryForMap(selectQuery, id);
        logger.info("TODO Data: {}",todoData);

        Todo todo = new Todo();
        todo.setId((int) todoData.get("id"));
        todo.setTitle((String) todoData.get("title"));
        todo.setContent((String) todoData.get("content"));
        todo.setStatus((String) todoData.get("status"));
        todo.setAddedDate(Helper.parseDate((LocalDateTime) todoData.get("addedDate")));
        todo.setAddedDate(Helper.parseDate((LocalDateTime) todoData.get("addedDate")));
        logger.info("TODO: {}",todo);
        return todo;
    }

    //  Get all Todos
    public List<Todo> getAllTodos(){
        String query = "select * from todos";
        List<Map<String, Object>> outputMaps = jdbcTemplate.queryForList(query);

        List<Todo> todoList = outputMaps.stream().map((map) -> {
            Todo todo = new Todo();
            todo.setId((int) map.get("id"));
            todo.setTitle((String) map.get("title"));
            todo.setContent((String) map.get("content"));
            todo.setStatus((String) map.get("status"));
            todo.setAddedDate(Helper.parseDate((LocalDateTime) map.get("addedDate")));
            todo.setAddedDate(Helper.parseDate((LocalDateTime) map.get("addedDate")));
            logger.info("TODO one by one: {}", todo);
            return todo;
        }).collect(Collectors.toList());
        logger.info("List of ToDo: {}", todoList);
        return todoList;
    }

    //update todo;
    public Todo updateTodo(int id, Todo newTodo){
        String query = " update todos set title=?,content=?,status=?,addedDate=?,todoDate=? where id = ?";
        int update = jdbcTemplate.update(query, newTodo.getTitle(), newTodo.getContent(), newTodo.getStatus(), newTodo.getAddedDate(), newTodo.getTodoDate(), id);
        logger.info("updated {}",update);
        newTodo.setId(id);
        return newTodo;
    }

    //Delete Todo
    public void deleteTodo(int id){
        String query="delete from todos where id=?";
        int update = jdbcTemplate.update(query, id);
        logger.info("Deleted {}",update);
    }

    //Delete Multiple Todos
    public void deleteMultipleTodo(int ids[]){
        String query="delete from todos where id=?";

        int[] ints = jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                int idx = ids[i];
                ps.setInt(1, idx);
            }
            @Override
            public int getBatchSize() {
                return ids.length;
            }
        });

        for(int i : ints){
            logger.info("Deleted {}",i);
        }
    }

    //==========================================================
    // With the HELP of RowMapper
    public Todo getTodoWithRowMapper(int id) {
        String query = "select * from todos WHERE id = ?";
        //Map<String, Object> todoData = jdbcTemplate.queryForMap(selectQuery, id);
        Todo todo = jdbcTemplate.queryForObject(query, new TodoRowMapper(), id);
        logger.info("TODO WithRowMapper: {}",todo);
        return todo;
    }

    //  Get all Todos
    public List<Todo> getAllTodosWithRowMapper(){
        String query = "select * from todos";
        //List<Map<String, Object>> outputMaps = jdbcTemplate.queryForList(query);

        List<Todo> todoList = jdbcTemplate.query(query, new TodoRowMapper());
        logger.info("List of ToDo WithRowMapper: {}", todoList);
        return todoList;
    }

}
