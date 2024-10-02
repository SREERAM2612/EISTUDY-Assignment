package question1;

import java.util.UUID;

public class Task {
    private String description;
    private String startTime;
    private String endTime;
    private String priority;
    private boolean isCompleted;
    private String taskId;

    public Task(String description, String startTime, String endTime, String priority) {
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

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void markCompleted() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return "Task ID: " + taskId + ", " + description + " [" + priority + "] from " + startTime + " to " + endTime + (isCompleted ? " (Completed)" : "");
    }
}
