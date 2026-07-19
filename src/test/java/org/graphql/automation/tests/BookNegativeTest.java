package org.graphql.automation.tests;

import io.restassured.response.Response;
import org.graphql.automation.base.BaseTest;
import org.graphql.automation.client.GraphQLClient;
import org.graphql.automation.utils.GraphQLAssertions;
import org.graphql.automation.utils.QueryLoader;
import org.testng.annotations.Test;

public class BookNegativeTest extends BaseTest {

    @Test
    public void shouldReturnErrorForInvalidBookField() {

        Response response = GraphQLClient.execute(
                QueryLoader.load("invalid-book-field.graphql")
        );

        GraphQLAssertions.assertGraphQLError(
                response,
                "invalidField"
        );
    }

    @Test
    public void shouldReturnErrorWhenBookIdIsMissing() {

        Response response = GraphQLClient.execute(
                QueryLoader.load("book-without-id.graphql")
        );

        GraphQLAssertions.assertGraphQLError(
                response,
                "id"
        );
    }
}