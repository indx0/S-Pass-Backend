package com.indexzero.finals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void userLogin() throws Exception {
        this.mockMvc.perform(
                post("/api/employee/login")
                    .with(httpBasic("pivanov", "HelloWorld1234"))
                )
                .andDo(print())
                .andExpect(status().isOk()
        );
    }

    @Test
    void userLoginWrong() throws Exception {
        this.mockMvc.perform(
                        post("/api/employee/login")
                                .with(httpBasic("pivanov", "HelloWorld12345")))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void doorOpen() throws Exception {
        this.mockMvc.perform(
                        patch("/api/employee/open")
                                .param("code", "1234567890123456789")
                                .with(httpBasic("pivanov", "HelloWorld1234")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void doorOpenWrongCode() throws Exception {
        this.mockMvc.perform(
                        patch("/api/employee/open")
                                .param("code", "1234567770123456789")
                                .with(httpBasic("pivanov", "HelloWorld1234")))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void lockAndUnlockUser() throws Exception {
        this.mockMvc.perform(
                        patch("/api/employee/ipetrov/blocked")
                                .with(httpBasic("pivanov", "HelloWorld1234")))
                .andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(
                        patch("/api/employee/ipetrov/active")
                                .with(httpBasic("pivanov", "HelloWorld1234")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void lockAndUnlockUserNotFound() throws Exception {
        this.mockMvc.perform(
                        patch("/api/employee/PetrTestovich/blocked")
                                .with(httpBasic("pivanov", "HelloWorld1234")))
                .andDo(print())
                .andExpect(status().isNotFound());
        this.mockMvc.perform(
                        patch("/api/employee/PetrTestovich/active")
                                .with(httpBasic("pivanov", "HelloWorld1234")))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void lockAndUnlockUserForbidden() throws Exception {
        this.mockMvc.perform(
                        patch("/api/employee/PetrTestovich/blocked")
                                .with(httpBasic("afedorov", "HelloWorld1234")))
                .andDo(print())
                .andExpect(status().isForbidden());
        this.mockMvc.perform(
                        patch("/api/employee/PetrTestovich/active")
                                .with(httpBasic("afedorov", "HelloWorld1234")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getAll() throws Exception {
        this.mockMvc.perform(
                        get("/api/employee/all")
                                .with(httpBasic("pivanov", "HelloWorld1234")))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getAllForbidden() throws Exception {
        this.mockMvc.perform(
                        get("/api/employee/all")
                                .with(httpBasic("afedorov", "HelloWorld1234")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }



}
