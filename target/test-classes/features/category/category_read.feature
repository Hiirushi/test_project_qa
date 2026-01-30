@API @Category
Feature: Category Read Operations
  As an authenticated user
  I want to view categories
  So that I can see category details

  Background:
    Given the user is authenticated

  @API_Category_Read_007
  Scenario: View non-existent category returns 404 error
    Given the following category IDs exist in the database
    When the user sends a GET request to view category with a non-existent ID
    Then the response status code should be 404
    And the response should contain error message "Category not found"
