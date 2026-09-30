package com.insurance.rest;

import com.insurance.enums.UserType;
import com.insurance.mapper.ClientMapper;
import com.insurance.mapper.EmployeeMapper;
import com.insurance.mapper.UserMapper;
import com.insurance.model.Client;
import com.insurance.model.Employee;
import com.insurance.modeldto.UserDTO;
import com.insurance.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
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

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable final Long id) {
        return ResponseEntity.ok(userMapper.toDto(userService.findById(id)));
    }

    @GetMapping()
    public ResponseEntity<Set<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll().stream().map(userMapper::toDto).collect(Collectors.toSet()));
    }

    @PostMapping()
    public ResponseEntity<UserDTO> addUser(@RequestBody final UserDTO userDTO) {
        UserDTO body = null;
        if (UserType.CLIENT == userDTO.getUserType()) {
            body = clientMapper.toDto((Client) userService.add(clientMapper.fromDto(userDTO)));
        }

        if (UserType.EMPLOYEE == userDTO.getUserType()) {
            body = employeeMapper.toDto((Employee) userService.add(employeeMapper.fromDto(userDTO)));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @PutMapping()
    public ResponseEntity<UserDTO> updateUser(@RequestBody final UserDTO userDTO) {
        UserDTO body = null;

        if (UserType.CLIENT == userDTO.getUserType()) {
            body = clientMapper.toDto((Client) userService.update(clientMapper.fromDto(userDTO)));
        }

        if (UserType.EMPLOYEE == userDTO.getUserType()) {
            body = employeeMapper.toDto((Employee) userService.update(employeeMapper.fromDto(userDTO)));
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
