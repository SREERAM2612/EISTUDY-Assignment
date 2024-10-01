package question1;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        ScheduleManager scheduleManager = ScheduleManager.getInstance();
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.println("\nChoose an option: \n1. Add Task \n2. Remove Task \n3. View Tasks \n4. Mark Task Completed \n5. Exit");
            command = scanner.nextLine();

            switch (command) {
                case "1":
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

                case "2":
                    System.out.println("Enter task description to remove:");
                    String taskToRemove = scanner.nextLine();
                    System.out.println(scheduleManager.removeTask(taskToRemove));
                    break;

                case "3":
                    System.out.println("Scheduled Tasks:");
                    System.out.println(scheduleManager.viewTasks());
                    break;

                case "4":
                    System.out.println("Enter task description to mark completed:");
                    String taskToComplete = scanner.nextLine();
                    System.out.println(scheduleManager.markTaskCompleted(taskToComplete));
                    break;

                case "5":
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
