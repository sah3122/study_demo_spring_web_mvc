package me.study.demospringwebmvc;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/events")
public class EventApi {

    /**
     * @RequestBody
     * 요청 본문(body)에 들어있는 HttpMessageConverter를 통해 변환한 객체로 받아 올 수 있다.
     * @Valid 또는 @Validated를 사용해서 값을 검증 할 수 있다.
     * BindingResult 아규먼트를 사용해 코드로 바인딩 또는 검증 에러를 확인할 수 있다.
     *
     * HttpMessageConverter
     * 스프링 MVC설정 (WebMvcConfigurer)에서 설정 할 수 있다.
     * configureMessageConverters : 기본 메시지 컨버터 대체
     * extendMessageConverters : 메시지 컨버터 추가
     * 기본 컨버터
     * WebMvcConfigurationSupoort.addDefaultHttpMessageConverters
     *
     * HttpEntity
     * @RequestBody와 비슷하지만 추가적으로 요청 헤더 정보를 사용 할 수 있다.
     *
     * ResponseEntity 는 응답 본문을 보내주기때문에 굳이 RestController 선언할 필요는 없다.
     */
    @PostMapping
    //Event 객체는 HTTPMessageConverter가 자동으로 컨버팅을 해준다.
    public ResponseEntity<Event> createEvent(//@RequestBody @Valid HttpEntity<Event> request,
                                             @RequestBody @Valid Event event,
                                             BindingResult bindingResult) {
        //MediaType contentType = request.getHeaders().getContentType();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error);
            });
            return ResponseEntity.badRequest().build();
        }


        //return ResponseEntity.ok(event);
        return new ResponseEntity<Event>(event, HttpStatus.CREATED);
    }
}
