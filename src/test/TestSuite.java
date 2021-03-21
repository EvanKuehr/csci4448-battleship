package test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ShipTest.class, CellTest.class, BoardTest.class, PlayerTest.class, CaptainsQuartersTest.class, AbilityTest.class})
public class TestSuite {
    //Tutorial followed to make a JUnit test suite: https://bit.ly/3pJ8vzb
}
