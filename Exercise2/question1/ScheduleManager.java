package question1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ScheduleManager {
    private static ScheduleManager instance = null;
    private ArrayList<Task> tasks;
    private ConflictObserver observer;

    private ScheduleManager() {
        tasks = new ArrayList<>();
        observer = new ConflictObserver();
    }

    public static ScheduleManager getInstance() {
        if (instance == null) {
            instance = new ScheduleManager();
        }
        return instance;
    }

    public String addTask(String description, String startTime, String endTime, String priority) {
        Task newTask = TaskFactory.createTask(description, startTime, endTime, priority);
    
        if (startTime.compareTo(endTime) >= 0) {
            return "Error: End time must be after start time.";
        }
    
        if (startTime.length() != 5 || endTime.length() != 5) {
            return "Error: Invalid time format. Please use HH:MM.";
        }
    
        if (startTime.charAt(2) != ':' || endTime.charAt(2) != ':') {
            return "Error: Invalid time format. Please use HH:MM.";
        }
    
        if (!startTime.substring(0, 2).matches("[0-9]+") || !startTime.substring(3, 5).matches("[0-9]+") ||
            !endTime.substring(0, 2).matches("[0-9]+") || !endTime.substring(3, 5).matches("[0-9]+")) {
            return "Error: Invalid time format. Please use HH:MM.";
        }
    
        int startHour = Integer.parseInt(startTime.substring(0, 2));
        int startMinute = Integer.parseInt(startTime.substring(3, 5));
        int endHour = Integer.parseInt(endTime.substring(0, 2));
        int endMinute = Integer.parseInt(endTime.substring(3, 5));
    
        if (startHour > 23 || endHour > 23 || startMinute > 59 || endMinute > 59) {
            return "Error: Invalid time format. Hours should be between 00 and 23, and minutes between 00 and 59.";
        }
    
        if (!checkConflicts(newTask)) {
            tasks.add(newTask);
            sortTasksByStartTime();
            return "Task added successfully. No conflicts.";
        }
    
        return "Error: Task conflicts with an existing task.";
    }
    

    public String removeTask(String description) {
        for (Task task : tasks) {
            if (task.toString().contains(description)) {
                tasks.remove(task);
                return "Task removed successfully.";
            }
        }
        return "Error: Task not found.";
    }

    public String viewTasks() {
        if (tasks.isEmpty()) {
            return "No tasks scheduled for the day.";
        }
        StringBuilder result = new StringBuilder();
        for (Task task : tasks) {
            result.append(task.toString()).append("\n");
        }
        return result.toString();
    }

    public String markTaskCompleted(String description) {
        for (Task task : tasks) {
            if (task.toString().contains(description)) {
                task.markCompleted();
                return "Task marked as completed.";
            }
        }
        return "Error: Task not found.";
    }

    private boolean checkConflicts(Task newTask) {
        for (Task task : tasks) {
            if (isTimeConflict(task.getStartTime(), task.getEndTime(), newTask.getStartTime(), newTask.getEndTime())) {
                observer.notifyConflict(task);
                return true;
            }
        }
        return false;
    }

    private boolean isTimeConflict(String start1, String end1, String start2, String end2) {
        return !(end1.compareTo(start2) <= 0 || start1.compareTo(end2) >= 0);
    }

    private void sortTasksByStartTime() {
        Collections.sort(tasks, Comparator.comparing(Task::getStartTime));
    }
}
