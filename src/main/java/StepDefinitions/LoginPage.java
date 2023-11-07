package StepDefinitions;

import APIs.customerAPIs.Login;
import POJOs.login.LoginResponse;
import POJOs.login.SetLoginBody;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.cucumber.java.en.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import utils.CommonUtils;

import java.io.*;

import static junit.framework.TestCase.*;

public class LoginPage {
    Login login = new Login();
    JSONObject loginData;
    LoginResponse validate;
    @Given("User reads data from test data file {string} for test case {string}")
    public void user_reads_data_from_test_data_file_for_test_case(String filepath, String testCaseName) throws IOException, ParseException {
        loginData = CommonUtils.getTestData(filepath, testCaseName);
    }

    @When("User sets the login body for valid login")
    public void user_sets_the_login_body_for_valid_login() {
        login.setBody(loginData);
    }

    @When("User calls the login API and checks the expected code")
    public void user_calls_the_login_api_and_checks_the_expected_code() throws IOException {
        Long expectedCode = (Long) loginData.get("expectedCode");
        int expectedCodeAsInteger = expectedCode.intValue();
        validate = login.userLogin(expectedCodeAsInteger);
    }

    @Then("Login API should return a valid token")
    public void login_api_should_return_a_valid_token() {
        assertNotNull("Token is empty", validate.getToken());
    }

    @Given("User Sets the login body invalid {string} and {string}")
    public void user_sets_the_login_body_invalid_username_and_password(String username, String password) {
        JSONObject data = new JSONObject();
        data.put("username", username);
        data.put("password", password);
        login.setBody(data);
    }
    @When("User calls the login API and checks the expected error {int}")
    public void user_calls_the_login_api_and_checks_the_expected_error(Integer code) throws IOException {
        validate = login.userLogin(code);
    }
    @Then("Login API should return a error {string}")
    public void login_api_should_return_a_error_bad_credentials(String message) {
        assertEquals("Error message is not correct", message, validate.getReason());
    }

}
