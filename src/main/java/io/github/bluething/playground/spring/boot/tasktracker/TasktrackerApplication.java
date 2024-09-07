package io.github.bluething.playground.spring.boot.tasktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.shell.command.annotation.EnableCommand;

@SpringBootApplication
@EnableCommand(TaskCommands.class)
public class TaskTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskTrackerApplication.class, args);
	}

}
