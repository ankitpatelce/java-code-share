package com.lcwd.todo;

import com.lcwd.todo.dao.TodoDao;
import com.lcwd.todo.models.Todo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;
import java.util.List;


@SpringBootApplication
public class TodoManagerApplication implements CommandLineRunner {

	Logger logger= LoggerFactory.getLogger(TodoManagerApplication.class);
	@Autowired
	private TodoDao todoDao;

	public static void main(String[] args) {
		SpringApplication.run(TodoManagerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		System.out.println("Application Started:");
//		JdbcTemplate jdbcTemplate = todoDao.getJdbcTemplate();
//		logger.info("Templet object: {}", jdbcTemplate);
//		logger.info("Templet object: {}", jdbcTemplate.getDataSource());


		/* insert Operation test */
		Todo todo = new Todo();
		//todo.setId(4);  // it is primary key and auto increment. So needed
		todo.setTitle("Second Todo item");
		todo.setContent("Make application with Java and spring Boot.");
		todo.setStatus("Not started.");
		todo.setAddedDate(new Date());
		todo.setTodoDate(new Date());
		Todo todo1 = todoDao.saveTodo(todo);

		/* Get Operation test */
		Todo todo2 = todoDao.getTodo(5);
		logger.info("todo 2 {}",todo2);
		Todo todo3 = todoDao.getTodo(6);
		logger.info("todo 3 {}",todo3);

		/* Get All Operation test */
		List<Todo> todos = todoDao.getAllTodos();
		logger.info("Output: List of ToDo: {}", todos);

		/* Update Operation test */
		Todo todo4 = new Todo();
		todo4.setTitle("Spring Boot learn");
		todo4.setContent("Make application of springBOOT");
		todo4.setStatus("Working on it");
		todo4.setAddedDate(new Date());
		todo4.setTodoDate(new Date());
		logger.info("New Todo: {}",todo4);
		todoDao.updateTodo( 2, todo4);

		//delete Todo test
		todoDao.deleteTodo(13);

		//delete Multiple Todos test
		todoDao.deleteMultipleTodo(new int[]{14,15,16});

		//***** test With Row Mapper method START ***********************************************

		/* Get Operation test with Row Mapper */
		Todo todo10 = todoDao.getTodoWithRowMapper(5);
		logger.info("Output :Get With Row Mapper : {}",todo10);

		/* Get AllOperation test with Row Mapper */
		List<Todo> todos11 = todoDao.getAllTodosWithRowMapper();
		logger.info("Output: Gell All with Row Mapper: {}", todos11);

		//***** test With Row Mapper method END ***********************************************

	}


}
