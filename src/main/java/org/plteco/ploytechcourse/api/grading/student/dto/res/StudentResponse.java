package org.plteco.ploytechcourse.api.grading.student.dto.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.plteco.ploytechcourse.domain.application.model.Student;

@Data
@JsonPropertyOrder({ "student_id", "name" })
@Schema(description = "학생 응답 DTO")
public class StudentResponse {

    @JsonProperty("student_id")
    @Schema(
        description = "학생 ID",
        required = true
    )
    private Long studentId;

    @Schema(
        description = "학생 이름",
        required = true
    )
    private String name;

    static public StudentResponse from(Student student) {
        StudentResponse response = new StudentResponse();
        response.setStudentId(student.getId());
        response.setName(student.getUser().getName());
        return response;
    }
}
