package com.example.unisportserver.data.mapper;

import com.example.unisportserver.data.dto.LessonDto;
import com.example.unisportserver.data.entity.LessonEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    LessonEntity toEntity(LessonDto lessonDto);

    LessonDto toDto(LessonEntity entity);

    List<LessonDto> toDtoList(List<LessonEntity> lessonEntities);
}
