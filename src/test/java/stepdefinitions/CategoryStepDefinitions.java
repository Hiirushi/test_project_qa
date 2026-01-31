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

    @Given("the user is authenticated as admin")
    public void theUserIsAuthenticatedAsAdmin() {
        authenticationActions.authenticateAsAdmin();
    }

    @Given("the user is authenticated as user")
    public void theUserIsAuthenticatedAsUser() {
        authenticationActions.authenticateUser();
    }

    private Integer lastCreatedCategoryId;

    @Given("a category exists with name {string}")
    public void aCategoryCreatesWithName(String name) {
        categoryActions.createCategory(name);
        lastCreatedCategoryId = categoryActions.getLastCreatedCategoryId();
    }

    @When("the admin creates a category with valid name {string}")
    public void theAdminCreatesACategoryWithName(String categoryName) {
        categoryActions.createCategoryWithValidData(categoryName);
    }

    @Then("the category should be created successfully")
    public void theCategoryShouldBeCreatedSuccessfully() {
        assertThat(categoryActions.getLastResponseStatusCode())
            .as("Category creation should succeed")
            .isIn(200, 201);
    }

    @When("the admin creates a category with less than 3 characters {string}")
    public void theAdminCreatesACategoryWithLessCharacters(String categoryName) {
        categoryActions.createCategory(categoryName);
    }

    @When("the admin creates a category with more than 10 characters {string}")
    public void theAdminCreatesACategoryWithMoreCharacters(String categoryName) {
        categoryActions.createCategory(categoryName);
    }

    @When("the admin creates a category without name {string}")
    public void theAdminCreatesACategoryWithoutName(String categoryName) {
        categoryActions.createCategory(categoryName);
    }

    @When("the user creates a category with name {string}")
    public void theUserCreatesACategoryWithName(String categoryName) {
        categoryActions.createCategory(categoryName);
    }

    @Then("the category creation should fail")
    public void theCategoryCreationShouldFail() {
        assertThat(categoryActions.getLastResponseStatusCode())
            .as("Category creation should not succeed")
            .isIn(400, 401);
    }

    @Then("the user should be denied permission to create a category")
    public void theUserShouldBeDeniedPermissionToCreateACategory() {
        assertThat(categoryActions.getLastResponseStatusCode())
            .as("Category creation should not succeed")
            .isIn(403, 404);
    }

    @When("the admin deletes that category")
    public void theAdminDeletesThatCategory() {
        categoryActions.deleteCategoryById(categoryActions.getLastCreatedCategoryId());
    }

    @Then("the category should be deleted successfully")
    public void theCategoryShouldBeDeletedSuccessfully() {
        assertThat(categoryActions.getLastResponseStatusCode())
            .as("Category deletion should succeed")
            .isIn(204, 205);
    }
}