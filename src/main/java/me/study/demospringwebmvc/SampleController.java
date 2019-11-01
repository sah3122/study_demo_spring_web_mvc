package me.study.demospringwebmvc;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by dongchul on 2019-10-31.
 */
@Controller
public class SampleController {
    /**
     * GET 요청
     * idemponent // 멱등
     * 클라이언트가 서버의 리소스 요청시 사용
     * 캐싱 가능
     * 브라우저 기록에 남음
     * 북마크 가능
     * 민감한 데이터를 보낼때 사용 X
     *
     * POST
     * 클라이언트가 서버의 리소스를 수정하거나 새로 만들때
     * 서버에 보내는 데이터를 post 요청 본문에 담는다
     * 캐싱 불가능
     * 브라우저 기록에 남지 않음
     * 북마크 할 수 없다,.
     *
     * PUT
     * URI에 해당하는 데이터를 새로 만들거나 수정 할 때 사용
     * POST와 다른점은 URI에 대한 의미가 다르다
     *  POST의 URI는 보내는 데이터를 처리할 리소스 지칭
     *  PUT의 URI는 보내는 데이터에 해당하는 리소스 지칭
     * idemponent
     *
     * PATCH
     * PUT 과 비슷 하지만 기존 엔티티와 새 데이터의 차이점만 보낸다는 차이가 있다.
     * iemponent
     *
     * DLEETE
     * URI에 해당하는 리소스 삭제시 사용
     * idemponent
     */
    //@RequestMapping(value = "/hello", method = {RequestMethod.GET, RequestMethod.DELETE}) // http method 정의하지 않을시 모든 method허용
    @GetMapping("/hello2") // 하나의 요청만 사용 할 경우우    @ResponseBody // 응답 본문으로 리턴, 작성하지 않을시 view를 찾아감
    public String hello() {
        return "hello";
    }

    // ? 한글자 매핑
    // * 여러글자
    // ** 여러 패스스    @RequestMapping("/**")
    // spring boot 는 확장자 매핑을 사용하지 못하도록 설정되어 있다.

    // 특정한 타입의 데이터를 담고 있는 요청만 처리할 수 있게 정의한다.
    // consumes -> content-type 헤더로 필터링
    // produces -> accept header 필터링, accpet 타입이 없을시 매칭이 된다.
   @GetMapping(
           value = "/hello3",
           consumes = MediaType.APPLICATION_JSON_VALUE, //클래스에 선언한 RequestMapping 에 사용한것과 조합되지 않고 메소드에 사용한 설정이 사용된다.
           produces = MediaType.TEXT_PLAIN_VALUE
   )
    @ResponseBody
    public String helloMediaType() {
        return "hello";
    }

    /**
     *  "!" + HttpHeaders.FROM 특정 헤더가 없는 요청을 처리하고 싶은 경우
     *  특정한 헤더 키/값이 있는 요청을 처리 하고 싶은 경우
     *  HttpHeaders.FROM + "=" + "value"
     *  특정한 요청 매개변수 키를 가지고 있는 요청을 처리 하고 싶은 경우
     *  params = "value"
     *  특정한 요청 매개변수가 없는 요청을 처리하고 싶은 경우
     *  params = "!value"
     *  특정한 요청 매개변수 요청을 처리하고 싶은 경우
     *  params = "key=value"
     * @return
     */
    @GetMapping(
            value = "/hello",
            headers = HttpHeaders.FROM
    )
    @ResponseBody
    public String helloHeader() {
        return "hello";
    }

    /**
     * 우리가 구현하지 않아도 스프링 웹 MVC에서 자동으로 처리하는 HTTP Method
     * HEAD
     * GET 요청과 동일하지만 응답 본문을 받아오지 않고 응답 헤더만 받아온다.
     *
     * OPTIONS
     * 사용할 수 있는 HTTP Method 제공
     * 서버 또는 특정 리소스가 제공하는 기능을 확인할 수 있다.
     * 서버는 Allow 응답 헤더에 사용할 수 있는 HTTP Method 목록을 제공해야 한다.
     */

    @GetMapping("/events")
    @ResponseBody
    public String events() {
        return "events";
    }

    @GetMapping("/events/{id}")
    @ResponseBody
    public String eventsWithId(@PathVariable int id) {
        return "events";
    }

    @DeleteMapping("/events/{id}")
    @ResponseBody
    public String deleteEventsWithId(@PathVariable int id) {
        return "events";
    }


    @PostMapping(value = "/events",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String createEvent(@PathVariable int id) {
        return "events";
    }

    @PutMapping(value = "/events",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateEvent(@PathVariable int id) {
        return "events";
    }


}
