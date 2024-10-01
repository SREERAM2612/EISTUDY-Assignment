package question1;

public class ConflictObserver {
    public void notifyConflict(Task conflictingTask) {
        System.out.println("Warning: New task conflicts with existing task '" + conflictingTask + "'.");
    }
}
