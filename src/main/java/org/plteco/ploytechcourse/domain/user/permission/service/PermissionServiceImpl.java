package org.plteco.ploytechcourse.domain.user.permission.service;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.plteco.ploytechcourse.application.application.dto.ShowApplicationDto;
import org.plteco.ploytechcourse.application.user.permission.dto.ShowPermissionDto;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.RoleEnum;
import org.plteco.ploytechcourse.domain.user.signup.model.entity.User;
import org.plteco.ploytechcourse.domain.user.signup.repository.UserRepository;
import org.plteco.ploytechcourse.shared.exception.PltecoException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final UserRepository userRepository;


    @Override
    public List<ShowPermissionDto> showPermissions() {
        List<User> users = userRepository.findAll();
        List<ShowPermissionDto> showPermissionDtos = new ArrayList<>();
        for (User user : users) {
            ShowPermissionDto showPermissionDto = ShowPermissionDto.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();
            showPermissionDtos.add(showPermissionDto);
        }
        return showPermissionDtos;
    }

    @Override
    public void changePermissions(Long id, RoleEnum role) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new PltecoException("존재하지 않는 유저입니다.", HttpStatus.BAD_REQUEST));
        user.updateRole(role);
    }
}
