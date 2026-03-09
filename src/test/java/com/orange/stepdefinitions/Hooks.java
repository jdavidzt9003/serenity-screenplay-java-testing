package com.orange.stepdefinitions;

import io.cucumber.java.AfterAll;

public class Hooks {

    @AfterAll
    public static void execAfter(){
        System.out.println("Hola mundooooo");
    }
}
