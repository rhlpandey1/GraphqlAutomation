package org.graphql.automation.listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println(
                "\nSTARTED: " + getTestName(result)
        );
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println(
                "PASSED:  " + getTestName(result)
        );
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println(
                "FAILED:  " + getTestName(result)
        );

        if (result.getThrowable() != null) {
            System.out.println(
                    "REASON:  " + result.getThrowable().getMessage()
            );
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println(
                "SKIPPED: " + getTestName(result)
        );
    }

    private String getTestName(ITestResult result) {
        return result.getTestClass().getRealClass().getSimpleName()
                + "."
                + result.getMethod().getMethodName();
    }
}