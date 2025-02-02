package com.catchylabs.ExtendReportListener;
import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.thoughtworks.gauge.*;

public class ExtendReportListener {

    private static ExtentReports extent;
    private static ExtentTest test;

    @BeforeSuite
    public void setupReport() {
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("reports/extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
    }

    @BeforeStep
    public void beforeStep(ExecutionContext context) {
        test = extent.createTest(context.getCurrentScenario().getName());
    }

    @AfterStep
    public void afterStep(ExecutionContext context) {
        test.pass("Adım geçti: " + context.getCurrentStep().getText());
    }

    @AfterSuite
    public void tearDown() {
        extent.flush();
    }
}

