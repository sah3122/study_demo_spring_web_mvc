package me.study.demospringwebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
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
     *
     * @ModelAttribute : 복합타입의 데이터를 전달 받기 위해 사용.
     * 옆에 BindingResult 선언시 바인딩과 관련된 에러가 담겨온다.
     *
     * @Valid : validation 기능
     *
     * validation에러도 binding result 안에 담긴다.
     *
     * @Valid @ModelAttribute Event event, BindingResult bindingResult 조합이 modelattribute를 사용할때 가장 많이 사용됨.
     *
     * @Validated(Event.ValidateLimit.class) 그룹화 가능
     * 해당 그룹만 validation 할 수 있다.
     * */
    @PostMapping("/events")
    public String createParam(@Validated @ModelAttribute Event event, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "/events/form";
        }

        List<Event> eventList = new ArrayList<>();
        eventList.add(event);
        //model.addAttribute("eventList", eventList);
        model.addAttribute(eventList); // 기본적으로 동일한 이름으로 매핑된다.
        return "redirect:/events/list";
    }

    @GetMapping("/events/list")
    public String getEvents(Model model) {
        Event event = new Event();
        event.setLimit(10);
        event.setNaem("dong");

        List<Event> eventList = new ArrayList<>();
        eventList.add(event);
        model.addAttribute(eventList);

        return "/events/list";
    }

    @GetMapping("/events/form")
    public String eventsForm(Model model) {
        Event newEvent = new Event();
        newEvent.setLimit(50);
        model.addAttribute("event", newEvent);
        return "events/form";
    }

}
