package com.example.unisportserver.data.mapper;

import com.example.unisportserver.data.dto.ReservationRequestDto;
import com.example.unisportserver.data.dto.ReservationResponseDto;
import com.example.unisportserver.data.entity.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationEntity toEntity(ReservationRequestDto reservationRequestDto);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "lessonScheduleId", source = "lessonSchedule.id")
    ReservationResponseDto toDto(ReservationEntity reservationEntity);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "lessonScheduleId", source = "lessonSchedule.id")
    List<ReservationResponseDto> toDtoList(List<ReservationEntity> reservationEntityList);

    List<ReservationEntity> toEntityList(List<ReservationResponseDto> reservationResponseDtoList);
}
