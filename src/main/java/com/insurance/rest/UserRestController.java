package com.insurance.rest;

import com.insurance.enums.UserType;
import com.insurance.mapper.ClientMapper;
import com.insurance.mapper.CycleAvoidingMappingContext;
import com.insurance.mapper.EmployeeMapper;
import com.insurance.mapper.UserMapper;
import com.insurance.modeldto.UserDTO;
import com.insurance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/app")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable final Long id) {
        return new ResponseEntity<>(userMapper.userToUserDTO(userService.findById(id), new CycleAvoidingMappingContext()), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<Set<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll().stream().map(x-> userMapper.userToUserDTO(x, new CycleAvoidingMappingContext())).collect(Collectors.toSet()), HttpStatus.OK);
    }

    @PostMapping("/user/add")
    public ResponseEntity<Void> addUser(@RequestBody final UserDTO userDTO) throws ValidationException {
        if(UserType.CLIENT == userDTO.getUserType()){
            userService.add(clientMapper.userDTOtoClient(userDTO, new CycleAvoidingMappingContext()));
        }

        if(UserType.EMPLOYEE == userDTO.getUserType()){
            userService.add(employeeMapper.userDTOtoEmployee(userDTO, new CycleAvoidingMappingContext()));
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") final Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/user/softDelete/{id}")
    public ResponseEntity<Void> softDeleteUser(@PathVariable("id") final Long id) throws ValidationException {
        userService.softDeleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
