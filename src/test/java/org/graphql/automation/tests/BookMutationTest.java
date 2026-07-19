package org.graphql.automation.tests;

import io.restassured.response.Response;
import org.graphql.automation.base.BaseTest;
import org.graphql.automation.client.GraphQLClient;
import org.graphql.automation.utils.JsonDataLoader;
import org.graphql.automation.utils.QueryLoader;
import org.testng.annotations.Test;

import java.util.Map;

import org.graphql.automation.utils.GraphQLAssertions;
public class BookMutationTest extends BaseTest {

    @Test
    public void shouldCreateBook() {

        String mutation = QueryLoader.load("create-book.graphql");

        Map<String, Object> variables =
                JsonDataLoader.loadAsMap("create-book.json");

        Response response = GraphQLClient.execute(
                mutation,
                variables
        );
        GraphQLAssertions.assertBookCreated(
                response,
                "API Automation with GraphQL",
                "Rahul",
                650.0
        );

    }
}