// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package org.acme.example.dao;

import org.acme.example.model.TodoItem;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, String> {

}
