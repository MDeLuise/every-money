package com.github.mdeluise.everymoney.integration.steps;

import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

@Component
public class StepData {
    private ResultActions resultActions;


    public ResultActions getResultActions() {
        return resultActions;
    }


    public void setResultActions(ResultActions resultActions) {
        this.resultActions = resultActions;
    }
}
