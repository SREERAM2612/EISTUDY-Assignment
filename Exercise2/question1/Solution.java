package question1;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        ScheduleManager scheduleManager = ScheduleManager.getInstance();
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.println("\nChoose an option: \n1. Add Task \n2. Remove Task \n3. View Tasks \n4. Mark Task Completed \n5. Edit Task \n6. Exit");
            command = scanner.nextLine();

            switch (command) {
                case "1": // Add Task
                    System.out.println("Enter task description:");
                    String description = scanner.nextLine();
                    System.out.println("Enter start time (HH:MM):");
                    String startTime = scanner.nextLine();
                    System.out.println("Enter end time (HH:MM):");
                    String endTime = scanner.nextLine();
                    System.out.println("Enter priority (High/Medium/Low):");
                    String priority = scanner.nextLine();
                    System.out.println(scheduleManager.addTask(description, startTime, endTime, priority));
                    break;

                case "2": // Remove Task
                    System.out.println("Enter the task ID to remove:");
                    String taskIdToRemove = scanner.nextLine();
                    System.out.println(scheduleManager.removeTask(taskIdToRemove));
                    break;

                case "3": // View Tasks
                    System.out.println("Scheduled Tasks:");
                    System.out.println(scheduleManager.viewTasks());
                    break;

                case "4": // Mark Task Completed
                    System.out.println("Enter the task ID to mark completed:");
                    String taskIdToComplete = scanner.nextLine();
                    System.out.println(scheduleManager.markTaskCompleted(taskIdToComplete));
                    break;

                case "5": // Edit Task
                    System.out.println("Enter the task ID of the task you want to edit:");
                    String taskIdToEdit = scanner.nextLine();
                    System.out.println("Enter new description:");
                    String newDescription = scanner.nextLine();
                    System.out.println("Enter new start time (HH:MM):");
                    String newStartTime = scanner.nextLine();
                    System.out.println("Enter new end time (HH:MM):");
                    String newEndTime = scanner.nextLine();
                    System.out.println("Enter new priority (High/Medium/Low):");
                    String newPriority = scanner.nextLine();
                    System.out.println(scheduleManager.editTask(taskIdToEdit, newDescription, newStartTime, newEndTime, newPriority));
                    break;

                case "6": // Exit
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
