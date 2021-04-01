package edu.brown.cs.student.weekli.schedule;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class Scheduler {

  /*
  STEP 1: PLACE COMMITMENTS
  STEP 3: CREATE LAMBDAS
      overflow constraint
      schedule blocks withing start/end time constrainst
  STEP 4: BUILD SCHEDULE BLOCK BY BLOCK, CHECKING LAMBDAS
   */
  private final long globalStartTime = (new Date()).getTime();
  private HashMap<AbstractMap.SimpleEntry<Long, Long>, Commitment> schedule = new HashMap<>();
  private final List<Block> commitmentBlocks;
  private final List<Task> tasks;
  private final List<TimeBin> bins = new ArrayList<>();

  public Scheduler(List<Commitment> commitments, List<Task> tasks) {
    this.tasks = tasks;
    this.commitmentBlocks = commitments.stream().map(Commitment::getBlocks).flatMap(Collection::stream).filter(b -> b.getStartTime() > globalStartTime).collect(Collectors.toList());
    boolean validCommitments;
    Block currentCommitment;
    Block nextCommitment;
    this.bins.add(new TimeBin(globalStartTime, commitmentBlocks.get(0).getStartTime()));
    for (int i = 0; i < commitmentBlocks.size() - 1; i++) {
      currentCommitment = commitmentBlocks.get(i);
      nextCommitment = commitmentBlocks.get(i + 1);
      validCommitments = currentCommitment.getEndTime() < nextCommitment.getStartTime();
      if (!validCommitments) {
        //commitments are overlapping, schedule can't be made
        throw new RuntimeException("Commitments are overlapping");
      }
        this.bins.add(new TimeBin(currentCommitment.getEndTime(), nextCommitment.getStartTime()));
      }
    this.bins.add(c)
    }
    //((blocks) -> blocks.getblockwithid(1).endtime < blocks.getblockwithid(2).starttime);
    //commitments are good and placed, go on to making lambdas
  }




}
