package com.github.mdeluise.everymoney.integration;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "classpath:features",
    glue = {
        "com.github.mdeluise.everymoney.integration",
        "com.github.mdeluise.everymoney.integration.steps"
    },
    plugin = {"pretty"}
)
public class IntegrationRunnerTest {
}
