package me.study.demospringwebmvc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.junit.Assert.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void helloTest() throws Exception {
        //given
        mockMvc.perform(get("/hello")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header(HttpHeaders.FROM, " localhost"))
                //.accept(MediaType.APPLICATION_JSON)) // accept 가 없을시 아무 응답이나 받는다는 뜻으로 받아드린다.
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello"))
                ;
    }

    // GET /events
    @Test
    public void getEvents() throws Exception {
        //given
        mockMvc.perform(get("/events"))
                .andExpect(status().isOk());
    }

    // GET /events/{id}
    @Test
    public void getEventsWithId() throws Exception {
        //given
        mockMvc.perform(get("/events/2"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/events/2"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/events/3"))
                .andExpect(status().isOk());
    }

    // POST /events CONTENTS-TYPE; application/json ACCEPT; application/json
    @Test
    public void createEvent() throws Exception {
        //given
        mockMvc.perform(post("/events")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // PUT /events CONTENTS-TYPE; application/json ACCEPT; application/json
    @Test
    public void updateEvent() throws Exception {
        //given
        mockMvc.perform(put("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // DELETE /events/{id}
    @Test
    public void deleteEventsWithId() throws Exception {
        //given
        mockMvc.perform(delete("/events/2"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/events/2"))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/events/3"))
                .andExpect(status().isOk());
    }

}