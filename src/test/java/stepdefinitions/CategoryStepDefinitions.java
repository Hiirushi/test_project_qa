package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import net.serenitybdd.annotations.Steps;
import actions.CategoryActions;
import actions.AuthenticationActions;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryStepDefinitions {

    @Steps
    CategoryActions categoryActions;

    @Steps
    AuthenticationActions authenticationActions;

    @Given("the user is authenticated")
    public void theUserIsAuthenticated() {
        authenticationActions.authenticateUser();
        // Auth token is stored in Serenity session by AuthenticationActions
    }

    @Given("the following category IDs exist in the database")
    public void theFollowingCategoryIDsExistInTheDatabase() {
        categoryActions.fetchExistingCategoryIds();
    }

    @When("the user sends a GET request to view category with a non-existent ID")
    public void theUserSendsAGETRequestToViewCategoryWithANonExistentID() {
        categoryActions.getCategoryWithNonExistentId();
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        assertThat(categoryActions.getLastResponseStatusCode())
            .isEqualTo(expectedStatusCode);
    }

    @Then("the response should contain error message {string}")
    public void theResponseShouldContainErrorMessage(String expectedMessage) {
        assertThat(categoryActions.getLastResponseBody())
            .contains(expectedMessage);
    }
}
