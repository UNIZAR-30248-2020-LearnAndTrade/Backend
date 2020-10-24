package com.project.LearnAndTrade;

import com.project.LearnAndTrade.Controlador.TestControlador;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LearnAndTradeApplicationTests {

	TestControlador testControlador = new TestControlador();

	LearnAndTradeApplicationTests() throws Exception {}

	@Test
	void main() throws Exception {
		System.out.println("Test pasados con éxito");
	}

}
