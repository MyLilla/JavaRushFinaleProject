package com.javarush.jira.profile.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.jira.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.login.internal.web.UserTestData.*;
import static com.javarush.jira.profile.web.ProfileTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProfileRestControllerTest extends AbstractControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getTest_WithoutAuth() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/profile"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getTest() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/profile"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROF_MATCHER.contentJson(PROFILE_TO))
                .andDo(print());
    }

    @Test
    void updateTest_WithoutAuth() throws Exception {
        perform(MockMvcRequestBuilders.put("/api/profile"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateTest() throws Exception {
        perform(MockMvcRequestBuilders.put("/api/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(PROFILE_TO)))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
