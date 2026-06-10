package com.derocode.EcommApp.security.mappers;



import com.derocode.EcommApp.security.api.CreateUserDto;
import com.derocode.EcommApp.security.api.UserResponseDto;
import com.derocode.EcommApp.security.models.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    User createUserDtoToUser (CreateUserDto userDto);

    @Mapping(target = "role", expression = "java(user.getRole().getName().name())")
    @Mapping(target = "userName", source = "email")
    UserResponseDto userToResponseDto (User user);















}
