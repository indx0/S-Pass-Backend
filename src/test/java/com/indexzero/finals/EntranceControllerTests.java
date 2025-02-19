package com.indexzero.finals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EntranceControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUserEntries() throws Exception {
        this.mockMvc.perform(
                        get("/api/entrance")
                                .with(httpBasic("pivanov", "HelloWorld1234"))
                )
                .andDo(print())
                .andExpect(status().isOk()
                );
    }

    @Test
    void getUserEntriesUnathorized() throws Exception {
        this.mockMvc.perform(
                        get("/api/entrance")
                                .with(httpBasic("pivanov", "HelloWorld12345"))
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getUserLastEntry() throws Exception {
        this.mockMvc.perform(
                        get("/api/entrance/last")
                                .with(httpBasic("pivanov", "HelloWorld1234"))
                )
                .andDo(print())
                .andExpect(status().isOk()
                );
    }

    @Test
    void getUserLastEntryUnathorized() throws Exception {
        this.mockMvc.perform(
                        get("/api/entrance/last")
                                .with(httpBasic("pivanov", "HelloWorld12345"))
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getUserAllEntries() throws Exception {
        this.mockMvc.perform(
                        get("/api/entrance/all")
                                .with(httpBasic("pivanov", "HelloWorld1234"))
                )
                .andDo(print())
                .andExpect(status().isOk()
                );
    }

    @Test
    void getUserAllEntriesForbidden() throws Exception {
        this.mockMvc.perform(
                        get("/api/entrance/all")
                                .with(httpBasic("afedorov", "HelloWorld1234"))
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getUserAllEntriesUnauthorized() throws Exception {
        this.mockMvc.perform(
                        get("/api/entrance/all")
                                .with(httpBasic("pivanov", "HelloWorld12345"))
                )
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getUserEntriesByLogin() throws Exception {
        this.mockMvc.perform(
                        get("/api/entrance/pivanov")
                                .with(httpBasic("pivanov", "HelloWorld1234"))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void getUserEntriesByLoginNotFound() throws Exception {
        this.mockMvc.perform(
                        get("/api/entrance/pivanova")
                                .with(httpBasic("pivanov", "HelloWorld1234"))
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserEntriesByLoginForbidden() throws Exception {
        this.mockMvc.perform(
                        get("/api/entrance/pivanov")
                                .with(httpBasic("afedorov", "HelloWorld1234"))
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
