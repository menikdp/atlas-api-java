package controller;

import constant.ApiConstant;
import defaultrequest.DefaultRequest;
import io.restassured.response.Response;
import model.Student;


public class StudentController extends DefaultRequest {

    public Response getAllStudents() {
        Response response = service()
                .queryParam("page", 2)
                .get(ApiConstant.GET_ALL_STUDENT);

        response.getBody().prettyPrint();
        return response;
    }

    public Response getSingleStudent(Integer id) {
        Response responseGetSingle = service()
                .pathParam("id", id)
                .get(ApiConstant.GET_SINGLE_STUDENT);

        responseGetSingle.getBody().prettyPrint();
        return responseGetSingle;
    }

    public Response addStudent(Student studentRequest) {
        Response responsePostStudent = service()
                .body(studentRequest)
                .post(ApiConstant.ADD_SINGLE_STUDENT);

        responsePostStudent.getBody().prettyPrint();
        return responsePostStudent;
    }

    public Response updateStudent(Student studentRequest, Integer id) {
        Response responseUpdate = service()
                .body(studentRequest)
                .pathParam("id", id)
                .put(ApiConstant.UPDATE_SINGLE_STUDENT);

        responseUpdate.getBody().prettyPrint();
        return responseUpdate;
    }

    public Response deleteStudent(Integer id) {
        Response responseDelete = service()
                .pathParam("id", id)
                .delete(ApiConstant.DELETE_SINGLE_STUDENT);
        return responseDelete;
    }
}
