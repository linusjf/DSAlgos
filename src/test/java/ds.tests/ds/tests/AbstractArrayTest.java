package ds.tests;

import static ds.tests.TestConstants.*;

import ds.AbstractArray;
import ds.AbstractOrdArray;
import ds.OrdArray;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@TestInstance(Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@DisplayName("AbstractArrayTest")
@SuppressWarnings("PMD.LawOfDemeter")
class AbstractArrayTest {

  @Test
  @DisplayName("AbstractArrayTest.leafAbstractArrayNodeEquals")
  @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
  void leafAbstractArrayNodeEquals() {
    EqualsVerifier.forClass(AbstractArray.class)
        .withIgnoredFields(MOD_COUNT, LOCK, STRICT)
        .withRedefinedSuperclass()
        .withRedefinedSubclass(OrdArray.class)
        .withIgnoredAnnotations(NonNull.class)
        .verify();
  }

  @Test
  @DisplayName("AbstractArrayTest.leafAbstractOrdArrayNodeEquals")
  @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
  void leafAbstractOrdArrayNodeEquals() {
    EqualsVerifier.forClass(AbstractOrdArray.class)
        .withIgnoredFields(MOD_COUNT, LOCK, STRICT)
        .withRedefinedSuperclass()
        .withRedefinedSubclass(OrdArray.class)
        .withIgnoredAnnotations(NonNull.class)
        .verify();
  }
}
