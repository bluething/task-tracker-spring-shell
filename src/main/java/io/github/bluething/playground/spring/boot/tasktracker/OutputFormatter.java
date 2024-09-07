package io.github.bluething.playground.spring.boot.tasktracker;

import org.springframework.shell.table.ArrayTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;

import java.util.Set;
import java.util.stream.Collectors;

final class OutputFormatter {
    public String format(Set<Task> tasks) {
        var data = tasks
                .stream()
                .map(OutputFormatter::toRow)
                .collect(Collectors.toList());
        data.add(0, addRow("id", "name", "status"));

        ArrayTableModel model = new ArrayTableModel(data.toArray(Object[][]::new));
        TableBuilder table = new TableBuilder(model);
        table.addHeaderAndVerticalsBorders(BorderStyle.fancy_light);
        return table.build().render(100);
    }
    private static String[] toRow(Task task) {
        return addRow(Long.toString(task.getId()), task.getName(), task.getStatus().name());
    }
    private static String[] addRow(String id, String name, String status) {
        return new String[] { id, name, status };
    }
}
