package org.graphql.automation.tests;

import io.restassured.response.Response;
import org.graphql.automation.base.BaseTest;
import org.graphql.automation.client.GraphQLClient;
import org.graphql.automation.utils.QueryLoader;
import org.testng.annotations.Test;

import org.graphql.automation.utils.GraphQLAssertions;
public class BookQueryTest extends BaseTest {

    @Test
    public void shouldReturnBooks() {

        String query = QueryLoader.load("books.graphql");

        Response response = GraphQLClient.execute(query);
        GraphQLAssertions.assertBooksReturned(response);
    }
}