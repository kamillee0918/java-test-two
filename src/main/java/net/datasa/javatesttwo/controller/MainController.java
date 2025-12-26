package net.datasa.javatesttwo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    /**
     * 메인 페이지 라우팅 메서드
     * - 로그인 여부와 무관하게 index 템플릿을 반환합니다.
     *
     * @return index 템플릿 경료(view/index)
     */
    @GetMapping({"", "/"})
    public String index() {
        log.debug("[GET - MainController.index] 호출 완료.");
        return "view/index";
    }
}
