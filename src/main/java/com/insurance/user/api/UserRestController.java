package com.insurance.user.api;

import com.insurance.common.exception.RequestMismatchException;
import com.insurance.user.dto.UserDTO;
import com.insurance.user.enums.UserType;
import com.insurance.user.mapper.ClientMapper;
import com.insurance.user.mapper.EmployeeMapper;
import com.insurance.user.mapper.UserMapper;
import com.insurance.user.model.Client;
import com.insurance.user.model.Employee;
import com.insurance.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final ClientMapper clientMapper;

    private final EmployeeMapper employeeMapper;

    public UserRestController(UserService userService, UserMapper userMapper, ClientMapper clientMapper, EmployeeMapper employeeMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.clientMapper = clientMapper;
        this.employeeMapper = employeeMapper;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getById(@PathVariable final Long id) {
        return ResponseEntity.ok(userMapper.toDto(userService.findById(id)));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll().stream().map(userMapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody final UserDTO userDTO) {
        UserDTO body = null;
        if (UserType.CLIENT == userDTO.getUserType()) {
            body = clientMapper.toDto((Client) userService.add(clientMapper.fromDto(userDTO)));
        }

        if (UserType.EMPLOYEE == userDTO.getUserType()) {
            body = employeeMapper.toDto((Employee) userService.add(employeeMapper.fromDto(userDTO)));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> updateUser(@PathVariable final Long id, @Valid @RequestBody final UserDTO userDTO) {
        if (Objects.nonNull(userDTO.getId()) && !id.equals(userDTO.getId())) {
            throw new RequestMismatchException(
                    "User ID in path does not match user ID in request body");
        }
        UserDTO body = null;

        if (UserType.CLIENT == userDTO.getUserType()) {
            body = clientMapper.toDto((Client) userService.update(id, clientMapper.fromDto(userDTO)));
        }

        if (UserType.EMPLOYEE == userDTO.getUserType()) {
            body = employeeMapper.toDto((Employee) userService.update(id, employeeMapper.fromDto(userDTO)));
        }
        return ResponseEntity.ok(body);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") final Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/softDelete/{id}")
    public ResponseEntity<Void> softDeleteUser(@PathVariable("id") final Long id) {
        userService.softDeleteById(id);
        return ResponseEntity.noContent().build();
    }
}
