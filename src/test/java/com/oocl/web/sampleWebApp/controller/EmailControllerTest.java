package com.oocl.web.sampleWebApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oocl.web.controller.EmailController;
import com.oocl.web.sampleWebApp.model.Email;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmailControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new EmailController()).build();
    }

    @Test
    public void shouldCreateEmail() throws Exception {
        // given
        Email email = new Email("zhangsan", "lizi", "test", "test content");

        // when
        this.mockMvc.perform(
                post("/emails").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(email))
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("location", containsString("/emails")))
                .andExpect(jsonPath("$.from", is("zhangsan")));
    }


    @Test
    public void shouldGot400ErrorForCreateEmailWhenToFieldIsMissing() throws Exception {
        // given
        Email email = new Email("zhangsan", null, "test", "test content");

        // when
        this.mockMvc.perform(
                post("/emails").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(email))
        ).andDo(print())
                .andExpect(status().isBadRequest());
    }
}
