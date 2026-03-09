package com.orange.stepdefinitions.login;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class LoginStepDefinitions {

    @Given("^(.*) abre la pagina de orangeHRM")
    public void abrirLaPagina(String actorName) {
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled(actorName);
        theActorInTheSpotlight().attemptsTo(
                Open.browserOn().thePageNamed("pages.orangeHRM")
        );
    }

    @When("ingresa las credenciales de login")
    public void ingresarCredenciales() {

    }
    @Then("debera ver el nombre al ingresar")
    public void validarIngreso() {

    }
}
