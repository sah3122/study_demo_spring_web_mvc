package me.study.demospringwebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
public class UriController {

    /**
     * URI Pattern
     * spring 에서 PathVariable에 정의한 값을 자동으로 캐스팅하여 매칭 해준다.
     * Optional 지원 가능 , Required False 와 같음.
     *
     * MatrixVariable
     *
     * /events/1;name=dong 와 같이 key - value 쌍으로 데이터를 전달하는것을 지원
     * 기본적으로 설정되어 있지 않아 세미콜론을 삭제 하지 않도록 설정 해야함.
     *
     */
    @GetMapping("/events/{id}")
    @ResponseBody
    public Event getEvent(@PathVariable Integer id, @MatrixVariable String name) {
        Event event = new Event();
        event.setId(id);
        event.setNaem(name);
        return event;
    }

    /**
     * RequestParam 의 기본값은 True 이다.
     * @RequestParam Map<String, String> params // map으로 받을 수 있다.
     */
    @PostMapping("/events")
    @ResponseBody
    public Event getRequestParam(
            @RequestParam(required = false, defaultValue = "dong") String name,
            @RequestParam Integer limit

    ) {
        Event event = new Event();
        event.setNaem(name);
        return event;
    }

}
