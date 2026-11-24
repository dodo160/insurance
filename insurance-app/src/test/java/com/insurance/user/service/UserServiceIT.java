package com.insurance.user.service;

import com.insurance.common.exception.NotFoundException;
import com.insurance.integration.AbstractMySqlIntegrationTest;
import com.insurance.user.enums.UserType;
import com.insurance.user.model.User;
import com.insurance.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.insurance.utils.TestUtils.buildUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceIT extends AbstractMySqlIntegrationTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findAllTest() {
        when(userRepository.findAll()).thenReturn(Set.of(new User()));

        final List<User> result = userService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    public void findByIdTest() {
        when(userRepository.findById(1l)).thenReturn(Optional.of(buildUser(UserType.CLIENT)));

        final User result = userService.findById(1l);

        assertNotNull(result);
        assertEquals(Long.valueOf(1), result.getId());
    }

    @Test
    public void deleteByIdTest() {
        when(userRepository.findById(1l)).thenReturn(Optional.of(buildUser(UserType.CLIENT)));
        try {
            userService.deleteById(1L);
        } catch (NotFoundException e) {
            fail();
        }
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void deleteByIdExceptionTest() {
        try {
            userService.deleteById(1L);
        } catch (NotFoundException e) {
            assertEquals("User not found", e.getMessage());
        }
    }

    @Test
    public void softDeleteByIdTest() {
        final User user = buildUser(UserType.CLIENT);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.softDeleteById(1L);

        assertNotNull(user.getDeletedDate());
    }

    @Test()
    public void softDeleteByIdNullInsuranceTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            userService.softDeleteById(1L);
        } catch (NotFoundException e) {
            assertEquals("User not found", e.getMessage());
        }
    }

    @Test
    public void addTest() {
        final User user = buildUser(UserType.CLIENT);

        userService.add(user);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void updateTest() {
        final User user = buildUser(UserType.CLIENT);
        user.setFirstName("FIRST_NAME_UPDATED");
        user.setLastName("LAST_NAME_UPDATED");
        user.setAddress("ADDRESS_UPDATED");
        user.setCity("CITY_UPDATED");
        user.setPostCode("5555");

        when(userRepository.findById(1L)).thenReturn(Optional.of(buildUser(UserType.CLIENT)));

        try {
            final User result = userService.update(user.getId(), user);

            assertNotNull(result);
            assertEquals(user.getFirstName(), result.getFirstName());
            assertEquals(user.getLastName(), result.getLastName());
            assertEquals(user.getAddress(), result.getAddress());
            assertEquals(user.getCity(), result.getCity());
            assertEquals(user.getPostCode(), result.getPostCode());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void updateTariffDoesntExistTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        final User user = buildUser(UserType.CLIENT);

        try {
            userService.update(user.getId(), user);
            fail();
        } catch (NotFoundException nfe) {
            assertEquals("User not found", nfe.getMessage());
        }
    }
}
