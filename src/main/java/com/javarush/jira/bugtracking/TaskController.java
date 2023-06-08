package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.to.TaskTo;
import com.javarush.jira.login.AuthUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(TaskController.REST_URL)
public class TaskController {

    @Autowired
    private TaskService taskService;
    public static final String REST_URL = "/api/tasks";

    @GetMapping
    public List<Task> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all for user: {}", authUser.id());
        return taskService.getAllForUser(authUser.id());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Task createTask(@RequestBody TaskTo taskTo, Long sprintId, Long projectId) {
        return taskService.create(taskTo, sprintId, projectId);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Task cangeTask(@RequestBody TaskTo taskTo) {
        return taskService.change(taskTo);
    }
}
