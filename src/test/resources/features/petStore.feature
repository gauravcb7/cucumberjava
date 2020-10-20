#Author: gaurav.x.srivastava@gmail.com

@demo
Feature: Test scenario based on pet store

  @tag1
  Scenario: Perform validator on pet store
    Given Rest Assured: Get all pets from store based on status "available" and verify expected results
    And Rest Assured: Add new pet to store with name "Honda" and status "available" and verify new pet added
    When Rest Assured: Update the pet status to "sold" for pet name "Jerry" and verify the expected status
    Then Rest Assured: Delete the pet and verify deletion
 
