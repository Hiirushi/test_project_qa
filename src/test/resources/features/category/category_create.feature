Feature: Category Creation
  As an authenticated user with appropriate permissions
  I want to create a category
  So that I can organize plants

  @simple
  Scenario: Create category with valid name
    Given the user is authenticated as admin
    When the admin creates a category with valid name "Flowers"
    Then the category should be created successfully

  @simple
  Scenario: Create category with less than 3 characters
    Given the user is authenticated as admin
    When the admin creates a category with less than 3 characters "a"
    Then the category creation should fail

  @simple
  Scenario: Create category with more than 10 characters
    Given the user is authenticated as admin
    When the admin creates a category with more than 10 characters "abcdefghijkl"
    Then the category creation should fail

  @simple
  Scenario: Create category without name
    Given the user is authenticated as admin
    When the admin creates a category without name ""
    Then the category creation should fail

  @simple
  Scenario: Create category as user
    Given the user is authenticated as user
    When the user creates a category with name "abcd"
    Then the user should be denied permission to create a category