package com.github.mdeluise.everymoney.integration.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mdeluise.everymoney.authentication.payload.request.LoginRequest;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class AuthenticationSteps {
    final String authPath = "/authentication";
    final MockMvc mockMvc;
    final StepData stepData;
    final int port;
    final ObjectMapper objectMapper;


    @Autowired
    public AuthenticationSteps(@Value("${server.port}") int port, MockMvc mockMvc, StepData stepData,
                               ObjectMapper objectMapper) {
        this.port = port;
        this.mockMvc = mockMvc;
        this.stepData = stepData;
        this.objectMapper = objectMapper;
    }


    @Given("the client login with username {string} and password {string}")
    public void theClientLoginWithUsernameAndPassword(String username, String password) throws Exception {
        LoginRequest loginRequest = new LoginRequest(username, password);
        stepData.setResultActions(mockMvc.perform(
            MockMvcRequestBuilders.post(String.format("http://localhost:%s%s/login", port, authPath))
                                  .contentType(MediaType.APPLICATION_JSON)
                                  .content(objectMapper.writeValueAsString(loginRequest)))
        );
    }
}
