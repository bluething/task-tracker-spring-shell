package io.github.bluething.playground.spring.boot.tasktracker;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class TaskPromptProvider implements PromptProvider {

    @Override
    public AttributedString getPrompt() {
        return new AttributedString("task-cli:>",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.BLUE));
    }
}
