open module ds.tests {
  requires java.logging;
  requires ds;
  requires org.jooq.joor;
  requires nl.jqno.equalsverifier;
  requires transitive org.junit.jupiter.api;
  requires transitive org.junit.jupiter.params;
  requires transitive org.junit.platform.launcher;
  requires transitive org.junit.platform.commons;
  requires transitive org.junit.platform.engine;

  exports ds.tests;
}
