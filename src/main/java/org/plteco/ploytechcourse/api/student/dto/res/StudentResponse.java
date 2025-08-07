package org.plteco.ploytechcourse.api.student.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.plteco.ploytechcourse.domain.application.model.Student;

@Data
@JsonPropertyOrder({ "student_id", "name" })
public class StudentResponse {

    @JsonProperty("student_id")
    private Long studentId;

    private String name;

    static public StudentResponse from(Student student) {
        StudentResponse response = new StudentResponse();
        response.setStudentId(student.getId());
        response.setName(student.getUser().getName());
        return response;
    }
}
