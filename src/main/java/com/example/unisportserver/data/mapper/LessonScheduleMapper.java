package com.example.unisportserver.data.mapper;

import com.example.unisportserver.data.dto.LessonScheduleResponseDto;
import com.example.unisportserver.data.entity.LessonScheduleEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonScheduleMapper {

    LessonScheduleResponseDto toResponseDto(LessonScheduleEntity lessonScheduleEntity);

    List<LessonScheduleResponseDto> toResponseDtoList(List<LessonScheduleEntity> lessonScheduleEntities);
}
