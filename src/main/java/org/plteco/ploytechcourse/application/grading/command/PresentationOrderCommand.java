package org.plteco.ploytechcourse.application.grading.command;

import org.plteco.ploytechcourse.api.grading.form.dto.req.OrderDto;
import org.plteco.ploytechcourse.api.grading.form.dto.req.PresentationOrderDto;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.stream.Collectors;

public record PresentationOrderCommand(Map<Long, Integer> changes) {
    public static PresentationOrderCommand fromDto(PresentationOrderDto dto) {
        if (dto == null || dto.getOrders() == null) {
            throw new PltecoException("요청이 비었습니다.", HttpStatus.BAD_REQUEST);
        }


        Map<Long, Integer> map = dto.getOrders().stream().collect(
                Collectors.toMap(
                        OrderDto::getStudentId,
                        OrderDto::getOrder,
                        (a, b) -> { throw new PltecoException("중복된 studentId가 있습니다.", HttpStatus.BAD_REQUEST); }
                )
        );
        return new PresentationOrderCommand(map);
    }
}