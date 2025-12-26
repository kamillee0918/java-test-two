package net.datasa.javatesttwo.repository;

import net.datasa.javatesttwo.domain.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
