package com.derocode.EcommApp.security.internals;



import com.derocode.EcommApp.security.CreateUserDto;
import com.derocode.EcommApp.security.UserResponseDto;
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
