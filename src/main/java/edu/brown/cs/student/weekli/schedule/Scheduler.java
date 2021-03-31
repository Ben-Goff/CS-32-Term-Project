package edu.brown.cs.student.weekli.schedule;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

public class Scheduler {

  /*
  STEP 1: PLACE COMMITMENTS
  STEP 3: CREATE LAMBDAS
      project constraint
      overlap constraint
      schedule blocks withing start/end time constrainst
  STEP 4: BUILD SCHEDULE BLOCK BY BLOCK, CHECKING LAMBDAS
   */

  private HashSet<Block> blocks;

  public Scheduler(HashSet<Block> blocks) {
    this.blocks = blocks;
    List<Block> commitments = blocks.stream().filter(Block::isCommitment).sorted(Comparator.comparingDouble(Block::getStartTime)).collect(Collectors.toList());
    boolean validCommitments;
    for (int i = 0; i < commitments.size() - 1; i++) {
      validCommitments = commitments.get(i).getEndTime() < commitments.get(i + 1).getStartTime();
      if (!validCommitments) {
        //commitments are overlapping, schedule can't be made
        throw new RuntimeException("Commitments are overlapping");
      }
    }
    //((blocks) -> blocks.getblockwithid(1).endtime < blocks.getblockwithid(2).starttime);
    //commitments are good and placed, go on to making lambdas
  }




}
