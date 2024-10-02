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
    
    public String editTask(String taskId, String newDescription, String newStartTime, String newEndTime, String newPriority) {
        Task taskToEdit = findTaskById(taskId);

        if (taskToEdit == null) {
            return "Error: Task not found.";
        }
    
        // Validate the new start and end time formats
        if(newStartTime.compareTo(newEndTime) >= 0) {
            return "Error: End time must be after start time.";
        }
        if(newStartTime.length() != 5 || newEndTime.length() != 5) {
            return "Error: Invalid time format. Please use HH:MM.";
        }
        if(newStartTime.charAt(2) != ':' || newEndTime.charAt(2) != ':') {
            return "Error: Invalid time format. Please use HH:MM.";
        }
        if(!newStartTime.substring(0, 2).matches("[0-9]+") || !newStartTime.substring(3, 5).matches("[0-9]+") ||
           !newEndTime.substring(0, 2).matches("[0-9]+") || !newEndTime.substring(3, 5).matches("[0-9]+")) {
            return "Error: Invalid time format. Please use HH:MM.";
        }
        if(Integer.parseInt(newStartTime.substring(0, 2)) > 24 || Integer.parseInt(newEndTime.substring(0, 2)) > 24 || 
           Integer.parseInt(newStartTime.substring(3)) > 60 || Integer.parseInt(newEndTime.substring(3)) > 60) {
            return "Error: Invalid time format. Please use HH:MM.";
        }
    
        // Create a new task with updated values
        Task updatedTask = TaskFactory.createTask(newDescription, newStartTime, newEndTime, newPriority);
    
        // Remove the old task temporarily
        tasks.remove(taskToEdit);

        if (!checkConflicts(updatedTask)) {
            // No conflict, so we add the updated task
            tasks.add(updatedTask);
            sortTasksByStartTime();
            return "Task edited successfully.";
        }
        
        
        // If there's a conflict, re-add the old task and return an error
        tasks.add(taskToEdit);
        sortTasksByStartTime();
        return "Error: Task conflicts with an existing task.";
    }

    public String removeTask(String taskId) {
        Task taskToRemove = findTaskById(taskId);
        if (taskToRemove != null) {
            tasks.remove(taskToRemove);
            return "Task removed successfully.";
        }
        return "Error: Task not found.";
    }

    public String markTaskCompleted(String taskId) {
        Task taskToComplete = findTaskById(taskId);
        if (taskToComplete != null) {
            taskToComplete.markCompleted();
            return "Task marked as completed.";
        }
        return "Error: Task not found.";
    }


    public String viewTasks() {
        if (tasks.isEmpty()) {
            return "No tasks scheduled for the day.";
        }
        StringBuilder result = new StringBuilder();
        for (Task task : tasks) {
            result.append(task.toString()).append("\n");  // Task.toString() now includes the taskId
        }
        return result.toString();
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
    
    private Task findTaskById(String taskId) {
        for (Task task : tasks) {
            if (task.getTaskId().equals(taskId)) {
                return task;
            }
        }
        return null; // Return null if no task with the ID is found
    }


    private boolean isTimeConflict(String start1, String end1, String start2, String end2) {
        return !(end1.compareTo(start2) <= 0 || start1.compareTo(end2) >= 0);
    }

    private void sortTasksByStartTime() {
        Collections.sort(tasks, Comparator.comparing(Task::getStartTime));
    }
}
