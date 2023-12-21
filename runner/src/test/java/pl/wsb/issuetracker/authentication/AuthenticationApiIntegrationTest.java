package pl.wsb.issuetracker.authentication;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"test"})
public class AuthenticationApiIntegrationTest {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String CREDENTIALS_JSON = "{\"login\":\"jan.nowak@company.com\",\"password\":\"password\"}";
    private static final String BAD_CREDENTIALS_JSON = "{\"login\":\"jan.nowak@company.com\",\"password\":\"bad_password\"}";
    private static final String AUTHENTICATE_ENDPOINT = "/api/public/authenticate";
    private static final String LOGGED_USER_ENDPOINT = "/api/secured/me";
    private static final String TOKEN_JSON_PATH = "$.token";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldAuthenticate() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(AUTHENTICATE_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(CREDENTIALS_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath(TOKEN_JSON_PATH).isNotEmpty())
                .andExpect(header().exists(AUTHORIZATION_HEADER_NAME))
                .andReturn();
        String jwt = mvcResult.getResponse().getHeader(AUTHORIZATION_HEADER_NAME);
        mockMvc.perform(MockMvcRequestBuilders.get(LOGGED_USER_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION_HEADER_NAME, jwt))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    void shouldNotAuthenticate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(AUTHENTICATE_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(BAD_CREDENTIALS_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }

}
