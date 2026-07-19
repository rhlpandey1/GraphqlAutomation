package org.graphql.automation.tests;

import io.restassured.response.Response;
import org.graphql.automation.base.BaseTest;
import org.graphql.automation.client.GraphQLClient;
import org.graphql.automation.utils.GraphQLAssertions;
import org.graphql.automation.utils.JsonDataLoader;
import org.graphql.automation.utils.QueryLoader;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class BookLifecycleTest extends BaseTest {

    @Test
    public void shouldCreateUpdateAndDeleteBook() {

        // 1. Create a new book.
        Response createResponse = GraphQLClient.execute(
                QueryLoader.load("create-book.graphql"),
                JsonDataLoader.loadAsMap("lifecycle-book.json")
        );

        GraphQLAssertions.assertBookCreated(
                createResponse,
                "Book Lifecycle Test",
                "Automation Framework",
                500.0
        );

        String bookId = createResponse.jsonPath()
                .getString("data.createBook.id");

        // 2. Update the same book using its generated ID.
        Map<String, Object> updateVariables = new HashMap<>(
                JsonDataLoader.loadAsMap("update-lifecycle-book.json")
        );

        updateVariables.put("id", bookId);

        Response updateResponse = GraphQLClient.execute(
                QueryLoader.load("update-book.graphql"),
                updateVariables
        );

        GraphQLAssertions.assertBookUpdated(
                updateResponse,
                "Book Lifecycle Test - Updated",
                "Automation Framework",
                750.0
        );

        // 3. Delete the same book using its generated ID.
        Response deleteResponse = GraphQLClient.execute(
                QueryLoader.load("delete-book.graphql"),
                Map.of("id", bookId)
        );

        GraphQLAssertions.assertBookDeleted(deleteResponse);
    }
}