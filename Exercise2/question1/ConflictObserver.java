package question1;

public class ConflictObserver {
    public void notifyConflict(Task conflictingTask) {
    	String message = "New task conflicts with existing task '" + conflictingTask + "'.";
    	LoggerUtility.warning(message);
        System.out.println("Error: " + message);
    }
}
