Feature: Category Deletion
  As an authenticated user with appropriate permissions
  I want to delete a category
  So that I can remove incorrect or unused categories

  @simple
  Scenario: Delete category as admin
    Given the user is authenticated as admin
    And a category exists with name "TempCategory"
    When the admin deletes that category
    Then the category should be deleted successfully