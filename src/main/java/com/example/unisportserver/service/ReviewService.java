package com.example.unisportserver.service;

import com.example.unisportserver.data.dto.ReviewCreateRequestDto;
import com.example.unisportserver.data.dto.ReviewResponseDto;
import com.example.unisportserver.data.dto.UserDto;
import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.entity.ReviewEntity;
import com.example.unisportserver.data.entity.UserEntity;
import com.example.unisportserver.data.mapper.ReviewMapper;
import com.example.unisportserver.data.repository.LessonRepository;
import com.example.unisportserver.data.repository.ReviewRepository;
import com.example.unisportserver.data.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    public final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, ReviewMapper reviewMapper, LessonRepository lessonRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.lessonRepository = lessonRepository;
        this.userRepository = userRepository;
    }

    // 레슨 생성
    @Transactional
    public ReviewResponseDto saveReview(ReviewCreateRequestDto reviewCreateRequestDto) {

        // 레슨이 있는지 확인
        LessonEntity lessonEntity = lessonRepository.findById(reviewCreateRequestDto.getLessonId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,  String.format("Lesson with id %s not found", reviewCreateRequestDto.getLessonId())
                        ));

        // 유저가 있는지 확인
        UserEntity userEntity = userRepository.findById((reviewCreateRequestDto.getUserId()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,  String.format("User with id %s not found", reviewCreateRequestDto.getUserId())
                        ));

        // ReviewEntity 만들고 DB에 save
        ReviewEntity reviewEntity = reviewMapper.toEntity(reviewCreateRequestDto, lessonEntity, userEntity);

        reviewEntity = reviewRepository.save(reviewEntity);


        // ReviewResponseDto로 다시 변환해서 리턴
        return reviewMapper.toResponseDto(reviewEntity);
    }

    // 레슨별 리뷰 검색
    public Page<ReviewResponseDto> getReviewsByLessonId(Long lessonId, Pageable pageable) {
        return reviewRepository.findAllByLessonId(lessonId, pageable).map(reviewMapper::toResponseDto);
    }

    //
}
