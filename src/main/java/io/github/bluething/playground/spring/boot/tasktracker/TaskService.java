package io.github.bluething.playground.spring.boot.tasktracker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class TaskService {
    private static final String DB_PATH = "db.json";
    private final ObjectMapper objectMapper;
    @Getter
    private Set<Task> tasks;

    @PostConstruct
    private void loadTask() {
        File file = new File(DB_PATH);
        try {
            if (file.exists()) {
                tasks = objectMapper.readValue(file, new TypeReference<>() {});
                log.info("Loaded {} tasks", tasks.size());
            } else {
                tasks = new HashSet<>();
                log.info("No db found, create a new one with empty list");
            }
        } catch (IOException e) {
            log.error("Error when loadTask()", e);
        }
    }
    @PreDestroy
    private void saveTasks() {
        File file = new File(DB_PATH);
        try {
            objectMapper.writeValue(file, tasks);
            log.info("Saved tasks");
        } catch (IOException e) {
            log.error("Error when saveTasks()", e);
        }
    }

    private Optional<Task> getTask(Long id) {
        return tasks.stream().filter(task -> task.getId().equals(id))
                .findFirst();
    }
    private void markTask(Long id, TaskStatus status) {
        getTask(id).ifPresentOrElse(task -> {
                    task.setStatus(status);
                    saveTasks();
                    log.info("Task marked as DONE.");
                },
                () -> {
                    log.info("Task not found.");
                    throw new IllegalArgumentException("Task not found.");
                });
    }
    public void markDone(Long id) {
        markTask(id, TaskStatus.DONE);
    }
    public void markInProgress(Long id) {
        markTask(id, TaskStatus.PROGRESS);
    }
    public void deleteTask(Long id) {
        getTask(id).ifPresentOrElse(task -> {
                    tasks.remove(task);
                    saveTasks();
                    log.info("Task deleted");
                },
                () -> {
                    log.info("Task not found.");
                    throw new IllegalArgumentException("Task not found.");
                });
    }
    public void updateTask(Long id, String name) {
        getTask(id).ifPresentOrElse(task -> {
                    tasks.remove(task);
                    tasks.add(new Task(task.getId(), name, task.getStatus()));
                    saveTasks();
                    log.info("Task updated");
                },
                () -> {
                    log.info("Task not found.");
                    throw new IllegalArgumentException("Task not found.");
                });
    }
    public long addTask(String name) {
        long id = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        Task task = new Task(id, name, TaskStatus.NOT_DONE);
        if (tasks.add(task)) {
            saveTasks();
            log.info("Task added");
        } else {
            log.info("Task already exist.");
            throw new IllegalArgumentException("Task already exist.");
        }

        return id;
    }
    public Set<Task> getTasks(TaskStatus status) {
        return tasks.stream().filter(task -> task.getStatus().equals(status))
                .collect(Collectors.toSet());
    }
}
