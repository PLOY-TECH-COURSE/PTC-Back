package org.plteco.ploytechcourse.api.grading.form.dto.res;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.plteco.ploytechcourse.api.grading.form.dto.req.OrderDto;

@EqualsAndHashCode(callSuper = true)
@Data
public class PresentationOrderResponseDto extends OrderDto {
    private String name;
    private boolean completed;

    public static PresentationOrderResponseDto fromOrder(Long studentId, Integer order,String name, boolean completed) {
        PresentationOrderResponseDto dto = new PresentationOrderResponseDto();
        dto.setStudentId(studentId);
        dto.setOrder(order);
        dto.setName(name);
        dto.setCompleted(completed);
        return dto;
    }
}
