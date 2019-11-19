package app.tree;

/**
 * Glitch
 */
public class Glitch {
    private int startTime, endTime;

    public Glitch(int t0, int t1) {
        startTime = t0;
        endTime = t1;
    }
    
    public int getStart() {
        return startTime;
    }

    public int getEnd() {
        return endTime;
    }
}