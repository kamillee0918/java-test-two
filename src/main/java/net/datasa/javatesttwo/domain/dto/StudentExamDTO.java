package net.datasa.javatesttwo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentExamDTO {

    private Integer examId;
    private String name;
    private String email;
    private String password;
    private Integer answer1;
    private Integer answer2;
    private Integer answer3;
    private String answer4;
    private String answer5;
    private Integer score;
    private LocalDateTime examDate;
}
