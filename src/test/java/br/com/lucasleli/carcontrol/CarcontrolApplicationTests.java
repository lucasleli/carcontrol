package br.com.lucasleli.carcontrol;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

@ActiveProfiles("test")
@AutoConfigureWireMock(port = 0)
@AutoConfigureMockMvc
@EnabledIf(expression = "#{environment['spring.profiles.active'] == null}")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarcontrolApplicationTests<T> {

    @Autowired
    protected MockMvc mockMvc;

    private final YamlPropertiesFactoryBean yamlProperties = new YamlPropertiesFactoryBean();

    private final T endpointTest = (T) this;

    protected CarcontrolApplicationTests() {
        super();
        this.yamlProperties.setResources(
                new ClassPathResource("scenarios/endPoints/" + endpointTest.getClass().getSimpleName() + ".yml")
        );
    }

    protected String getScenarioRequestBody(final String scenario) {
        return Objects.requireNonNull(yamlProperties.getObject(), "Cenario inexistente!").getProperty(scenario + ".requestBody");
    }


    protected CarcontrolApplicationTests<T> mockGet(String path, int responseCode, String body) {
        stubFor(WireMock.get(WireMock.urlPathEqualTo(path))
                .willReturn(WireMock.aResponse().withStatus(responseCode).withBody(body)));
        return this;
    }

    protected CarcontrolApplicationTests<T> mockPost(String path, int responseCode, String body, String requestBody) {
        stubFor(WireMock.post(WireMock.urlPathEqualTo(path)).withRequestBody((WireMock.equalToJson(requestBody)))
                .willReturn(WireMock.aResponse().withStatus(responseCode).withBody(body)));
        return this;
    }

}
