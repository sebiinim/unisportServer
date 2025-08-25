package com.example.unisportserver.data.mapper;

import com.example.unisportserver.data.dto.LessonDto;
import com.example.unisportserver.data.dto.ReservationRequestDto;
import com.example.unisportserver.data.dto.ReservationResponseDto;
import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.entity.ReservationEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationEntity toEntity(ReservationRequestDto reservationRequestDto);

    ReservationResponseDto toDto(ReservationEntity reservationEntity);

    List<ReservationResponseDto> toDtoList(List<ReservationEntity> reservationEntityList);

    List<ReservationEntity> toEntityList(List<ReservationResponseDto> reservationResponseDtoList);
}
