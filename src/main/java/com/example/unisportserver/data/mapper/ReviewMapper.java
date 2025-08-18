package com.example.unisportserver.data.mapper;

import com.example.unisportserver.data.dto.ReviewCreateRequestDto;
import com.example.unisportserver.data.dto.ReviewResponseDto;
import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.entity.ReviewEntity;
import com.example.unisportserver.data.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    ReviewCreateRequestDto toCreateRequestDtoList(ReviewEntity reviewEntity);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "lessonId", source = "lesson.id"),
            @Mapping(target = "userId", source = "user.id")
    })
    ReviewResponseDto toResponseDto(ReviewEntity reviewEntity);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "lesson", source = "lessonEntity"),
            @Mapping(target = "user", source = "userEntity"),
            @Mapping(target = "rating", source = "dto.rating")
    })
    ReviewEntity toEntity(ReviewCreateRequestDto dto, LessonEntity lessonEntity, UserEntity userEntity);

    List<ReviewCreateRequestDto> toCreateRequestDtoList(List<ReviewEntity> entities);
}
