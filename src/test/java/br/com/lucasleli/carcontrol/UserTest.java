package br.com.lucasleli.carcontrol;

import br.com.lucasleli.carcontrol.model.User;
import br.com.lucasleli.carcontrol.repository.UserRepository;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static br.com.lucasleli.carcontrol.controller.UserController.USER_V1_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

public class UserTest extends CarcontrolApplicationTests<UserTest> {

    UserRepository userRepository;

    @Autowired
    public UserTest(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setup() {
        WireMock.reset();
    }

    @Test
    @Sql("/user.sql")
    @Sql(value = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void when_create_user_should_return_created() throws Exception {
        List<User> users = userRepository.findAll();
        assertEquals(2, userRepository.findAll().size());

        String uri = fromPath(USER_V1_URL).toUriString();

        mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(getScenarioRequestBody("whenPostUserReturnSucess")))
                .andDo(print())
                .andExpect(status().isCreated());

        assertEquals(3, userRepository.findAll().size());
    }

    @Test
    @Sql("/user.sql")
    @Sql(value = "/clean.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void when_create_user_should_return_badRequest() throws Exception {
        List<User> users = userRepository.findAll();
        assertEquals(2, userRepository.findAll().size());

        String uri = fromPath(USER_V1_URL).toUriString();

        mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(getScenarioRequestBody("whenPostUserReturnBadRequest")))
                .andDo(print())
                .andExpect(status().isBadRequest());

        assertEquals(2, userRepository.findAll().size());
    }

}
