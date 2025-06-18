Feature: Data Admin

  Background:
    Given User Should Login with valid credential
    Then User should be redirected to the Superadmin Dashboard

  Scenario: Mengaktifkan akun admin dari status nonaktif
    Given User is on the Data Admin Page
    When User selects an admin with "nonaktif" status
    And User clicks the "Aktifkan" button for the selected admin
    Then The selected admin's status should change to "admin"
    And The button for the selected admin should change to "Nonaktifkan"

  Scenario: Menonaktifkan akun admin dari status aktif
    Given User is on the Data Admin Page
    When User selects an admin with "admin" status
    And User clicks the "Nonaktifkan" button for the selected admin
    Then The selected admin's status should change to "nonaktif"
    And The button for the selected admin should change to "Aktifkan"