// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package org.acme.example.controller;

import org.acme.example.dao.TodoItemRepository;
import org.acme.example.model.TodoItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@RestController
public class TodoListController {

    private static Logger logger = LoggerFactory.getLogger(TodoListController.class);
    private final Random random = new Random();

    @Autowired
    private TodoItemRepository todoItemRepository;


    /**
     * HTTP GET
     */
    @RequestMapping(value = "/api/todolist/{index}",
        method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getTodoItem(@PathVariable("index") String index) {
        logger.info("GET request access '/api/todolist/{}' path.", index);
        try {
            return new ResponseEntity<TodoItem>(todoItemRepository.findById(index).get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(index + " not found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * HTTP GET ALL
     */
    @RequestMapping(value = "/api/todolist", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getAllTodoItems() {
        logger.info("GET request access '/api/todolist' path.");
        try {
            List<TodoItem> todoItems = new ArrayList<>();
            Iterable<TodoItem> iterable = todoItemRepository.findAll();
            if (iterable != null) {
                iterable.forEach(todoItems::add);
            }
            return new ResponseEntity<>(todoItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Nothing found", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * HTTP POST NEW ONE
     */
    @RequestMapping(value = "/api/todolist", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addNewTodoItem(@RequestBody TodoItem item) {
        logger.info("POST request access '/api/todolist' path with item: {}", item);
        try {
            item.setId(UUID.randomUUID().toString());
            todoItemRepository.save(item);
            return new ResponseEntity<>("Entity created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Entity creation failed", HttpStatus.CONFLICT);
        }
    }

    /**
     * HTTP PUT UPDATE
     */
    @RequestMapping(value = "/api/todolist", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateTodoItem(@RequestBody TodoItem item) {
        logger.info("PUT request access '/api/todolist' path with item {}", item);
        try {
            Optional<TodoItem> todoItem = todoItemRepository.findById(item.getId());
            if (todoItem.isPresent()) {
                todoItemRepository.deleteById(item.getId());
                todoItemRepository.save(item);
                return new ResponseEntity<>("Entity updated", HttpStatus.OK);
            }
            return new ResponseEntity<>("Not found the entity", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Delete and save errors: ", e);
            return new ResponseEntity<>("Entity updating failed", HttpStatus.NOT_FOUND);
        }
    }

    /**
     * HTTP DELETE
     */
    @RequestMapping(value = "/api/todolist/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteTodoItem(@PathVariable("id") String id) {
        logger.info("DELETE request access '/api/todolist/{}' path.", id);
        try {
            Optional<TodoItem> todoItem = todoItemRepository.findById(id);
            if (todoItem.isPresent()) {
                todoItemRepository.deleteById(id);
                return new ResponseEntity<>("Entity deleted", HttpStatus.OK);
            }
            return new ResponseEntity<>("Not found the entity", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Delete errors: ", e);
            return new ResponseEntity<>("Entity deletion failed", HttpStatus.NOT_FOUND);
        }

    }
}
