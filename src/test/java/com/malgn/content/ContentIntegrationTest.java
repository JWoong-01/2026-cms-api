package com.malgn.content;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@Transactional
class ContentIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;
    private String user1Token;
    private String user2Token;
    private String adminToken;

    @BeforeEach
    void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilters(springSecurityFilterChain)
            .build();
        user1Token = issueToken("user1", "user1234");
        user2Token = issueToken("user2", "user1234");
        adminToken = issueToken("admin", "admin1234");
    }

    @Test
    void getContentsSupportsPaging() throws Exception {
        mockMvc.perform(get("/api/contents?page=0&size=2")
                .header(HttpHeaders.AUTHORIZATION, bearer(user1Token)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.items.length()").value(2))
            .andExpect(jsonPath("$.page").value(0))
            .andExpect(jsonPath("$.size").value(2));
    }

    @Test
    void userCanCreateAndUpdateOwnContent() throws Exception {
        MvcResult createResult = mockMvc.perform(post("/api/contents")
                .header(HttpHeaders.AUTHORIZATION, bearer(user1Token))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "title": "My First Content",
                      "description": "owned by user1"
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.createdBy").value("user1"))
            .andReturn();

        Long createdId = readId(createResult);

        mockMvc.perform(put("/api/contents/{id}", createdId)
                .header(HttpHeaders.AUTHORIZATION, bearer(user1Token))
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "title": "My Updated Content",
                      "description": "updated by user1"
                    }
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("My Updated Content"))
            .andExpect(jsonPath("$.lastModifiedBy").value("user1"));
    }

    @Test
    void userCannotDeleteOthersContent() throws Exception {
        mockMvc.perform(delete("/api/contents/2")
                .header(HttpHeaders.AUTHORIZATION, bearer(user1Token)))
            .andExpect(status().isForbidden());
    }

    @Test
    void adminCanDeleteAnyContent() throws Exception {
        mockMvc.perform(delete("/api/contents/3")
                .header(HttpHeaders.AUTHORIZATION, bearer(adminToken)))
            .andExpect(status().isNoContent());
    }

    private String issueToken(String username, String password) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "username": "%s",
                      "password": "%s"
                    }
                    """.formatted(username, password)))
            .andExpect(status().isOk())
            .andReturn();

        JsonNode body = objectMapper.readTree(result.getResponse().getContentAsString());
        return body.get("accessToken").asText();
    }

    private Long readId(MvcResult result) throws Exception {
        JsonNode body = objectMapper.readTree(result.getResponse().getContentAsString());
        return body.get("id").asLong();
    }

    private String bearer(String token) {
        return "Bearer " + token;
    }
}
