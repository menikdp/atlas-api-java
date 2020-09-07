import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import com.github.javafaker.Faker;
import configuration.DataConfiguration;
import controller.StudentController;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.Student;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class ApiJavaAutomationTest {

  String test = "THIS IS THE LATEST VERSION";

  public final static String URL = "http://localhost:8080";

  //  faker untuk generate random data
  public Faker faker = new Faker();

  public String generateRandomName() {
    return faker.name().fullName();
  }

  public String generateRandomPassportNumber() {
    return faker.numerify("#########");
  }

  StudentController studentController = new StudentController();

  @Test
  public void getListStudents() {
    Response response =  studentController.getAllStudents();

    int statusCode = response.getStatusCode();
    Assert.assertEquals(200, statusCode);
    System.out.println("The response status is " + statusCode);
  }


  @Test
  public void getSingleStudent() {
    Response responseGetAll =  studentController.getAllStudents();
    Response responseGetSingle = studentController.getSingleStudent(responseGetAll.path("id[0]"));

    int statusCode = responseGetSingle.getStatusCode();
    Assert.assertEquals(200, statusCode);
    Assert.assertThat(responseGetSingle.getBody().asString(), matchesJsonSchemaInClasspath("schemas/getSingleStudent.json"));
    System.out.println("The response status is " + statusCode);
  }


  @Test
  public void addStudent() {
    Student studentRequestBody = new Student();

    DataConfiguration dataConfiguration = new DataConfiguration();

    studentRequestBody.setName(dataConfiguration.getData("name"));
    studentRequestBody.setPassportNumber(dataConfiguration.getData("passport"));

    Response response =  studentController.addStudent(studentRequestBody);

    int statusCode = response.getStatusCode();
    Assert.assertEquals(200, statusCode);
    System.out.println("The response status is " + statusCode);

//    deserialization
    Student studentResponse = response.getBody().as(Student.class);
    System.out.println("id: " + studentResponse.getId());
    System.out.println("name: " + studentResponse.getName());
    System.out.println("passport: " + studentResponse.getPassportNumber());
  }


  @Test
  public void updateStudent() {
    Student studentRequestBody = new Student();
    studentRequestBody.setName(generateRandomName());
    studentRequestBody.setPassportNumber(generateRandomPassportNumber());

    Response responseGetAll =  studentController.getAllStudents();
    Response responseUpdate = studentController.updateStudent(studentRequestBody,responseGetAll.path("id[0]"));

    int statusCode = responseUpdate.getStatusCode();
    Assert.assertEquals(200, statusCode);
    System.out.println("The responseAdd status is " + statusCode);

    studentController.getAllStudents();
  }


  @Test
  public void deleteStudent() {
    Response responseGet =  studentController.getAllStudents();
    Response responseDelete = studentController.deleteStudent(responseGet.path("id[0]"));

    int statusCode = responseDelete.getStatusCode();
    Assert.assertEquals(200, statusCode);
    System.out.println("The response status is " + statusCode);
  }

}
