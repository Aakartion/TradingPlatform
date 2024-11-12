package edu.miu.TradingPlatform.mapper;

import edu.miu.TradingPlatform.domain.Users;
import edu.miu.TradingPlatform.dto.request.UserRequestDTO;
import edu.miu.TradingPlatform.dto.response.UserResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    Users usersRequestDtoToUsers(UserRequestDTO userRequestDTO);

    UserRequestDTO usersToUsersRequestDto(Users users);

    UserResponseDTO usersToUserResponseDto(Users users);
}
