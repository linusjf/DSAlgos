package ds.tests;

import static ds.ExecutorUtils.terminateExecutor;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@DisplayName("ExecutorUtilsTest")
@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
class ExecutorUtilsTest {

  @Test
  @DisplayName("ExecutorUtilsTest.testNormalTerminate")
  void testNormalTerminate() {
    ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    terminateExecutor(es, 1000, TimeUnit.MILLISECONDS);
    assertTrue(es.isTerminated(), "All tasks completed!");
  }

  @Test
  @DisplayName("ExecutorUtilsTest.testForceShutdown")
  void testForceShutdown() {
    ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    es.submit(
        () -> {
          try {
            Thread.currentThread().sleep(1000);
          } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
          }
        });
    terminateExecutor(es, 10, TimeUnit.MILLISECONDS);
    assertTrue(es.isShutdown(), "Executor is shutdown!");
    assertFalse(es.isTerminated(), "Not all tasks complete expected!");
  }

  @Test
  @DisplayName("ExecutorUtilsTest.testInterruptedAwait")
  void testInterruptedAwait() {
    ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    es.submit(new InterruptThread(Thread.currentThread()));
    es.submit(new InterruptThread(Thread.currentThread()));
    es.submit(new InterruptThread(Thread.currentThread()));
    es.submit(new InterruptThread(Thread.currentThread()));
    es.submit(new InterruptThread(Thread.currentThread()));
    es.submit(new InterruptThread(Thread.currentThread()));
    es.submit(new InterruptThread(Thread.currentThread()));
    terminateExecutor(es, 500, TimeUnit.MILLISECONDS);
    assertTrue(es.isShutdown(), "Executor is shutdown!");
    assertFalse(es.isTerminated(), "All tasks not complete!");
  }

  static class InterruptThread extends Thread {
    Thread parentThread;

    InterruptThread(Thread parent) {
      parentThread = parent;
    }

    public void run() {
      try {
        Thread.currentThread().sleep(100);
        parentThread.interrupt();
      } catch (InterruptedException ie) {
        Thread.currentThread().interrupt();
      }
    }
  }
}
