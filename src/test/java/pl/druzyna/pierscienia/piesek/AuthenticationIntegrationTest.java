package pl.druzyna.pierscienia.piesek;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import pl.druzyna.pierscienia.piesek.model.entity.UserAccount;
import pl.druzyna.pierscienia.piesek.security.SecurityConstants;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthenticationIntegrationTest {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationIntegrationTest.class);

    @Autowired
    MockMvc mockMvc;

    @LocalServerPort
    int port;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${defaultAdminAccount.password}")
    String adminDefaultPassword;

    @Value("${defaultAdminAccount.email}")
    String adminDefaultEmail;

    @Test
    void givenDefaultAdminCredentials_shouldReceiveOkResponse() throws Exception {
        ResponseEntity<String> response = tryToLogInWithValidData();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void givenDefaultAdminCredentials_shouldReceiveBearerToken() throws Exception {
        ResponseEntity<String> response = tryToLogInWithValidData();
        String authorizationHeader = response.getHeaders().getFirst(SecurityConstants.JWT_HEADER_NAME);
        assertNotNull(authorizationHeader);
        assertTrue(authorizationHeader.startsWith(SecurityConstants.JWT_HEADER_VALUE_PREFIX));
    }

    private ResponseEntity<String> tryToLogInWithValidData() throws Exception {
        UserAccount userAccount = new UserAccount();
        userAccount.setEmail(adminDefaultEmail);
        userAccount.setPassword(adminDefaultPassword);
        String body = objectMapper.writeValueAsString(userAccount);

        log.info("Attempting test login with email {} and password {}", userAccount.getEmail(),
                userAccount.getPassword());

        TestRestTemplate testRestTemplate = new TestRestTemplate();
        return testRestTemplate
                .postForEntity(
                        "http://localhost:" + port + "api/login",
                        body,
                        String.class
                );
    }
}