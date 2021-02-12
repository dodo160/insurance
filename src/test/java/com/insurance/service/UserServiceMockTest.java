package com.insurance.service;

import com.insurance.enums.UserType;
import com.insurance.model.User;
import com.insurance.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.bind.ValidationException;
import java.util.Optional;
import java.util.Set;

import static com.insurance.TestUtils.buildUser;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UserServiceMockTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAllTest(){
        when(userRepository.findAll()).thenReturn(Set.of(new User()));

        final Set<User> result = userService.findAll();

        Assert.assertTrue(result.size() == 1);
    }

    @Test
    public void findByIdTest(){
        when(userRepository.findById(1l)).thenReturn(Optional.of(buildUser(UserType.CLIENT)));

        final User result = userService.findById(1l);

        Assert.assertNotNull(result);
        Assert.assertEquals(Long.valueOf(1), result.getId());
    }

    @Test
    public void deleteByIdTest(){
        userService.deleteById(1L);
        verify(userRepository,times(1)).deleteById(1L);
    }

    @Test
    public void softDeleteByIdTest(){
        final User user = buildUser(UserType.CLIENT);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.softDeleteById(1L);

        Assert.assertNotNull(user.getDeletedDate());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void softDeleteByIdNullInsuranceTest(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        userService.softDeleteById(1L);

        verify(userRepository, times(0)).save(any());
    }

    @Test
    public void addTest(){
        final User user = buildUser(UserType.CLIENT);

        userService.add(user);

        verify(userRepository,times(1)).save(user);
    }

    @Test
    public void updateTest(){
        final User user = buildUser(UserType.CLIENT);
        user.setFirstName("FIRST_NAME_UPDATED");
        user.setLastName("LAST_NAME_UPDATED");
        user.setAddress("ADDRESS_UPDATED");
        user.setCity("CITY_UPDATED");
        user.setPostCode("5555");

        when(userRepository.findById(1L)).thenReturn(Optional.of(buildUser(UserType.CLIENT)));

        final ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        try {
            userService.update(user);
        } catch (ValidationException e) {
            Assert.fail();
        }

        verify(userRepository,times(1)).save(userArgumentCaptor.capture());
        final User userArgumentCaptorValue = userArgumentCaptor.getValue();
        Assert.assertEquals(user.getFirstName(), userArgumentCaptorValue.getFirstName());
        Assert.assertEquals(user.getLastName(), userArgumentCaptorValue.getLastName());
        Assert.assertEquals(user.getAddress(), userArgumentCaptorValue.getAddress());
        Assert.assertEquals(user.getCity(), userArgumentCaptorValue.getCity());
        Assert.assertEquals(user.getPostCode(), userArgumentCaptorValue.getPostCode());
    }

    @Test
    public void updateTariffDoesntExistTest(){
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            userService.update(buildUser(UserType.CLIENT));
            Assert.fail();
        } catch (ValidationException e) {
            Assert.assertTrue("User entity doesn't exist".equals(e.getMessage()));
        }
    }
}
