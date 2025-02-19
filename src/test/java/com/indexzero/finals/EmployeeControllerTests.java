package com.indexzero.finals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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



}
