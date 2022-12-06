package com.github.mdeluise.everymoney.integration.steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class IntegrationSteps {
    MockMvc mockMvc;
    StepData stepData;
    int port;


    @Autowired
    public IntegrationSteps(@Value("${server.port}") int port, MockMvc mockMvc, StepData stepData) {
        this.port = port;
        this.mockMvc = mockMvc;
        this.stepData = stepData;
    }


    @When("the client calls GET {string}")
    public void theClientCallsGet(String url) throws Exception {
        stepData.setResultActions(
            mockMvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON)));
    }


    @When("the client calls POST {string} with body {string}")
    public void theClientCallsPostWithBody(String url, String body) throws Exception {
        stepData.setResultActions(
            mockMvc.perform(MockMvcRequestBuilders.post(url).contentType(MediaType.APPLICATION_JSON).content(body)));
    }


    @When("the client calls PUT {string} with body {string}")
    public void theClientCallsPutWithBody(String url, String body) throws Exception {
        stepData.setResultActions(
            mockMvc.perform(MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON).content(body)));
    }


    @When("the client calls DELETE {string} with body {string}")
    public void theClientCallsDeleteWalletWithBody(String url, String body) throws Exception {
        stepData.setResultActions(
            mockMvc.perform(MockMvcRequestBuilders.delete(url).contentType(MediaType.APPLICATION_JSON).content(body)));
    }


    @Then("the client receives status code of {int}")
    public void theClientReceivesStatusCode(int status) throws Exception {
        stepData.getResultActions().andExpect(MockMvcResultMatchers.status().is(status));
    }
}
