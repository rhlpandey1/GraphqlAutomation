package org.graphql.automation.base;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.filter.log.LogDetail;
import org.graphql.automation.config.ConfigManager;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void setUpFramework() {

        RestAssured.baseURI = ConfigManager.get("base.url");
        RestAssured.basePath = ConfigManager.get("graphql.endpoint");

        RestAssured.filters(
                new RequestLoggingFilter(LogDetail.ALL),
                new ResponseLoggingFilter(LogDetail.ALL)
        );
    }

    @AfterSuite(alwaysRun = true)
    public void cleanUpFramework() {
        RestAssured.reset();
    }
}