package com.insurance.user.api;

import com.insurance.common.exception.ErrorResponse;
import com.insurance.common.exception.RequestMismatchException;
import com.insurance.user.dto.UserDTO;
import com.insurance.user.enums.UserType;
import com.insurance.user.mapper.ClientMapper;
import com.insurance.user.mapper.EmployeeMapper;
import com.insurance.user.mapper.UserMapper;
import com.insurance.user.model.Client;
import com.insurance.user.model.Employee;
import com.insurance.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User", description = "User management operations")
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

    @Operation(summary = "Get user by ID", description = "Returns an user contract by its identifier")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDTO> getById(@PathVariable final Long id) {
        return ResponseEntity.ok(userMapper.toDto(userService.findById(id)));
    }

    @Operation(summary = "Get all users", description = "Returns all users")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Users found"),
            @ApiResponse(responseCode = "404", description = "No users found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll().stream().map(userMapper::toDto).collect(Collectors.toList()));
    }

    @Operation(summary = "Create user", description = "Creates a new user and returns the created user.")
    @ApiResponses({@ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Validation failed or request constraints were violated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
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

    @Operation(summary = "Update user by id", description = "Updates an existing user.")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Validation failed or request constraints were violated",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
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

    @Operation(summary = "Delete user by id", description = "Deletes an exist user.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") final Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Soft delete user by id", description = "Mark user as deleted.")
    @ApiResponses({@ApiResponse(responseCode = "204", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class)))})
    @PutMapping("/softDelete/{id}")
    public ResponseEntity<Void> softDeleteUser(@PathVariable("id") final Long id) {
        userService.softDeleteById(id);
        return ResponseEntity.noContent().build();
    }
}
