package me.study.demospringwebmvc;

import org.springframework.http.HttpMethod;
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
    @GetMapping("/hello") // 하나의 요청만 사용 할 경우우    @ResponseBody // 응답 본문으로 리턴, 작성하지 않을시 view를 찾아감
    public String hello() {
        return "hello";
    }

    // ? 한글자 매핑
    // * 여러글자
    // ** 여러 패스스    @RequestMapping("/**")
    // spring boot 는 확장자 매핑을 사용하지 못하도록 설정되어 있다.
    @ResponseBody
    public String helloDong() {
        return "hello";
    }
}