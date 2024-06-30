package com.team2final.minglecrm.employee.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.team2final.minglecrm.auth.dto.Subject;
import com.team2final.minglecrm.auth.infrastructure.JwtUtil;
import com.team2final.minglecrm.employee.domain.Employee;
import com.team2final.minglecrm.employee.domain.repository.EmployeeRepository;
import com.team2final.minglecrm.employee.domain.repository.dao.RedisDao;
import com.team2final.minglecrm.employee.dto.request.SignInRequest;
import com.team2final.minglecrm.employee.dto.request.SignUpRequest;
import com.team2final.minglecrm.employee.dto.request.UpdateEmployeeRequest;
import com.team2final.minglecrm.employee.dto.response.EmployeeInfoResponse;
import com.team2final.minglecrm.employee.dto.response.SignInResponse;
import com.team2final.minglecrm.employee.dto.response.SignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RedisDao redisDao;

    @Transactional
    public SignUpResponse signUp(SignUpRequest signUpRequest) {

        if (employeeRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호 유효성 검사
        String password = signUpRequest.getPassword();
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("비밀번호는 최소 8자 이상이어야 합니다.");
        }

        String encodedPassword = passwordEncoder.encode(password);
        Employee employee = Employee.builder()
                .email(signUpRequest.getEmail())
                .password(encodedPassword)
                .name(signUpRequest.getName())
                .authority(signUpRequest.getAuthority())
                .build();

        employee.createEmployee(signUpRequest);
        employeeRepository.save(employee);
        return SignUpResponse.of(employee);
    }

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequest signInRequest) {
        Employee employee = employeeRepository.findByEmail(signInRequest.getEmail()).get();

        boolean matches = passwordEncoder.matches(signInRequest.getPassword(), employee.getPassword());
        if (!matches) {
            return null;
        }
        return SignInResponse.of(employee);
    }

    @Transactional(readOnly = true)
    public Boolean isEmailExists(String email) {
        Employee employee = employeeRepository.findByEmail(email).orElseThrow();
        return employee.getEmail().equals(email);
    }

    @Transactional(readOnly = true)
    public Boolean isValidEmailAndPassword(SignInRequest request) {
        Optional<Employee> tempEmployee = employeeRepository.findByEmail(request.getEmail());

        if (tempEmployee.isPresent()) {
            Employee employee = tempEmployee.get();

            if (!employee.getEmail().equals(request.getEmail())) {
                return false;
            }

            boolean matches = passwordEncoder.matches(request.getPassword(), employee.getPassword());
            if (!matches) {
                return false;
            }

            return true;

        } else {
            return false;
        }
    }


    @Transactional
    public Void logout(String rtk) throws JsonProcessingException {
        Subject subject = jwtUtil.getSubject(rtk);
        redisDao.getValues(subject.getEmail());
        redisDao.deleteValues(subject.getEmail());
        System.out.println(subject.getEmail() + "로그아웃이요");
        return null;
    }

    public EmployeeInfoResponse getEmployeeInfo(String employeeEmail) {
        Employee employee = employeeRepository.findByEmail(employeeEmail).orElseThrow();
        return EmployeeInfoResponse.of(employee);
    }

    @Transactional
    public void updateEmployeeInfo(UpdateEmployeeRequest updateEmployeeRequest, String employeeEmail) {
        Employee employee = employeeRepository.findByEmail(employeeEmail).orElseThrow();
        String encodedPassword = passwordEncoder.encode(updateEmployeeRequest.getPassword());
        employee.updateEmployeeInfo(encodedPassword);
    }
}
