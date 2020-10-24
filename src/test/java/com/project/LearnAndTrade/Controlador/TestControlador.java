package com.project.LearnAndTrade.Controlador;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
public class TestControlador {

    public TestControlador() throws Exception {

    }

    @Test
    @Order(1)
    public void puedeLoguear() throws Exception {

    }

}
