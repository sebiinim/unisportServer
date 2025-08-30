package com.example.unisportserver.service;

import com.example.unisportserver.data.dto.ReviewCreateRequestDto;
import com.example.unisportserver.data.dto.ReviewResponseDto;
import com.example.unisportserver.data.entity.LessonEntity;
import com.example.unisportserver.data.entity.ReviewEntity;
import com.example.unisportserver.data.entity.UserEntity;
import com.example.unisportserver.data.mapper.ReviewMapper;
import com.example.unisportserver.data.repository.LessonRepository;
import com.example.unisportserver.data.repository.ReviewRepository;
import com.example.unisportserver.data.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    // 리뷰 생성
    @Transactional
    public ReviewResponseDto saveReview(ReviewCreateRequestDto reviewCreateRequestDto) {

        // 레슨이 있는지 확인
        LessonEntity lessonEntity = lessonRepository.findById(reviewCreateRequestDto.getLessonId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("Lesson with id %s not found", reviewCreateRequestDto.getLessonId())
                ));

        // 유저가 있는지 확인
        UserEntity userEntity = userRepository.findById((reviewCreateRequestDto.getUserId()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, String.format("User with id %s not found", reviewCreateRequestDto.getUserId())
                ));

        // ReviewEntity 만들고 DB에 save
        ReviewEntity reviewEntity = reviewMapper.toEntity(reviewCreateRequestDto, lessonEntity, userEntity);

        reviewEntity = reviewRepository.save(reviewEntity);


        // ReviewResponseDto로 다시 변환해서 리턴
        return reviewMapper.toResponseDto(reviewEntity);
    }

    // 레슨별 리뷰 검색(최신순)
    public List<ReviewResponseDto> getReviewsByLessonIdOrderByCreatedAtDesc(Long lessonId) {
        List<ReviewEntity> reviewEntities = reviewRepository.findAllByLessonIdOrderByCreatedAtDesc(lessonId);

        return reviewMapper.toResponseDtoList(reviewEntities);
    }

    // 레슨별 리뷰 검색(추천순)
    public List<ReviewResponseDto> getReviewsByLessonIdOrderByRatingDesc(Long lessonId) {
        List<ReviewEntity> reviewEntities = reviewRepository.findAllByLessonIdOrderByRatingDesc(lessonId);

        return reviewMapper.toResponseDtoList(reviewEntities);
    }
}
