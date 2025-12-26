package net.datasa.javatesttwo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.javatesttwo.domain.dto.ExamSubmitDTO;
import net.datasa.javatesttwo.domain.dto.StudentExamDTO;
import net.datasa.javatesttwo.exception.ValidationException;
import net.datasa.javatesttwo.service.ExamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    /**
     * 시험 응시 화면
     */
    @GetMapping("/exam")
    public String examForm(
            @ModelAttribute("examSubmit") ExamSubmitDTO examSubmit) {
        return "view/exam";
    }

    /**
     * 시험 제출 처리
     */
    @PostMapping("/exam/submit")
    public String submitExam(
            @ModelAttribute ExamSubmitDTO examSubmit,
            RedirectAttributes redirectAttributes,
            Model model) {
        log.debug("[POST - ExamController.submitExam] 호출 완료.");

        try {
            // 이메일 중복 체크
            if (examService.isEmailDuplicate(examSubmit.getEmail())) {
                log.warn("[POST - ExamController.submitExam] 이메일 중복: {}", examSubmit);
                redirectAttributes.addFlashAttribute("error", "이미 응시한 이메일입니다: " + examSubmit.getEmail());
                redirectAttributes.addFlashAttribute("examSubmit", examSubmit);
                return "redirect:/exam"; // Exam 라우팅("/")으로 리디렉트
            }
            // 저장
            examService.saveExam(examSubmit);
            log.debug("[POST - ExamController.submitExam] 시험 제출 성공: {}", examSubmit);
            return "redirect:/result";
        } catch (ValidationException e) {
            log.warn("[POST - ExamController.submitExam] 시험 제출 실패: {}", examSubmit);
            model.addAttribute("error", e.getMessage());
            return "view/exam"; // 시험 응시 폼으로 복귀
        }
    }

    /**
     * 시험 결과 목록
     */
    @GetMapping("/result")
    public String resultList(@RequestParam(value = "sort", defaultValue = "date") String sort,
                             Model model) {
        List<StudentExamDTO> list = examService.getExamList(sort);
        model.addAttribute("examList", list);
        model.addAttribute("currentSort", sort);
        return "view/result";
    }

    /**
     * 삭제 화면
     */
    @GetMapping("/result/delete/{examId}")
    public String deleteForm(@PathVariable Integer examId, Model model) {
        StudentExamDTO dto = examService.getExamById(examId);
        model.addAttribute("exam", dto);
        return "view/delete";
    }

    /**
     * 삭제 처리
     */
    @PostMapping("/result/delete")
    public String deleteExam(@RequestParam Integer examId,
                             @RequestParam String password,
                             RedirectAttributes redirectAttributes) {
        log.debug("삭제 요청: examId={}", examId);

        boolean deleted = examService.deleteExam(examId, password);

        if (!deleted) {
            redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "redirect:/result/delete/" + examId;
        }

        return "redirect:/result";
    }
}
