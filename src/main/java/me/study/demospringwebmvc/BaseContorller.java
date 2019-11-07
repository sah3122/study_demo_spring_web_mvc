package me.study.demospringwebmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

/**
 * 전역 컨트롤러 @(Rest)ControllerAdvice
 * 예외 처리, 바인딩 설정, 모델 객체를 모든 컨트롤러 전반에 걸쳐 적용하고 싶은 경우에 사용한다.
 * @ExceptionHandler
 * @InitBinder
 * @ModelAttributes
 *
 * 적용할 범위를 지정할 수도 있다.
 * 특정 애노테이션을 가지고 있는 컨트롤러에만 적용하기
 * 특정 패키지 이하의 컨트롤러에만 적용하기
 * 특정 클래스 타입에만 적용하기
 */
@ControllerAdvice(assignableTypes = {UriController.class, EventApi.class})
public class BaseContorller {
    @ExceptionHandler({EventException.class, RuntimeException.class})
    public String eventErrorHandler(RuntimeException exception, Model model) {
        model.addAttribute("message", "event error");
        return "error";
    }

    @ExceptionHandler
    public String runtimeErrorHandler(RuntimeException exception, Model model) {
        model.addAttribute("message", "runtime error");
        return "error";
    }

    /**
     * DataBinder : @InitBinder
     * 특정 컨트롤러에서 바인딩 또는 검증 설정을 변경하고 싶을 때 사용
     *
     */
    @InitBinder
    //@InitBinder("event") 특정 모델 객체에만 바인딩 또는 Validator 설정을 하고 싶은 경우
    public void initEventBinder(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id"); // id값을 바인딩하지 않도록 설정
        //webDataBinder.addCustomFormatter(); 포메터 설정
        webDataBinder.addValidators(new EventValidator()); //Validator 설정
    }


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
}
