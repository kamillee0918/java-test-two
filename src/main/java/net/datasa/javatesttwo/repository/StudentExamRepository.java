package net.datasa.javatesttwo.repository;

import net.datasa.javatesttwo.domain.entity.StudentExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentExamRepository extends JpaRepository<StudentExam, Integer> {

    // 이메일 중복 체크
    boolean existsByEmail(String email);

    // 응시일순 정렬 (오름차순)
    List<StudentExam> findAllByOrderByExamDateAsc();

    // 이름순 정렬 (오름차순)
    List<StudentExam> findAllByOrderByNameAsc();

    // 성적순 정렬 (내림차순)
    List<StudentExam> findAllByOrderByScoreDesc();
}
