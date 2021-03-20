package edu.brown.cs.student.weekli;

/** Interface for all classes that execute a particular action given
 * the correct command and the correct number of arguments. */
public interface TriggerAction {
  String command();
  String execute(String[] args, boolean isREPL);
  int[] getNumParameters();
}
