Feature: Authorization
  @positive
  Scenario: Login (Positive)
    Given User is on the Login Page
    When User inputs valid username "damaarsi01" and password "DamaarsiDiHati"
    And User clicks the login button
    Then User should be redirected to the Superadmin Dashboard

  @negative
  Scenario: Login (Negative)
    Given User is on the Login Page
    When User inputs invalid username "" and password "wrong"
    And User clicks the login button
    Then User should see validation error messages
