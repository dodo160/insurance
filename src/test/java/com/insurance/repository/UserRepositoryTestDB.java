package com.insurance.repository;

import com.insurance.enums.UserType;
import com.insurance.model.Client;
import com.insurance.model.Employee;
import com.insurance.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Set;

import static com.insurance.TestUtils.buildUser;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTestDB {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByIdTest(){
        final User employee = userRepository.findById(1L).orElse(null);
        Assert.assertNotNull(employee);
        Assert.assertTrue(employee instanceof Employee);

        final User client = userRepository.findById(2L).orElse(null);
        Assert.assertNotNull(client);
        Assert.assertTrue(client instanceof Client);
    }

    @Test
    public void saveTest(){
        final User newUser = buildUser(UserType.CLIENT);
        newUser.setId(null);

        final User savedUser = userRepository.save(newUser);

        final User userDb = userRepository.findById(savedUser.getId()).orElse(null);

        Assert.assertNotNull(userDb);
        Assert.assertEquals(newUser.getFirstName(), userDb.getFirstName());
        Assert.assertEquals(newUser.getLastName(), userDb.getLastName());
        Assert.assertEquals(newUser.getPostCode(), userDb.getPostCode());
        Assert.assertEquals(newUser.getIdentityId(), userDb.getIdentityId());
        Assert.assertEquals(newUser.getUserType(), userDb.getUserType());
        Assert.assertEquals(newUser.getCity(), userDb.getCity());
        Assert.assertEquals(newUser.getAddress(), userDb.getAddress());

        final Set<User> users = new HashSet<>();
        userRepository.findAll().forEach(users::add);

        Assert.assertFalse(users.isEmpty());
        Assert.assertEquals(3, users.size());
    }

    @Test
    public void findAllTest(){
        final Set<User> users = new HashSet<>();
        userRepository.findAll().forEach(users::add);

        Assert.assertFalse(users.isEmpty());
        Assert.assertEquals(2, users.size());
    }

    @Test
    public void updateTest(){
        final User user = userRepository.findById(1L).orElse(null);
        Assert.assertNotNull(user);

        user.setIdentityId("7844");

        final User savedUser = userRepository.save(user);
        Assert.assertNotNull(savedUser);
        Assert.assertEquals(user.getIdentityId(), savedUser.getIdentityId());
    }
}
