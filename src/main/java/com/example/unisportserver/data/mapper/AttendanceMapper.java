package com.example.unisportserver.data.mapper;

import com.example.unisportserver.data.dto.AttendanceRequestDto;
import com.example.unisportserver.data.dto.AttendanceResponseDto;
import com.example.unisportserver.data.entity.AttendanceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    AttendanceEntity toEntity(AttendanceRequestDto attendanceRequestDto);

    AttendanceResponseDto toResponseDto(AttendanceEntity attendanceEntity);

}
