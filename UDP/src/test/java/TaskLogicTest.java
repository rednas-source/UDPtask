import no.ntnu.TaskLogic;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class TaskLogicTest {
  @Test
  public void testSentenceType() {
    assertEquals("statement", TaskLogic.sentenceType("I will be back."));
    assertEquals("question", TaskLogic.sentenceType("Are you there?"));
    assertNull(TaskLogic.sentenceType("Failure"));
    assertNull(TaskLogic.sentenceType(null));
  }

  @Test
  public void testWordCount() {
    assertEquals(4, TaskLogic.wordCount("I will be back."));
    assertEquals(1, TaskLogic.wordCount("Hey?"));
    assertEquals(0, TaskLogic.wordCount("."));
    assertEquals(0, TaskLogic.wordCount("?"));
    assertEquals(0, TaskLogic.wordCount(""));
    assertEquals(0, TaskLogic.wordCount(null));
  }

  @Test
  public void testTaskSolving() {
    assertEquals("statement 4", TaskLogic.solveTask("I will be back."));
    assertEquals("question 3", TaskLogic.solveTask("Are you there?"));
    assertEquals("statement 0", TaskLogic.solveTask("."));
    assertEquals("question 0", TaskLogic.solveTask("?"));
    assertEquals(null, TaskLogic.solveTask("Failure"));
    assertNull(TaskLogic.solveTask(null));
  }

}