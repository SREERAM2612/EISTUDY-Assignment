package question1;
import java.time.LocalTime;
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
    private LocalTime parseTime(String time) {
        try {
            return LocalTime.parse(time);
        } catch (Exception e) {
            return null; // Invalid time format
        }
    }

    public String addTask(String description, String startTime, String endTime, String priority) {
    	// Validate time formats and convert to LocalTime
        LocalTime startLocalTime = parseTime(startTime);
        LocalTime endLocalTime = parseTime(endTime);
        
        if (!isValidPriority(priority)) {
        	LoggerUtility.info("Error: Invalid priority level. Please use High, Medium, or Low.");
            return "Error: Invalid priority level. Please use High, Medium, or Low.";
        }
        
        if (startLocalTime == null || endLocalTime == null) {
        	LoggerUtility.info("Error: Invalid time format. Please use HH:MM.");
            return "Error: Invalid time format. Please use HH:MM.";
        }

        if (!isValidTimeRange(startLocalTime, endLocalTime)) {
        	LoggerUtility.info("Error: End time must be after start time.");
            return "Error: End time must be after start time.";
        }
        
        Task newTask = TaskFactory.createTask(description, startLocalTime, endLocalTime, priority);
        
        if (!checkConflicts(newTask)) {
            tasks.add(newTask);
            sortTasksByStartTime();
            LoggerUtility.info("Task added successfully. No conflicts.");
            return "Task added successfully. No conflicts.";
        }
    
        return "Error: Task conflicts with an existing task.";
    }
    
    private boolean isValidTimeRange(LocalTime start, LocalTime end) {
        return start.isBefore(end);
    }
    
    public String editTask(String taskId, String newDescription, String newStartTime, String newEndTime, String newPriority) {
        Task taskToEdit = findTaskById(taskId);

        if (taskToEdit == null) {
        	LoggerUtility.info("Error: Task not found.");
            return "Error: Task not found.";
        }
    
        // Validate new time formats and convert to LocalTime
        LocalTime newStartLocalTime = parseTime(newStartTime);
        LocalTime newEndLocalTime = parseTime(newEndTime);

        if (newStartLocalTime == null || newEndLocalTime == null) {
            return "Error: Invalid time format. Please use HH:MM.";
        }

        if (!isValidTimeRange(newStartLocalTime, newEndLocalTime)) {
            return "Error: End time must be after start time.";
        }
        
        if (!isValidPriority(newPriority)) {
            return "Error: Invalid priority level. Please use High, Medium, or Low.";
        }

        // Create a new task with updated values
        Task updatedTask = TaskFactory.createTask(newDescription, newStartLocalTime, newEndLocalTime, newPriority);
        
        // Remove the old task temporarily
        tasks.remove(taskToEdit);

        if (!checkConflicts(updatedTask)) {
            // No conflict, so we add the updated task
            tasks.add(updatedTask);
            sortTasksByStartTime();
            LoggerUtility.info("Task edited successfully.");
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
            LoggerUtility.info("Task removed successfully.");
            return "Task removed successfully.";
        }
        return "Error: Task not found.";
    }

    public String markTaskCompleted(String taskId) {
        Task taskToComplete = findTaskById(taskId);
        if (taskToComplete != null) {
            taskToComplete.markCompleted();
            LoggerUtility.info("Task marked as completed.");
            return "Task marked as completed.";
        }
        return "Error: Task not found.";
    }


    public String viewTasks() {
        if (tasks.isEmpty()) {
        	LoggerUtility.info("No tasks scheduled for the day.");
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

    private boolean isValidPriority(String priority) {
        return priority.equalsIgnoreCase("High") || 
               priority.equalsIgnoreCase("Medium") || 
               priority.equalsIgnoreCase("Low");
    }

    private boolean isTimeConflict(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return !(end1.compareTo(start2) <= 0 || start1.compareTo(end2) >= 0);
    }

    private void sortTasksByStartTime() {
        Collections.sort(tasks, Comparator.comparing(Task::getStartTime));
    }
}
