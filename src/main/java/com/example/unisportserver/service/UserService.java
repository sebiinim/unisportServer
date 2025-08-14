package com.example.unisportserver.service;

import com.example.unisportserver.domain.UserEntity;
import com.example.unisportserver.dto.UserDTO;
import com.example.unisportserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDTO createUser(UserDTO dto) {
        if (dto.getId() == null || dto.getId().isBlank()) {
            dto.setId(UUID.randomUUID().toString());
        }
        LocalDateTime now = LocalDateTime.now();
        dto.setCreatedAt(now);
        dto.setUpdatedAt(now);

        UserEntity entity = toEntity(dto);
        UserEntity saved = userRepository.save(entity);
        return toDTO(saved);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getUniversity(),
                        user.getMajor(),
                        user.getGrade(),
                        user.getBio(),
                        user.getRating(),
                        user.getReviewCount(),
                        user.getCreatedAt(),
                        user.getUpdatedAt()
                ))
                .collect(Collectors.toList());
    }

    // DTO → Entity
    private UserEntity toEntity(UserDTO dto) {
        return UserEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }

    // Entity → DTO
    private UserDTO toDTO(UserEntity entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
