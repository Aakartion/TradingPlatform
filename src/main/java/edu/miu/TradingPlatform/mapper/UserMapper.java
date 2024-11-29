package edu.miu.TradingPlatform.mapper;

import edu.miu.TradingPlatform.domain.User;
import edu.miu.TradingPlatform.dto.users.request.UserRequestDTO;
import edu.miu.TradingPlatform.dto.users.response.UserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO userToUserResponseDTO(User user);

    UserRequestDTO userToUserRequestDTO(User user);
}