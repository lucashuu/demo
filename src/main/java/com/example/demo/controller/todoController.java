package com.example.demo.controller;

import com.example.demo.model.todo;
import com.example.demo.service.todoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos") // 为这个控制器下的所有API统一添加/api/todos前缀
public class todoController {

    @Autowired
    private todoService todoService;

    // 1. 读取 (Read) - 获取所有待办事项
    @GetMapping
    public List<todo> getAllTodos() {
        return todoService.findAll();
    }

    // 2. 读取 (Read) - 根据ID获取单个待办事项
    // @PathVariable会把URL路径中的{id}部分的值，赋给方法的id参数
    @GetMapping("/{id}")
    public todo getTodoById(@PathVariable Long id) {
        return todoService.findById(id);
    }

    // 3. 创建 (Create) - 添加一个新的待办事项
    // @RequestBody会把请求体中的JSON数据，自动转换成一个Todo对象
    @PostMapping
    public todo createTodo(@RequestBody todo todo) {
        return todoService.save(todo);
    }

    // 4. 更新 (Update) - 替换一个已有的待办事项
    @PutMapping("/{id}")
    public todo updateTodo(@PathVariable Long id, @RequestBody todo todo) {
        todo.setId(id); // 确保我们更新的是正确的ID
        return todoService.save(todo);
    }

    // 5. 删除 (Delete) - 删除一个待办事项
    @DeleteMapping("/{id}")
    public String deleteTodo(@PathVariable Long id) {
        todoService.deleteById(id);
        return "ID为 " + id + " 的待办事项已成功删除。";
    }
}

