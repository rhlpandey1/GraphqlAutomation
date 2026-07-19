package org.graphql.automation.utils;

import io.restassured.response.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Offset.offset;

public final class GraphQLAssertions {

    private GraphQLAssertions() {
        // Utility class: do not create an object.
    }

    public static void assertSuccessfulResponse(Response response) {

        assertThat(response.statusCode())
                .as("HTTP response:%n%s", response.asPrettyString())
                .isEqualTo(200);

        Object graphQlErrors = response.jsonPath().get("errors");

        assertThat(graphQlErrors)
                .as("GraphQL errors:%n%s", response.asPrettyString())
                .isNull();
    }

    public static void assertBooksReturned(Response response) {

        assertSuccessfulResponse(response);

        List<Object> books = response.jsonPath().getList("data.books");

        assertThat(books)
                .as("Books response:%n%s", response.asPrettyString())
                .isNotNull()
                .isNotEmpty();

        assertThat(response.jsonPath().getString("data.books[0].title"))
                .isNotBlank();
    }

    public static void assertBookCreated(
            Response response,
            String expectedTitle,
            String expectedAuthor,
            double expectedPrice
    ) {
        assertSuccessfulResponse(response);

        assertThat(response.jsonPath().getString("data.createBook.id"))
                .isNotBlank();

        assertThat(response.jsonPath().getString("data.createBook.title"))
                .isEqualTo(expectedTitle);

        assertThat(response.jsonPath().getString("data.createBook.author"))
                .isEqualTo(expectedAuthor);

        assertThat(response.jsonPath().getDouble("data.createBook.price"))
                .isCloseTo(expectedPrice, offset(0.001));
    }
    public static void assertBookUpdated(
            Response response,
            String expectedTitle,
            String expectedAuthor,
            double expectedPrice
    ) {
        assertSuccessfulResponse(response);

        assertThat(response.jsonPath().getString("data.updateBook.id"))
                .isNotBlank();

        assertThat(response.jsonPath().getString("data.updateBook.title"))
                .isEqualTo(expectedTitle);

        assertThat(response.jsonPath().getString("data.updateBook.author"))
                .isEqualTo(expectedAuthor);

        assertThat(response.jsonPath().getDouble("data.updateBook.price"))
                .isCloseTo(expectedPrice, offset(0.001));
    }

    public static void assertBookDeleted(Response response) {
        assertSuccessfulResponse(response);

        assertThat(response.jsonPath().getBoolean("data.deleteBook"))
                .isTrue();
    }
    public static void assertGraphQLError(
            Response response,
            String expectedErrorText
    ) {
        assertThat(response.statusCode())
                .as("HTTP response:%n%s", response.asPrettyString())
                .isEqualTo(200);

        Object graphQlErrors = response.jsonPath().get("errors");

        assertThat(graphQlErrors)
                .as("Expected GraphQL errors but received:%n%s",
                        response.asPrettyString())
                .isNotNull();

        String errorMessage = response.jsonPath()
                .getString("errors[0].message");

        assertThat(errorMessage)
                .as("GraphQL error response:%n%s", response.asPrettyString())
                .containsIgnoringCase(expectedErrorText);
    }
}