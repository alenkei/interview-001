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
                .statusCode(200);
    }

    @Test
    public void testGetRecordEndpoint() {
        given()
                .pathParam("recID", 1)
                .when().get("/service/records/{recID}")
                .then()
                .statusCode(200)
                .body(is("ReportDataEntity{id=1, recID=1, firstName='Andi', lastName='Eldon', emailAddress='aeldon0@prweb.com', gender='Female', usdBalance=81.34, eurBalance=966.0, yenBalance=84.0, gbpBalance=279.0}"));
    }

    @Test
    public void testGetAllEndpoint() {
        given()
                .when().get("/service/records")
                .then()
                .statusCode(200);
    }

    @Test
    public void testDeleteReport() {
        given()
                .pathParam("reportID", 1001)
                .when().delete("/service/deleteReport/{reportID}")
                .then()
                .statusCode(200);
    }

}