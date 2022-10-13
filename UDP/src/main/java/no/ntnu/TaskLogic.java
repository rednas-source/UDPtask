package no.ntnu;
/**
 * Contains the logic of the assignment, without any knowledge about sockets
 */
public class TaskLogic {
  public static final String TASK_REQUEST = "task";
  private static final String SENTENCE_TYPE_QUESTION = "question";
  private static final String SENTENCE_TYPE_STATEMENT = "statement";
  private static final String QUESTION_SENTENCE_ENDING = "?";
  private static final String STATEMENT_SENTENCE_ENDING = ".";
  private static final String OK_RESPONSE = "ok";

  /**
   * Solve a task according to the assignment protocol
   *
   * @param task A task (sentence)
   * @return The answer according to the protocol: sentenceType wordCount
   */
  public static String solveTask(String task) {
    String type = sentenceType(task);
    if (type == null) {
      return null;
    }
    return type + " " + wordCount(task);
  }

  /**
   * Count the number of words in the sentence
   * @param sentence The sentence to check
   * @return Number of words; 0 if empty string or null given.
   */
  public static int wordCount(String sentence) {
    if (sentence == null || sentence.isEmpty() || sentence.length() == 1) {
      return 0;
    }

    return sentence.split(" ").length;
  }


  /**
   * Detect the type of the sentence
   *
   * @param sentence A sentence, ending with . or ?
   * @return "statement" or "question"; null on error
   */
  public static String sentenceType(String sentence) {
    if (sentence == null) {
      return null;
    }

    String type = null;
    if (sentence.endsWith(QUESTION_SENTENCE_ENDING)) {
      type = SENTENCE_TYPE_QUESTION;
    } else if (sentence.endsWith(STATEMENT_SENTENCE_ENDING)) {
      type = SENTENCE_TYPE_STATEMENT;
    }
    return type;
  }

  /**
   * Check if the given servers response means that the server approves the answer, or not
   * @param response The response from the server
   * @return True if this means server's approval, false otherwise
   */
  public static boolean hasServerApproved(String response) {
    return OK_RESPONSE.equals(response);
  }
}