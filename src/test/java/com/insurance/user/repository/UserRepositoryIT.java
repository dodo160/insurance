package com.insurance.user.repository;

import com.insurance.integration.AbstractMySqlIntegrationTest;
import com.insurance.user.enums.UserType;
import com.insurance.user.model.Client;
import com.insurance.user.model.Employee;
import com.insurance.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.HashSet;
import java.util.Set;

import static com.insurance.utils.TestUtils.buildUser;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryIT extends AbstractMySqlIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByIdTest() {
        final User employee = userRepository.findById(1L).orElse(null);
        assertNotNull(employee);
        assertTrue(employee instanceof Employee);

        final User client = userRepository.findById(2L).orElse(null);
        assertNotNull(client);
        assertTrue(client instanceof Client);
    }

    @Test
    public void saveTest() {
        final User newUser = buildUser(UserType.CLIENT);
        newUser.setId(null);

        final User savedUser = userRepository.save(newUser);

        final User userDb = userRepository.findById(savedUser.getId()).orElse(null);

        assertNotNull(userDb);
        assertEquals(newUser.getFirstName(), userDb.getFirstName());
        assertEquals(newUser.getLastName(), userDb.getLastName());
        assertEquals(newUser.getPostCode(), userDb.getPostCode());
        assertEquals(newUser.getIdentityId(), userDb.getIdentityId());
        assertEquals(newUser.getUserType(), userDb.getUserType());
        assertEquals(newUser.getCity(), userDb.getCity());
        assertEquals(newUser.getAddress(), userDb.getAddress());

        final Set<User> users = new HashSet<>();
        userRepository.findAll().forEach(users::add);

        assertFalse(users.isEmpty());
        assertEquals(3, users.size());
    }

    @Test
    public void findAllTest() {
        final Set<User> users = new HashSet<>();
        userRepository.findAll().forEach(users::add);

        assertFalse(users.isEmpty());
        assertEquals(2, users.size());
    }

    @Test
    public void updateTest() {
        final User user = userRepository.findById(1L).orElse(null);
        assertNotNull(user);

        user.setIdentityId("7844");

        final User savedUser = userRepository.save(user);
        assertNotNull(savedUser);
        assertEquals(user.getIdentityId(), savedUser.getIdentityId());
    }
}
