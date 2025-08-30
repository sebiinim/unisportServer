package com.example.unisportserver.data.mapper;

import com.example.unisportserver.data.dto.LessonRequestDto;
import com.example.unisportserver.data.dto.LessonResponseDto;
import com.example.unisportserver.data.entity.LessonEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    LessonEntity toEntity(LessonRequestDto lessonRequestDto);

    LessonResponseDto toDto(LessonEntity lessonEntity);

    List<LessonResponseDto> toDtoList(List<LessonEntity> lessonEntities);
}
