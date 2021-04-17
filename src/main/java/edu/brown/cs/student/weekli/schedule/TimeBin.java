package edu.brown.cs.student.weekli.schedule;

import com.sun.source.tree.Tree;

import java.util.*;
import java.util.stream.Collectors;

public class TimeBin {

    private final long startTime;
    private final long endTime;
    private NavigableSet<Task> tasks;
    private NavigableSet<Block> blocks;
    private long breakTime;


    public TimeBin(long startTime, long endTime, long breakTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.blocks = new TreeSet<>(Comparator.comparingLong(Block::getStartTime));
        this.tasks = new TreeSet<>(new WiggleComparator(this.startTime, this.endTime));
        this.breakTime = breakTime;
    }

    public boolean addBlock(Task t) {
      long tDur = t.getSessionTime();
      long tStart = Math.max(startTime, t.getStartDate());
      long tEnd = Math.min(endTime, t.getEndDate());
      String tName = t.getName();
      String tDesc = t.getDescription();
      String tColor = t.getColor();
      long tMean = (tEnd - tStart) / 2;
      Block toAdd = addLeftSide(blocks, t.getID(), tDur, tStart + breakTime, tEnd - breakTime,
          tName, tDesc, tColor);
      if (toAdd != null) {
        blocks.add(toAdd);
        tasks.add(t);
      } else {
        NavigableSet<Block> attempt = new TreeSet<>(Comparator.comparingLong(Block::getStartTime));
        for (Task task : tasks) {
          long curDur = task.getSessionTime();
          long curStart = Math.max(startTime, task.getStartDate());
          long curEnd = Math.min(endTime, task.getEndDate());
          long curMean = (curEnd - curStart) / 2;
          if (curMean < tMean) {
            toAdd = addLeftSide(attempt, task.getID(), curDur, curStart + breakTime,
                curEnd - breakTime, tName, tDesc, tColor);
          } else {
            toAdd = addRightSide(attempt.descendingSet(), task.getID(), curDur,
                curStart + breakTime, curEnd - breakTime, tName, tDesc, tColor);
          }
          if(toAdd == null) {
            throw new RuntimeException("blocks did not refit");
          } else {
            attempt.add(toAdd);
          }
        }
        toAdd = addLeftSide(attempt, t.getID(), tDur, tStart + breakTime, tEnd - breakTime, tName,
            tDesc, tColor);
        if (toAdd != null) {
          blocks = attempt;
          blocks.add(toAdd);
          tasks.add(t);
        } else {
          return false;
        }
      }
      return true;
    }


    public Block addLeftSide(NavigableSet<Block> blocks, UUID id, long duration, long startSearch, long endSearch, String name, String desc, String color) {
      if ((endSearch - startSearch) < duration) {
        return null;
      }
      for (Block b: blocks) {
        if (b.getEndTime() > startSearch) {
          if (b.getStartTime() < startSearch) {
            return addLeftSide(blocks, id, duration, b.getEndTime() + breakTime, endSearch, name, desc, color);
          } else {
            if ((b.getStartTime() - startSearch) >= duration) {
              return new Block(startSearch, startSearch + duration, id, name, desc, color);
            } else {
              return addLeftSide(blocks, id, duration, b.getEndTime() + breakTime, endSearch, name, desc, color);
            }
          }
        }
      }
      return new Block(startSearch, startSearch + duration, id, name, desc, color);
    }


  public Block addRightSide(NavigableSet<Block> blocks, UUID id, long duration, long startSearch, long endSearch, String name, String desc, String color) {
    if ((endSearch - startSearch) < duration) {
      return null;
    }
    for (Block b: blocks) {
      if (b.getStartTime() < endSearch) {
        if (b.getEndTime() > endSearch) {
          return addRightSide(blocks, id, duration, startSearch, b.getStartTime() - breakTime, name, desc, color);
        } else {
          if ((endSearch - b.getEndTime()) >= duration) {
            return new Block(endSearch - duration, endSearch, id, name, desc, color);
          } else {
            return addRightSide(blocks, id, duration, startSearch, b.getStartTime() - breakTime, name, desc, color);
          }
        }
      }
    }
    return new Block(endSearch - duration, endSearch, id, name, desc, color);
  }

    public List<Task> getTasks() {
      return new ArrayList<>(tasks);
    }

    public List<Block> getBlocks() {
      return new ArrayList<>(blocks);
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



