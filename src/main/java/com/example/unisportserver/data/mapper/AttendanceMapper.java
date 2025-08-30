package com.example.unisportserver.data.mapper;

import com.example.unisportserver.data.dto.AttendanceRequestDto;
import com.example.unisportserver.data.dto.AttendanceResponseDto;
import com.example.unisportserver.data.entity.AttendanceEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    AttendanceEntity toEntity(AttendanceRequestDto attendanceRequestDto);

    AttendanceResponseDto toResponseDto(AttendanceEntity attendanceEntity);

    List<AttendanceResponseDto> toResponseDtoList(List<AttendanceEntity> attendanceEntityList);

}
