package com.lcwd.todo.dao;

import com.lcwd.todo.Helper.Helper;
import com.lcwd.todo.models.Todo;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TodoRowMapper implements RowMapper<Todo> {
    @Override
    public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
        Todo todo = new Todo();
        todo.setId(rs.getInt("id"));
        todo.setTitle(rs.getString("title"));
        todo.setContent(rs.getString("content"));
        todo.setStatus(rs.getString("status"));
        todo.setAddedDate(Helper.parseDate((LocalDateTime) rs.getObject("addedDate")));
        todo.setAddedDate(Helper.parseDate((LocalDateTime) rs.getObject("addedDate")));
        //logger.info("TODO one by one: {}", todo);
        return todo;
    }
}
