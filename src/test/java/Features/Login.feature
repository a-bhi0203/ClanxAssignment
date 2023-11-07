Feature: Validating the login scenarios

  Scenario: Successful login to the system
    Given User reads data from test data file "src/main/resources/testData/loginTestData.json" for test case "validLogin"
    When User sets the login body for valid login
    When User calls the login API and checks the expected code
    Then  Login API should return a valid token

  Scenario Outline: Test login with invalid credentials
    Given User Sets the login body invalid "<username>" and "<password>"
    When User calls the login API and checks the expected error <code>
    Then  Login API should return a error "<message>"

    Examples:
      |username | password | code | message |
      |abc | password123 | 200 | Bad credentials |
      |admin | passwordw | 200 | Bad credentials |
      | |  | 200 | Bad credentials |
