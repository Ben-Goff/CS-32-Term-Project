package edu.brown.cs.student.weekli.schedule;

import java.util.*;

public class TimeBin {

    private final long startTime;
    private final long endTime;
    private long capacity;
    private List<Task> tasks;
    private NavigableSet<Block> blocks;


    public TimeBin(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.capacity = endTime - startTime;
        this.blocks = new TreeSet<>(Comparator.comparingLong(Block::getStartTime));
        this.tasks = new ArrayList<>();
    }

    public boolean addBlock(Task t) {
      tasks.add(t);
      tasks.sort(new WiggleComparator(startTime, endTime));

      long dur = t.getSessionTime();
      long start = Math.max(startTime, t.getStartDate());
      long end = Math.min(endTime, t.getEndDate());

      Block toAdd = addLeftSide(blocks, t.getID(), dur, start, end);

      if (toAdd != null) {
        blocks.add(toAdd);
      } else {

        NavigableSet<Block> attempt = new TreeSet<>(Comparator.comparingLong(Block::getStartTime));

        for (Task task : tasks) {
          long duration = task.getSessionTime();

          long startSearch = Math.max(startTime, task.getStartDate());
          long endSearch = Math.min(endTime, task.getEndDate());

          long bigMean = (end - start) / 2;
          long searchMean = (endSearch - startSearch) / 2;

          if (searchMean < bigMean) {
            toAdd = addLeftSide(attempt, task.getID(), duration, startSearch, endSearch);
          } else {
            toAdd = addRightSide(attempt.descendingSet(), task.getID(), duration, startSearch, endSearch);
          }

          if(toAdd == null) {
            return false;
          } else {
            attempt.add(toAdd);
          }

        }
        blocks = attempt;
      }
      return true;
    }


    public Block addLeftSide(NavigableSet<Block> blocks, UUID id, long duration, long startSearch, long endSearch) {
      if ((endSearch - startSearch) < duration) {
        return null;
      }
      for (Block b: blocks) {
        if (b.getEndTime() > startSearch) {
          if (b.getStartTime() < startSearch) {
            return addLeftSide(blocks, id, duration, b.getEndTime(), endSearch);
          } else {
            if ((b.getStartTime() - startSearch) >= duration) {
              return new Block(startSearch, startSearch + duration, id);
            } else {
              return addLeftSide(blocks, id, duration, b.getEndTime(), endSearch);
            }
          }
        }
      }
      return new Block(startSearch, startSearch + duration, id);
    }

  public Block addRightSide(NavigableSet<Block> blocks, UUID id, long duration, long startSearch, long endSearch) {
    if ((endSearch - startSearch) < duration) {
      return null;
    }
    for (Block b: blocks) {
      if (b.getStartTime() < endSearch) {
        if (b.getEndTime() > endSearch) {
          return addLeftSide(blocks, id, duration, startSearch, b.getStartTime());
        } else {
          if ((endSearch - b.getEndTime()) >= duration) {
            return new Block(endSearch - duration, endSearch, id);
          } else {
            return addRightSide(blocks, id, duration, startSearch, b.getStartTime());
          }
        }
      }
    }

    return new Block(endSearch - duration, endSearch, id);
  }

    public List<Task> getTasks() {
      return tasks;
    }

    public NavigableSet<Block> getBlocks() {
        return blocks;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }


    public static class WiggleComparator implements Comparator<Task> {

      private long startTime;
      private long endTime;

      public WiggleComparator(long sT, long eT) {
        startTime = sT;
        endTime = eT;
      }

      /**
       * Compares its two arguments for order.  Returns a negative integer,
       * zero, or a positive integer as the first argument is less than, equal
       * to, or greater than the second.<p>
       * <p>
       * The implementor must ensure that {@code sgn(compare(x, y)) ==
       * -sgn(compare(y, x))} for all {@code x} and {@code y}.  (This
       * implies that {@code compare(x, y)} must throw an exception if and only
       * if {@code compare(y, x)} throws an exception.)<p>
       * <p>
       * The implementor must also ensure that the relation is transitive:
       * {@code ((compare(x, y)>0) && (compare(y, z)>0))} implies
       * {@code compare(x, z)>0}.<p>
       * <p>
       * Finally, the implementor must ensure that {@code compare(x, y)==0}
       * implies that {@code sgn(compare(x, z))==sgn(compare(y, z))} for all
       * {@code z}.<p>
       * <p>
       * It is generally the case, but <i>not</i> strictly required that
       * {@code (compare(x, y)==0) == (x.equals(y))}.  Generally speaking,
       * any comparator that violates this condition should clearly indicate
       * this fact.  The recommended language is "Note: this comparator
       * imposes orderings that are inconsistent with equals."<p>
       * <p>
       * In the foregoing description, the notation
       * {@code sgn(}<i>expression</i>{@code )} designates the mathematical
       * <i>signum</i> function, which is defined to return one of {@code -1},
       * {@code 0}, or {@code 1} according to whether the value of
       * <i>expression</i> is negative, zero, or positive, respectively.
       *
       * @param o1 the first object to be compared.
       * @param o2 the second object to be compared.
       * @return a negative integer, zero, or a positive integer as the
       * first argument is less than, equal to, or greater than the
       * second.
       * @throws NullPointerException if an argument is null and this
       *                              comparator does not permit null arguments
       * @throws ClassCastException   if the arguments' types prevent them from
       *                              being compared by this comparator.
       */
      @Override
      public int compare(Task o1, Task o2) {
        long blockLength1 = o1.getSessionTime();
        long startSearch1 = Math.max(startTime, o1.getStartDate());
        long endSearch1 = Math.min(endTime, o1.getEndDate());
        long wiggle1 = endSearch1 - startSearch1 - blockLength1;
        long blockLength2 = o2.getSessionTime();
        long startSearch2 = Math.max(startTime, o2.getStartDate());
        long endSearch2 = Math.min(endTime, o2.getEndDate());
        long wiggle2 = endSearch2 - startSearch2 - blockLength2;
        return Long.compare(wiggle1, wiggle2);
      }
    }

}



