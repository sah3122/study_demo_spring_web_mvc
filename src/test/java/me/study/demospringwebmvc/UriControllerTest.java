package me.study.demospringwebmvc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
        mockMvc.perform(get("/events/form"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("/evnets/form"))
                .andExpect(model().attributeExists("event"))
        ;
    }

}