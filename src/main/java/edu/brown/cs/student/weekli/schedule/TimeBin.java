package edu.brown.cs.student.weekli.schedule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class TimeBin {

    private final long startTime;
    private final long endTime;
    private long capacity;
    PriorityQueue<Task> blocks;

    public TimeBin(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = endTime - startTime;
        this.blocks = new PriorityQueue<>((t1, t2) -> {
            long blockLength1 = t1.getSessionTime();
            long startSearch1 = Math.max(startTime, t1.getStartDate());
            long endSearch1 = Math.min(endTime, t1.getEndDate());
            long wiggle1 = endSearch1 - startSearch1 - blockLength1;
            long blockLength2 = t2.getSessionTime();
            long startSearch2 = Math.max(startTime, t2.getStartDate());
            long endSearch2 = Math.min(endTime, t2.getEndDate());
            long wiggle2 = endSearch2 - startSearch2 - blockLength2;
            return Long.compare(wiggle1, wiggle2);
        });
    }

    public boolean addBlock(Task t) {
        this.blocks.add(t);
        long startSearch = Math.max(this.startTime, t.getStartDate());
        long endSearch = Math.min(this.endTime, t.getEndDate());

        if (b.getEndTime() - b.getStartTime() >= this.capacity) {
            this.blocks.add(b);
        } else {
            throw new ArithmeticException("ERROR: block can't fit within bin");
        }
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }
}
