package actions;

import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.util.EnvironmentVariables;
import net.thucydides.model.environment.SystemEnvironmentVariables;

public class AuthenticationActions {

    private String authToken;
    
    private final EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();
    
    private String getBaseUrl() {
        return EnvironmentSpecificConfiguration.from(environmentVariables)
            .getProperty("api.base.url");    }

    @Step("Authenticate user")
    public void authenticateUser() {
        // Read from serenity.conf (test.user.username and test.user.password)
        String username = EnvironmentSpecificConfiguration.from(environmentVariables)
            .getOptionalProperty("test.user.username")
            .orElseThrow(() -> new RuntimeException("test.user.username not configured in serenity.conf"));
        String password = EnvironmentSpecificConfiguration.from(environmentVariables)
            .getOptionalProperty("test.user.password")
            .orElseThrow(() -> new RuntimeException("test.user.password not configured in serenity.conf"));
        
        Response response = SerenityRest.given()
            .contentType("application/json")
            .body("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}")
            .when()
            .post(getBaseUrl() + "/api/auth/login");
        
        if (response.getStatusCode() != 200) {
            throw new IllegalStateException(
                "Authentication failed with status " + response.getStatusCode() + 
                ": " + response.getBody().asString());
        }
        authToken = response.jsonPath().getString("token");
        
        // Store token in Serenity session for use across action classes
        Serenity.setSessionVariable("authToken").to(authToken);
    }

    public String getAuthToken() {
        return authToken;
    }
}
