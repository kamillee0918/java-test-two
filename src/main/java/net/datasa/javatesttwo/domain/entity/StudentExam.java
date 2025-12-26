package net.datasa.javatesttwo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "student_exam")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentExam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id")
    private Integer examId;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "answer1", nullable = false)
    private Integer answer1;

    @Column(name = "answer2", nullable = false)
    private Integer answer2;

    @Column(name = "answer3", nullable = false)
    private Integer answer3;

    @Column(name = "answer4", nullable = false, length = 50)
    private String answer4;

    @Column(name = "answer5", nullable = false, length = 50)
    private String answer5;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "exam_date", nullable = false)
    private LocalDateTime examDate;

    @PrePersist
    protected void onCreate() {
        if (examDate == null) {
            examDate = LocalDateTime.now();
        }
    }
}
