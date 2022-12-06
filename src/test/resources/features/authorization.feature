Feature: Test the correct functionality of the authorization

  Scenario: Login correctly
    Given the client login with username "user1" and password "user1"
    Then the client receives status code of 200