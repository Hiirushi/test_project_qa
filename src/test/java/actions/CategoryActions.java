package actions;

import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.util.EnvironmentVariables;
import net.thucydides.model.environment.SystemEnvironmentVariables;

import java.util.List;

public class CategoryActions {

    private Response lastResponse;
    private List<Integer> existingCategoryIds;
    
    private final EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
    
    private String getBaseUrl() {
        return EnvironmentSpecificConfiguration.from(environmentVariables)
            .getProperty("api.base.url");
    }

    /**
     * Retrieves auth token from Serenity session (set by AuthenticationActions)
     */
    private String getAuthToken() {
        return Serenity.sessionVariableCalled("authToken");
    }

    @Step("Retrieve existing category IDs from the system")
    public void fetchExistingCategoryIds() {
        Response response = SerenityRest.given()
            .header("Authorization", "Bearer " + getAuthToken())
            .when()
            .get(getBaseUrl() + "/api/categories");
        
        if (response.getStatusCode() == 200) {
            existingCategoryIds = response.jsonPath().getList("id", Integer.class);
        }
    }

    @Step("Send GET request for category with non-existent ID")
    public void getCategoryWithNonExistentId() {
        long nonExistentId = generateNonExistentId();
        
        lastResponse = SerenityRest.given()
            .header("Authorization", "Bearer " + getAuthToken())
            .when()
            .get(getBaseUrl() + "/api/categories/" + nonExistentId);
    }

    @Step("Send GET request for category with ID: {0}")
    public void getCategoryById(long categoryId) {
        lastResponse = SerenityRest.given()
            .header("Authorization", "Bearer " + getAuthToken())
            .when()
            .get(getBaseUrl() + "/api/categories/" + categoryId);
    }

    @Step("Get response status code")
    public int getLastResponseStatusCode() {
        return lastResponse.getStatusCode();
    }

    @Step("Get response body")
    public String getLastResponseBody() {
        return lastResponse.getBody().asString();
    }

    private long generateNonExistentId() {
        long nonExistentId = 999999L;
        if (existingCategoryIds != null && !existingCategoryIds.isEmpty()) {
            nonExistentId = existingCategoryIds.stream()
                    .mapToInt(Integer::intValue)
                    .max()
                    .orElse(0) + 99999;
        }
        return nonExistentId;
    }
}
