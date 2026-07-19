package org.graphql.automation.client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.graphql.automation.config.ConfigManager;

import java.util.LinkedHashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public final class GraphQLClient {

    private GraphQLClient() {
        // Utility class: do not create an object.
    }

    public static Response execute(String query) {
        return execute(query, null);
    }

    public static Response execute(
            String query,
            Map<String, Object> variables
    ) {
        Map<String, Object> requestBody = new LinkedHashMap<>();

        requestBody.put("query", query);

        if (variables != null && !variables.isEmpty()) {
            requestBody.put("variables", variables);
        }

        return given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post()
                .then()
                .extract()
                .response();
    }
}