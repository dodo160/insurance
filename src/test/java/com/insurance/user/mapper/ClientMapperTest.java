package com.insurance.user.mapper;

import com.insurance.user.dto.UserDTO;
import com.insurance.user.enums.UserType;
import com.insurance.user.model.Client;
import com.insurance.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientMapperTest {

    private final ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);

    @Test
    public void toDtoTest(){
        final Client user = (Client) TestUtils.buildUser(UserType.CLIENT);

        final UserDTO userDTO = clientMapper.toDto(user);

        assertNotNull(userDTO);
        assertEquals(user.getId(), userDTO.getId());
        assertEquals(user.getFirstName(), userDTO.getFirstName());
        assertEquals(user.getLastName(), userDTO.getLastName());
        assertEquals(user.getAddress(), userDTO.getAddress());
        assertEquals(user.getCity(), userDTO.getCity());
        assertEquals(user.getPostCode(), userDTO.getPostCode());
        assertEquals(user.getUserType(), userDTO.getUserType());
        assertEquals(user.getIdentityId(), userDTO.getIdentityId());
    }

    @Test
    public void fromDtoTest(){
        final UserDTO userDTO = TestUtils.buildUserDTO(UserType.CLIENT);

        final Client user = clientMapper.fromDto(userDTO);

        assertNotNull(user);
        assertEquals(userDTO.getId(), user.getId());
        assertEquals(userDTO.getFirstName(), user.getFirstName());
        assertEquals(userDTO.getLastName(), user.getLastName());
        assertEquals(userDTO.getAddress(), user.getAddress());
        assertEquals(userDTO.getCity(), user.getCity());
        assertEquals(userDTO.getPostCode(), user.getPostCode());
        assertEquals(userDTO.getIdentityId(), user.getIdentityId());
        assertEquals(userDTO.getUserType(), user.getUserType());
    }
}
