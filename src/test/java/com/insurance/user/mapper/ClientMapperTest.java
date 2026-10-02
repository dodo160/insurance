package com.insurance.user.mapper;

import com.insurance.TestUtils;
import com.insurance.user.dto.UserDTO;
import com.insurance.user.enums.UserType;
import com.insurance.user.model.Client;
import org.junit.Assert;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

public class ClientMapperTest {

    private final ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);

    @Test
    public void toDtoTest(){
        final Client user = (Client) TestUtils.buildUser(UserType.CLIENT);

        final UserDTO userDTO = clientMapper.toDto(user);

        Assert.assertNotNull(userDTO);
        Assert.assertEquals(user.getId(), userDTO.getId());
        Assert.assertEquals(user.getFirstName(), userDTO.getFirstName());
        Assert.assertEquals(user.getLastName(), userDTO.getLastName());
        Assert.assertEquals(user.getAddress(), userDTO.getAddress());
        Assert.assertEquals(user.getCity(), userDTO.getCity());
        Assert.assertEquals(user.getPostCode(), userDTO.getPostCode());
        Assert.assertEquals(user.getUserType(), userDTO.getUserType());
        Assert.assertEquals(user.getIdentityId(), userDTO.getIdentityId());
    }

    @Test
    public void fromDtoTest(){
        final UserDTO userDTO = TestUtils.buildUserDTO(UserType.CLIENT);

        final Client user = clientMapper.fromDto(userDTO);

        Assert.assertNotNull(user);
        Assert.assertEquals(userDTO.getId(), user.getId());
        Assert.assertEquals(userDTO.getFirstName(), user.getFirstName());
        Assert.assertEquals(userDTO.getLastName(), user.getLastName());
        Assert.assertEquals(userDTO.getAddress(), user.getAddress());
        Assert.assertEquals(userDTO.getCity(), user.getCity());
        Assert.assertEquals(userDTO.getPostCode(), user.getPostCode());
        Assert.assertEquals(userDTO.getIdentityId(), user.getIdentityId());
        Assert.assertEquals(userDTO.getUserType(), user.getUserType());
    }
}
