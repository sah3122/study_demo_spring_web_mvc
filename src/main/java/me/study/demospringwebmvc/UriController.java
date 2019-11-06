package me.study.demospringwebmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @SessionAttributes("event")
 *
 * ModelAttributes에 담은 객체가 sessionAttributes 에 정의한 값과 일치하면 자동으로 세션에 넣어준다.
 * 모델 정보를 HTTP 세션에 저장해주는 애노테이션
 *
 * HttpSession을 직접 사용할 수도 있지만 이 애노테이션에 설정한 이름에 해당하는 모델 정보를 자동으로 세션에 넣어준다.
 * @ModelAttribute는 세션에 있는 데이터도 바인딩한다.
 * 열화면에서 사용해야 하는 객체를 공유할 때 사용.
 *
 * @SessionAttribute
 * HTTP 세션에 들어있는 값 참조에 사용
 * HttpSession을 사용할 때 비해 타입 컨버전을 자동으로 지원하기 때문에 조금 편리함.
 * HTTP 세션에 데이터를 넣고 빼고 싶은 경우에는 HttpSession을 사용할 것
 * @SessionAttributes와는 다르다.
 * @SessionAttributes는 해당 컨트롤러 내에서만 동작
 * 즉 해당 컨트롤러 면에서 다루는 특정 모델 객체를 세션에 넣고 공유할 때 상용
 * @SessionAttribute 는 컨트롤러 밖(인터셉터 또는 필터 등)에서 만들어준 세션 데이터에 접근 할 때 사용
*
 */
@Controller
@SessionAttributes("event")
public class UriController {

    /**
     * @ModelAttribute 사용법
     *
     * @RequestMapping 을 사용한 핸들러 메소드의 아규먼트에 사용하기
     * @Controller 또는 @ControllerAdvice를 사용한 클래스에서 모델 정보를 초기화 할 때 사용한다,
     * @RequestMapping과 같이 사용하면 해당 메소드에서 리턴하는 객체를 모델에 넣어준다.
     *    RequestToViewNameTranslator
     */
    @ModelAttribute
    public void categories(Model model) {
        model.addAttribute("categories", List.of("study", "seminar", "hobby", "social"));
    }

//    @ModelAttribute("categories")
//    public List<String> categories(Model model) {
//        return List.of("study", "seminar", "hobby", "social");
//    }

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
     * form 및 queryparam 을 받을수 있다.
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
     *
     * SessionStatus를 사용해서 세션 처리 완료를 알려줄 수 있다.
     * SessionAttributes 과 같이 사용할 때 폼 처리가 끝나고 세션을 비울때 사용한다.
     * */
    @PostMapping("/events")
    public String createParam(@Validated @ModelAttribute Event event,
                              BindingResult bindingResult,
                              Model model,
                              SessionStatus sessionStatus) {
        if(bindingResult.hasErrors()) {
            return "/events/form";
        }

        List<Event> eventList = new ArrayList<>();
        eventList.add(event);
        sessionStatus.isComplete();
        //model.addAttribute("eventList", eventList);
        model.addAttribute(eventList); // 기본적으로 동일한 이름으로 매핑된다.
        return "redirect:/events/list";
    }

    /**
     * @SessionAttribute
     *  interceptor에서 저장한 session값을 받아올수 있다.
     *  타입 컨버전 지원
     */
    @GetMapping("/events/list")
    public String getEvents(@RequestParam String name,
                            @RequestParam Integer limit,
                            @ModelAttribute("newEvent") Event event, // session attribute와 동일한 네임을 사용하면 안됨다.
                            Model model,
                            @SessionAttribute LocalDateTime visitTime) {
        System.out.println(visitTime);
        Event newEvent = new Event();
        newEvent.setNaem(name);
        newEvent.setLimit(limit);

        Event spring = new Event();
        event.setLimit(10);
        event.setNaem("dong");

        Event newEvent1 = (Event) model.asMap().get("newEvent"); // Flash Attributes로 넘겨준 값. Model에 바로 바인딩 된다.

        List<Event> eventList = new ArrayList<>();
        eventList.add(spring);
        eventList.add(newEvent);
        eventList.add(newEvent1);
        eventList.add(event);

        model.addAttribute(eventList);

        return "/events/list";
    }

    @GetMapping("/events/form")
    public String eventsForm(Model model, HttpSession httpSession) {
        Event newEvent = new Event();
        newEvent.setLimit(50);
        model.addAttribute("event", newEvent);
        //httpSession.setAttribute("event", newEvent);
        return "events/form";
    }

    /**
     * 멀티 폼 서브밋
     * Session을 사용한 방법.
     * 마지막에 Session Status에서 비워 줄것.
     *
     */
    @GetMapping("/events/form/name")
    public String eventsFormName(Model model, HttpSession httpSession) {
        model.addAttribute("event", new Event());
        return "events/form/form-name";
    }

    @GetMapping("/events/form/limit")
    public String eventsFormLimie(@ModelAttribute Event event, Model model, HttpSession httpSession) {
        model.addAttribute("event", event);
        return "events/form/form-limit";
    }

    @PostMapping("/events/form/name")
    @ModelAttribute
    public String createParamName(@Validated @ModelAttribute Event event,
                              BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "/events/form-name";
        }

        return "redirect:/events/form/limit";
    }

    @PostMapping("/events/form/name")
    @ModelAttribute // 모델에 담아준다.
    public Event returnModelAttributes() {
        return new Event();
    }

    /**
     * RedirectAttributes
     * 리다이렉트 시 기본적으로 Model에 들어있는 primitive type 데이터는 URI 쿼리 매개변수에 추가된다.
     * 스프링 부트에선 해당 기능이 기본적으로 비활성화
     * ignore-default-model-on-redirect 프로퍼티를 사용해서 활성화 가능
     *
     * 원하는 값만 리다이렉트 할 때 전달하고 싶다면 RedirctAttributes에 명시적으로 추가 가능하다.
     *
     * 리다이렉트 요청을 처리하는 곳에서 쿼리 매개변수를 @RequestParam 또는 @ModelAttribute로 받을 수 있다.
     *
     * spring boot 를 사용하고 있어 redirect시 model에 담아둔 primitive 값을 자동으로 넘겨주지 않게 설정되어 있다.
     * 일부 데이터만 명시적으로 보내고 싶을때 사용.
     *
     * Flash Attributes
     * 주로 리 다이렉트시에 데이터를 전달하기 위해 사용
     * 데이터가 URI에 노출 되지 않음
     * 임의의 객체를 저장할 수 있다. (Object)
     * 보통 HTTP 세션을 사용한다.
     * 리다리렉트 하기 전에 데이터를 세션에 저장하고 리다이렉트 요청을 처리 한 다음 그 즉시 제거됨.
     */
    @PostMapping("/events/form/limit")
    public String createParamLimit(@Validated @ModelAttribute Event event,
                                   BindingResult bindingResult,
                                   SessionStatus sessionStatus,
                                   RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return "/events/form-limit";
        }
        sessionStatus.setComplete();
        // spring boot 를 사용하고 있어 redirect시 model에 담아둔 값을 자동으로 넘겨주지 않게 설정되어 있다.
        redirectAttributes.addAttribute("name", event.getNaem());
        redirectAttributes.addAttribute("limit", event.getLimit());
        redirectAttributes.addFlashAttribute("newEvent", event);
        return "redirect:/events/list";
    }

}
