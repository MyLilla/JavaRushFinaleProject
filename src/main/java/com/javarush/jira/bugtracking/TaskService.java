package com.javarush.jira.bugtracking;

import com.javarush.jira.bugtracking.internal.mapper.TaskMapper;
import com.javarush.jira.bugtracking.internal.model.Task;
import com.javarush.jira.bugtracking.internal.repository.ProjectRepository;
import com.javarush.jira.bugtracking.internal.repository.SprintRepository;
import com.javarush.jira.bugtracking.internal.repository.TaskRepository;
import com.javarush.jira.bugtracking.to.TaskTo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class TaskService extends BugtrackingService<Task, TaskTo, TaskRepository> {
    public TaskService(TaskRepository repository, TaskMapper mapper) {
        super(repository, mapper);
    }

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private SprintRepository sprintRepository;

    public List<TaskTo> getAll() {
        return mapper.toToList(repository.getAll());
    }

    public List<Task> getAllForUser(Long userId) {
        List<Task> tasks = repository.getAllByUserId(userId);
        return tasks;
    }

    public Task create(TaskTo taskTo, Long sprint, Long project) {
        Task task = mapper.toEntity(taskTo);
        task.setProject(projectRepository.findById(project).get());
        task.setSprint(sprintRepository.findById(sprint).get());
        log.debug("Create new task: {}", task);

        return repository.saveAndFlush(task);
    }

    public Task change(TaskTo taskTo) {
        Task task = repository.findById(taskTo.getId()).get();
        task.setTags(taskTo.getTags());
        task.setDescription(taskTo.getDescription());
        task.setStatusCode(taskTo.getStatusCode());
        task.setTitle(taskTo.getTitle());
        task.setUpdated(LocalDateTime.now());

        log.debug("Change task: {}", task.getId());

        return repository.saveAndFlush(task);
    }
}
