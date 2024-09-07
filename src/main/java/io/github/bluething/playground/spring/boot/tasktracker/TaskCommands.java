package io.github.bluething.playground.spring.boot.tasktracker;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import java.util.Set;

@Command(group = "Task Commands")
@RequiredArgsConstructor
public class TaskCommands {
    private final TaskService service;
    private final OutputFormatter outputFormatter;

    @Command(command = "add",
    description = "Adding a new task")
    public String addTask(@Option(required = true, description = "Task name") String name) {
        long id;
        try {
            id = service.addTask(name);
        } catch (Exception e) {
            return e.getMessage();
        }

        return String.format("Task added successfully (ID: %d)", id);
    }

    @Command(command = "update",
            description = "Update existing task")
    public String updateTask(@Option(required = true, description = "Task id") long id,
                             @Option(required = true, description = "Task name") String name) {
        try {
            service.updateTask(id, name);
        } catch (Exception e) {
            return e.getMessage();
        }

        return String.format("Task updated successfully (ID: %d)", id);
    }

    @Command(command = "delete",
            description = "Delete existing task")
    public String deleteTask(@Option(required = true, description = "Task id") long id) {
        try {
            service.deleteTask(id);
        } catch (Exception e) {
            return e.getMessage();
        }

        return String.format("Task deleted successfully (ID: %d)", id);
    }

    @Command(command = "mark-in-progress",
            description = "Mark in progress existing task")
    public String markInProgressTask(@Option(required = true, description = "Task id") long id) {
        try {
            service.markInProgress(id);
        } catch (Exception e) {
            return e.getMessage();
        }

        return String.format("Task (ID: %d) successfully mark in progress", id);
    }

    @Command(command = "mark-done",
            description = "Mark done existing task")
    public String markDoneTask(@Option(required = true, description = "Task id") long id) {
        try {
            service.markDone(id);
        } catch (Exception e) {
            return e.getMessage();
        }

        return String.format("Task (ID: %d) successfully mark done", id);
    }

    @Command(command = "list",
            description = "List all tasks")
    public String listTask() {
        Set<Task> tasks = service.getTasks();
        return outputFormatter.format(tasks);
    }

    @Command(command = "list done",
            description = "List all done tasks")
    public String listDoneTask() {
        Set<Task> tasks = service.getTasks(TaskStatus.DONE);
        return outputFormatter.format(tasks);
    }

    @Command(command = "list todo",
            description = "List all todo tasks")
    public String listToDoTask() {
        Set<Task> tasks = service.getTasks(TaskStatus.NOT_DONE);
        return outputFormatter.format(tasks);
    }

    @Command(command = "list in-progress",
            description = "List all in progress tasks")
    public String listInProgressTask() {
        Set<Task> tasks = service.getTasks(TaskStatus.PROGRESS);
        return outputFormatter.format(tasks);
    }
}
