public interface AocDay {
    // TODO implement a general aocday
    public String run();
    public void debug();
    public default void print() {
        run();
    }
}
