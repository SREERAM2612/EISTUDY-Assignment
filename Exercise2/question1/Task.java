package question1;

import java.util.UUID;
import java.time.LocalTime;

public class Task {
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private String priority;
    private boolean isCompleted;
    private String taskId;

    public Task(String description, LocalTime startTime, LocalTime endTime, String priority) {
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
        this.isCompleted = false;
        this.taskId = UUID.randomUUID().toString();  // Generate unique ID

    }
    // Getter for taskId
    public String getTaskId() {
        return taskId;
    }
    
    public String getDescription() {
    	return this.description;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void markCompleted() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return taskId + ". " + startTime + " - " + endTime + ": " + description + " [" + priority + "]"+ (isCompleted ? " (Completed)" : "");
    }

}
