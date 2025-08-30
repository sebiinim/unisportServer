package com.example.unisportserver.service;

import com.example.unisportserver.data.dto.AttendanceRequestDto;
import com.example.unisportserver.data.dto.AttendanceResponseDto;
import com.example.unisportserver.data.entity.AttendanceEntity;
import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.mapper.AttendanceMapper;
import com.example.unisportserver.data.repository.AttendanceRepository;
import com.example.unisportserver.data.repository.LessonRepository;
import com.example.unisportserver.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    private final AttendanceMapper attendanceMapper;


    // 레슨에 출석 표시(출석 엔티티는 이미 생성되어 있음)
    public AttendanceResponseDto markAttendanceTrue(AttendanceRequestDto attendanceRequestDto) {

        Long lessonId = attendanceRequestDto.getLessonId();
        Long instructorUserId = attendanceRequestDto.getInstructorUserId();
        Long userId = attendanceRequestDto.getUserId();
        Boolean isAttended = attendanceRequestDto.getIsAttended();

        // 레슨 엔티티 찾기
        LessonEntity lessonEntity = lessonRepository.findById(lessonId).orElseThrow(
                () -> new RuntimeException("lesson with id: " + lessonId + " not found")
        );

        // 출석을 표시하려는 유저가 이 레슨의 강사가 아니면 오류 발생
        if (!lessonEntity.getInstructorUserId().equals(instructorUserId)) {
            throw new RuntimeException("User with id: " + userId + "is not the instructor of the Lesson with id: " + lessonId);
        }

        // 출석 엔티티 찾기
        AttendanceEntity attendanceEntity = attendanceRepository.findByLessonIdAndUserId(lessonId, userId);

        attendanceEntity.setIsAttended(true);

        attendanceRepository.save(attendanceEntity);

        return attendanceMapper.toResponseDto(attendanceEntity);
    }

    // 레슨에 결석 표시(출석 엔티티는 이미 생성되어 있음)
    public AttendanceResponseDto markAttendanceFalse(AttendanceRequestDto attendanceRequestDto) {

        Long lessonId = attendanceRequestDto.getLessonId();
        Long instructorUserId = attendanceRequestDto.getInstructorUserId();
        Long userId = attendanceRequestDto.getUserId();
        Boolean isAttended = attendanceRequestDto.getIsAttended();

        // 레슨 엔티티 찾기
        LessonEntity lessonEntity = lessonRepository.findById(lessonId).orElseThrow(
                () -> new RuntimeException("lesson with id: " + lessonId + " not found")
        );

        // 출석을 표시하려는 유저가 이 레슨의 강사가 아니면 오류 발생
        if (!lessonEntity.getInstructorUserId().equals(instructorUserId)) {
            throw new RuntimeException("User with id: " + userId + "is not the instructor of the Lesson with id: " + lessonId);
        }

        // 출석 엔티티 찾기
        AttendanceEntity attendanceEntity = attendanceRepository.findByLessonIdAndUserId(lessonId, userId);

        attendanceEntity.setIsAttended(false);

        attendanceRepository.save(attendanceEntity);

        return attendanceMapper.toResponseDto(attendanceEntity);
    }


    // 레슨 출석 현황 확인
    public List<AttendanceResponseDto> getAttendanceByLessonId(Long lessonId) {

        List<AttendanceEntity> attendanceEntities = attendanceRepository.findAllByLessonId(lessonId);

        return attendanceMapper.toResponseDtoList(attendanceEntities);
    }


}
