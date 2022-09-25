// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package org.acme.example.dao;

import org.acme.example.model.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, String> {

}
