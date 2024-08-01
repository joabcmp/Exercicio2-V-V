package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IngressoTest {

	@Test
	void testCriarIngressoValido() {
		try {
			double  precoBase = 10;
			Ingresso ingresso = new Ingresso(precoBase, TipoIngresso.NORMAL);
		}catch(IllegalArgumentException e){
			assertEquals("Entrada inválida", e.getMessage());
		}
	}
	
	@Test
	void testGetPrecoIngressoNORMAL() {
		double  precoBase = 10;
		double  precoEsperado = 10;
		Ingresso ingresso = new Ingresso(precoBase, Status.NORMAL);
		assertEquals(precoEsperado, ingresso.getPreco());
	}

	@Test
	void testGetPrecoIngressoVIP() {
		double  precoBase = 10;
		double  precoEsperado = 20;
		Ingresso ingresso = new Ingresso(precoBase, Status.VIP);
		assertEquals(precoEsperado, ingresso.getPreco());
	}
	
	@Test
	void testGetPrecoIngressoMEIA_ENTRADA() {
		double  precoBase = 10;
		double  precoEsperado = 5;
		Ingresso ingresso = new Ingresso(precoBase, Status.MEIA_ENTRADA);
		assertEquals(precoEsperado, ingresso.getPreco());
	}
	
	@Test
	void testIngressoInicializaNAO_VENDIDO() {
		Status statusEsperado = Status.NAO_VENDIDO;
		Ingresso ingresso = new Ingresso(10, Status.NORMAL);
		
		assertEquals(statusEsperado, ingresso.getStatus());
	}
	
	@Test
	void testVendaDeIngresso() {
		Status statusEsperado = Status.VENDIDO;
		Ingresso ingresso = new Ingresso(10, Status.NORMAL);
		ingresso.setStatus(Status.VENDIDO);
		
		assertEquals(statusEsperado, ingresso.getStatus());
	}
	
	@Test
	void testVendeIngressoDuasVezes() {
		try {
			Status statusEsperado = Status.VENDIDO;
			Ingresso ingresso = new Ingresso(10, Status.NORMAL);
			ingresso.setStatus(Status.VENDIDO);
			ingresso.setStatus(Status.VENDIDO);
		}catch(IllegalArgumentException e){
			assertEquals("Ingresso já vendido", e.getMessage());
		}
	}
}
