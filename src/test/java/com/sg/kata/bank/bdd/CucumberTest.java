package com.sg.kata.bank.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        plugin = {"pretty"},
        glue = "com.sg.kata.bank.bdd.steps"
)
public class CucumberTest {
}
