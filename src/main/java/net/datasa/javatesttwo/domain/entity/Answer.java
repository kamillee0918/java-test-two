package net.datasa.javatesttwo.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "answer")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {

    @Id
    @Column(name = "question_num")
    private Integer questionNum;

    @Column(name = "correct_answer", nullable = false)
    private Integer correctAnswer;
}
