package com.example.unisportserver.data.mapper;

import com.example.unisportserver.data.dto.UserDto;
import com.example.unisportserver.data.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(UserEntity entity);

    UserEntity toEntity(UserDto dto);

    List<UserDto> toDtoList(List<UserEntity> entities);
}
