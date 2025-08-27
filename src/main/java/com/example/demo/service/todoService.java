package com.example.demo.service;

import com.example.demo.model.todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class todoService {
    private final List<todo> todoList = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(); // 用于生成唯一的ID

    public todoService() {
        // 添加一些初始数据
        todoList.add(new todo(counter.incrementAndGet(), "学习Spring Boot", false));
        todoList.add(new todo(counter.incrementAndGet(), "完成课后作业", false));
    }

    public List<todo> findAll() {
        return todoList;
    }

    public todo findById(Long id) {
        return todoList.stream()
                .filter(todo -> todo.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public todo save(todo todo) {
        if (todo.getId() == null) { // 创建新的Todo
            todo.setId(counter.incrementAndGet());
            todoList.add(todo);
        } else { // 更新已有的Todo
            todo existingTodo = findById(todo.getId());
            if (existingTodo != null) {
                existingTodo.setTask(todo.getTask());
                existingTodo.setCompleted(todo.isCompleted());
            }
        }
        return todo;
    }

    public void deleteById(Long id) {
        todoList.removeIf(todo -> todo.getId().equals(id));
    }
}

