package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ServiceEndpointResourceTest {

    @Test
    public void testUploadReportEndpoint() {
        given()
                .multiPart("file", new File("data/report_1.csv"))
                .formParam("fileName", "report_1.csv")
                .when().post("/service/uploadReport")
                .then()
                .statusCode(200)
                .body(is("OK"));
    }
}