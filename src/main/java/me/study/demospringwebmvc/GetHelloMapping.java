package me.study.demospringwebmvc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * custom annotation은 meta annotation만 사용 가능하다.
 * GetMapping 같은 Composed annotation은 meta annotation이 아니라서 사용 불가능.
 * Retention 해당 애노테이션 정보를 언제까지 유지 할 것인가
 * 기본값 : RetentionPolicy.CLASS
 * source : 소스코드에서만 유지 컴파일시 삭제
 * Class : 컴파일한 class파일에도 유지 런타임시 삭제
 * Runtime : 클래스를 메모리에 읽어 왔을때 까지 유지. 이정보를 바탕으로 특정 로직을 실행 가능
 *
 * Target
 * 해당 애노테이션을 어디에 사용할 수 있는지 결정
 *
 * Documented
 * 해당 애노테이션을 사용한 코드의 문서에 그 애노테이션에 대한 정보를 표기할 지 결정
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@RequestMapping(method = RequestMethod.GET, value = "/hello")
public @interface GetHelloMapping {
}
