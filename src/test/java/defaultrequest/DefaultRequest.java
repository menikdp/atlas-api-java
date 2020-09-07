package defaultrequest;

import configuration.DataConfiguration;
import constant.ApiConstant;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class DefaultRequest {
    public RequestSpecification service() {
        DataConfiguration dataConfiguration = new DataConfiguration();

        return given().log().all().baseUri(dataConfiguration.getData("url"))
                .contentType(ContentType.JSON).accept(ContentType.JSON);
    }
}
