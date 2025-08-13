package com.example.unisportserver.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    @GetMapping(value = "/all")
    public String getAllLessons() {
        return """
                {
                  "success": true,
                  "data": {
                    "lessons": [
                      {
                        "id": "lesson-123",
                        "title": "테니스 기초 레슨",
                        "description": "테니스 기초를 배워보세요",
                        "sport": "tennis",
                        "instructor": {
                          "id": "user-123",
                          "name": "김철수",
                          "rating": 4.5,
                          "reviewCount": 10
                        },
                        "price": 30000,
                        "duration": 60,
                        "maxStudents": 4,
                        "location": "서울대학교 테니스장",
                        "schedule": [
                          {
                            "id": "schedule-123",
                            "date": "2024-01-15",
                            "startTime": "14:00",
                            "endTime": "15:00",
                            "availableSpots": 3
                          }
                        ],
                        "createdAt": "2024-01-01T00:00:00Z",
                        "updatedAt": "2024-01-01T00:00:00Z"
                      }
                    ],
                    "pagination": {
                      "page": 1,
                      "limit": 10,
                      "total": 50,
                      "totalPages": 5
                    }
                  }
                }""";
    }

    @GetMapping(value = "/search/{lessonID}")
    public String getLessonbyID(@PathVariable("lessonID") String lessonID) {
        return lessonID + "번째 레슨";
    }
}
