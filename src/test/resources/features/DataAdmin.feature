Feature: Data Admin

  Scenario: Aktivasi akun admin
    Given User is on the Data Admin Page
    When User selects a non-active admin
    And User clicks the "Aktivasi" button
    Then The admin account status should change to active
    And The "Aktivasi" button should change to "Non-Aktivasi"
