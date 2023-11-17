package APIs.customerAPIs;
import APIs.Resources.APIResources;
import io.restassured.response.Response;
import POJOs.login.LoginResponse;
import POJOs.login.SetLoginBody;
import org.json.simple.JSONObject;
import utils.CommonUtils;
import java.io.IOException;
import static io.restassured.RestAssured.given;


public class Login extends CommonUtils {
    APIResources loginResource = APIResources.valueOf("login");
    SetLoginBody setLogin = new SetLoginBody();

    public void setBody(JSONObject loginData){
        if (loginData != null) {
            String username = (String) loginData.get("username");
            String password = (String) loginData.get("password");

            setLogin.setUsername(username);
            setLogin.setPassword(password);
        }
    }
    public LoginResponse userLogin(int status) throws IOException {
        Response res = given()
                .spec(requestSpecification())
                .header("Content-Type", "application/json")
                .log().all()
                .body(setLogin)
                .when()
                .post(loginResource.getResource())
                .then()
                .assertThat().statusCode(status)
                .extract().response();
        LoginResponse resp = res.as(LoginResponse.class);
        return resp;

    }
}
