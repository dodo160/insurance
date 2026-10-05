package com.insurance.user.service;

import com.insurance.common.exception.NotFoundException;
import com.insurance.user.enums.UserType;
import com.insurance.user.model.User;
import com.insurance.user.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.insurance.TestUtils.buildUser;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void findAllTest() {
        when(userRepository.findAll()).thenReturn(Set.of(new User()));

        final List<User> result = userService.findAll();

        Assert.assertEquals(1, result.size());
    }

    @Test
    public void findByIdTest() {
        when(userRepository.findById(1l)).thenReturn(Optional.of(buildUser(UserType.CLIENT)));

        final User result = userService.findById(1l);

        Assert.assertNotNull(result);
        Assert.assertEquals(Long.valueOf(1), result.getId());
    }

    @Test
    public void deleteByIdTest() {
        userService.deleteById(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    public void softDeleteByIdTest() {
        final User user = buildUser(UserType.CLIENT);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.softDeleteById(1L);

        Assert.assertNotNull(user.getDeletedDate());
        verify(userRepository, times(1)).save(user);
    }

    @Test(expected=NotFoundException.class)
    public void softDeleteByIdNullInsuranceTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        userService.softDeleteById(1L);
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

        final ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        try {
            userService.update(user.getId(), user);
        } catch (Exception e) {
            Assert.fail();
        }

        verify(userRepository, times(1)).save(userArgumentCaptor.capture());
        final User userArgumentCaptorValue = userArgumentCaptor.getValue();
        Assert.assertEquals(user.getFirstName(), userArgumentCaptorValue.getFirstName());
        Assert.assertEquals(user.getLastName(), userArgumentCaptorValue.getLastName());
        Assert.assertEquals(user.getAddress(), userArgumentCaptorValue.getAddress());
        Assert.assertEquals(user.getCity(), userArgumentCaptorValue.getCity());
        Assert.assertEquals(user.getPostCode(), userArgumentCaptorValue.getPostCode());
    }

    @Test
    public void updateTariffDoesntExistTest() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        final User user = buildUser(UserType.CLIENT);

        try {
            userService.update(user.getId(), user);
            Assert.fail();
        } catch (NotFoundException nfe) {
            Assert.assertEquals("User not found", nfe.getMessage());
        }
    }
}
