package net.datasa.javatesttwo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.javatesttwo.domain.dto.ExamSubmitDTO;
import net.datasa.javatesttwo.domain.dto.StudentExamDTO;
import net.datasa.javatesttwo.domain.entity.Answer;
import net.datasa.javatesttwo.domain.entity.StudentExam;
import net.datasa.javatesttwo.repository.AnswerRepository;
import net.datasa.javatesttwo.repository.StudentExamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ExamService {

    private final AnswerRepository answerRepository;
    private final StudentExamRepository studentExamRepository;

    /**
     * 이메일 중복 체크
     */
    public boolean isEmailDuplicate(String email) {
        return studentExamRepository.existsByEmail(email);
    }

    // 주관식 정답 (4번, 5번)
    private static final String ANSWER4_CORRECT = "extends";
    private static final String ANSWER5_CORRECT = "try";

    /**
     * 채점 로직 - 5문항 × 20점 = 100점 만점
     * 1~3번: 객관식 (DB 정답 테이블 비교)
     * 4~5번: 주관식 (문자열 비교, 대소문자 무시)
     */
    public int gradeExam(ExamSubmitDTO dto) {
        List<Answer> answers = answerRepository.findAll();
        int score = 0;

        // 1~3번 객관식 채점
        for (Answer answer : answers) {
            int questionNum = answer.getQuestionNum();
            if (questionNum <= 3) {
                int correctAnswer = answer.getCorrectAnswer();
                int studentAnswer = getStudentAnswerInt(dto, questionNum);
                if (correctAnswer == studentAnswer) {
                    score += 20;
                }
            }
        }

        // 4번 주관식 채점
        if (dto.getAnswer4() != null && dto.getAnswer4().trim().equalsIgnoreCase(ANSWER4_CORRECT)) {
            score += 20;
        }

        // 5번 주관식 채점
        if (dto.getAnswer5() != null && dto.getAnswer5().trim().equalsIgnoreCase(ANSWER5_CORRECT)) {
            score += 20;
        }

        log.debug("채점 결과: {}점", score);
        return score;
    }

    /**
     * 문제 번호에 해당하는 학생 답안 반환 (객관식 1~3번)
     */
    private int getStudentAnswerInt(ExamSubmitDTO dto, int questionNum) {
        return switch (questionNum) {
            case 1 -> dto.getAnswer1() != null ? dto.getAnswer1() : 0;
            case 2 -> dto.getAnswer2() != null ? dto.getAnswer2() : 0;
            case 3 -> dto.getAnswer3() != null ? dto.getAnswer3() : 0;
            default -> 0;
        };
    }

    /**
     * 시험 응시정보 저장
     */
    @Transactional
    public void saveExam(ExamSubmitDTO dto) {
        int score = gradeExam(dto);

        StudentExam entity = StudentExam.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .answer1(dto.getAnswer1())
                .answer2(dto.getAnswer2())
                .answer3(dto.getAnswer3())
                .answer4(dto.getAnswer4())
                .answer5(dto.getAnswer5())
                .score(score)
                .build();

        studentExamRepository.save(entity);
        log.debug("응시정보 저장 완료: {}", entity);
    }

    /**
     * 응시정보 목록 조회 (정렬 옵션별)
     */
    public List<StudentExamDTO> getExamList(String sortBy) {
        List<StudentExam> list;

        list = switch (sortBy) {
            case "name" -> studentExamRepository.findAllByOrderByNameAsc();
            case "score" -> studentExamRepository.findAllByOrderByScoreDesc();
            default -> studentExamRepository.findAllByOrderByExamDateAsc();
        };

        return list.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    /**
     * 응시정보 단건 조회
     */
    public StudentExamDTO getExamById(Integer examId) {
        StudentExam entity = studentExamRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("응시정보를 찾을 수 없습니다."));
        return entityToDto(entity);
    }

    /**
     * 응시정보 삭제 (비밀번호 검증)
     */
    @Transactional
    public boolean deleteExam(Integer examId, String password) {
        StudentExam entity = studentExamRepository.findById(examId)
                .orElseThrow(() -> new RuntimeException("응시정보를 찾을 수 없습니다."));

        if (entity.getPassword().equals(password)) {
            studentExamRepository.delete(entity);
            log.debug("응시정보 삭제 완료: examId={}", examId);
            return true;
        }

        log.debug("비밀번호 불일치: examId={}", examId);
        return false;
    }

    /**
     * Entity -> DTO 변환
     */
    private StudentExamDTO entityToDto(StudentExam entity) {
        return StudentExamDTO.builder()
                .examId(entity.getExamId())
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .answer1(entity.getAnswer1())
                .answer2(entity.getAnswer2())
                .answer3(entity.getAnswer3())
                .answer4(entity.getAnswer4())
                .answer5(entity.getAnswer5())
                .score(entity.getScore())
                .examDate(entity.getExamDate())
                .build();
    }
}
