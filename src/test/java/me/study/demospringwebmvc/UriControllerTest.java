package me.study.demospringwebmvc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Map;

import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class UriControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void deleteEvent() throws Exception {
        //given
        mockMvc.perform(delete("/events/1;name=dong"))
                //.accept(MediaType.APPLICATION_JSON)) // accept 가 없을시 아무 응답이나 받는다는 뜻으로 받아드린다.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
        ;
    }

    @Test
    public void errorEvent() throws Exception {
        //given
        ResultActions resultActions = mockMvc.perform(post("/events/?name=dong")
                .param("name", "dong")
                .param("limit", "-1"))
                //.accept(MediaType.APPLICATION_JSON)) // accept 가 없을시 아무 응답이나 받는다는 뜻으로 받아드린다.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().hasErrors());
        ModelAndView modelAndView = resultActions.andReturn().getModelAndView();
        Map<String, Object> model = modelAndView.getModel();
        System.out.println(model.size());
    }

    @Test
    public void postEvent() throws Exception {
        //given
        mockMvc.perform(post("/events/?name=dong")
                .param("name", "dong")
                .param("limit", "1"))
                //.accept(MediaType.APPLICATION_JSON)) // accept 가 없을시 아무 응답이나 받는다는 뜻으로 받아드린다.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
        ;
    }

    @Test
    public void eventForm() throws Exception {
        //given
        MockHttpServletRequest request = mockMvc.perform(get("/events/form"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/evnets/form"))
                .andExpect(model().attributeExists("event"))
                .andExpect(request().sessionAttribute("event", notNullValue()))
                .andReturn().getRequest();

        Object event = request.getSession().getAttribute("event");
        System.out.println(event);
    }

    @Test
    public void getEvents() throws Exception {
        Event newEvent = new Event();
        newEvent.setNaem("dong");
        newEvent.setLimit(10);

        mockMvc.perform(get("/events/list")
        .sessionAttr("visitTime", LocalDateTime.now())
        .flashAttr("newEvent", newEvent))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("//p").nodeCount(2)); // P 노드가 몇개인지 확인하는 테스트 코드
    }


}