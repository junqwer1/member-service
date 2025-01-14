package org.koreait.member.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ActiveProfiles({"default", "test", "jwt"})
@AutoConfigureMockMvc
@Transactional
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Test
    void  Jointest1() throws Exception {
        RequestJoin form = new RequestJoin();
        form.setEmail("user01@test.org");
        form.setName("이용자01");
        form.setPassword("_aA123456");
        form.setConfirmPassword(form.getPassword());
        form.setRequiredTerms1(true);
        form.setRequiredTerms2(true);
        form.setRequiredTerms3(true);
        form.setOptionalTerms(List.of("advertisement"));

        String body = om.writeValueAsString(form);
        mockMvc.perform(post("/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)).andDo(print());

        RequestLogin loginForm = new RequestLogin();
        loginForm.setEmail(form.getEmail());
        loginForm.setPassword(form.getPassword());
        String body3 = om.writeValueAsString(loginForm);
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(body3)).andDo(print()).andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

//        토큰으로 로그인 처리 테스트
        Map<String, String> data = om.readValue(body3, new TypeReference<Map<>>() {});
        String token =data.get("data");

        mockMvc.perform(get("/test")
                .header("Authorization", "Bearer " + token))
                .andDo(print());
    }
}
