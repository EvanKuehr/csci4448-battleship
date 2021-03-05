package edu.colorado.group18;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import test.TestSuite;

public class Main {
    public static void main(String[] args) {
        //Tutorial followed to run a JUnit test suite: https://bit.ly/3pJ8vzb
        Result result = JUnitCore.runClasses(TestSuite.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
            failure.getException().printStackTrace();
        }

        if (result.wasSuccessful()) {
            System.out.println("All tests were successful!");
        }
        else {
            System.out.println("One or more tests have failed");
        }
    }
}
