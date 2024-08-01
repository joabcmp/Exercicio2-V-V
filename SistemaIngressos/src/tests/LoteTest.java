package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.jupiter.api.Test;

class LoteTest {

	@Test
	void testCriarLoteValido() {
		try {
			int numIngressos = 10;
			double porcentagemVIP = 0.3;
			double desconto = 0.25;
			int precoBase = 10;
			
			Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
		}catch(IllegalArgumentException e){
			assertEquals("Entrada inválida", e.getMessage());
		}
	}
	
	@Test
	void testCriarLoteInvalidoNumeroIngressosVazio() {
		try {
			int numIngressos = 0;
			double porcentagemVIP = 0.3;
			double desconto = 0.25;
			int precoBase = 10;
			
			Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
		}catch(IllegalArgumentException e){
			assertEquals("Numero de ingressos vazio", e.getMessage());
		}
	}
	
	@Test
	void testCriarLoteInvalidoPorcentagemMaxVipExcedida() {
		try {
			int numIngressos = 10;
			double porcentagemVIP = 0.31;
			double desconto = 0.1;
			int precoBase = 10;
			
			Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
		}catch(IllegalArgumentException e){
			assertEquals("Porcentagem maxima de ingressos VIP excedida", e.getMessage());
		}
	}
	
	@Test
	void testCriarLoteInvalidoPorcentagemMinVipNaoAtingida() {
		try {
			int numIngressos = 10;
			double porcentagemVIP = 0.19;
			double desconto = 0.1;
			int precoBase = 10;
			
			Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
		}catch(IllegalArgumentException e){
			assertEquals("Porcentagem maxima de ingressos VIP excedida", e.getMessage());
		}
	}

	@Test
	void testCriarLoteInvalidoDescontoLimiteExcedido() {
		try {
			int numIngressos = 10;
			double porcentagemVIP = 0.19;
			double desconto = 0.26;
			int precoBase = 10;
			
			Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
		}catch(IllegalArgumentException e){
			assertEquals("Porcentagem de desconto limite excedida", e.getMessage());
		}
	}
	
	@Test
	void testGetIngressos() {
		Set<Ingresso> respostaEsperada = new HashSet<Ingresso>();
		int precoBase = 10;
		Ingresso i1 = new Ingresso(precoBase, Status.NORMAL);
		respostaEsperada.add(i1);
		Ingresso i2 = new Ingresso(precoBase, Status.NORMAL);
		respostaEsperada.add(i2);
		Ingresso i3 = new Ingresso(precoBase, Status.NORMAL);
		respostaEsperada.add(i3);
		Ingresso i4 = new Ingresso(precoBase, Status.NORMAL);
		respostaEsperada.add(i4);
		Ingresso i5 = new Ingresso(precoBase, Status.NORMAL);
		respostaEsperada.add(i5);
		Ingresso i6 = new Ingresso(precoBase, Status.NORMAL);
		respostaEsperada.add(i6);
		Ingresso i7 = new Ingresso(precoBase, Status.NORMAL);
		respostaEsperada.add(i7);
		Ingresso i8 = new Ingresso(precoBase, Status.MEIA_ENTRADA);
		respostaEsperada.add(i8);
		Ingresso i9 = new Ingresso(precoBase, Status.VIP);
		respostaEsperada.add(i9);
		Ingresso i10 = new Ingresso(precoBase, Status.VIP);
		respostaEsperada.add(i10);
		
		int numIngressos = 10;
		double porcentagemVIP = 0.2;
		double desconto = 0.25;
		int precoBaseLote = 10;
		
		Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBaseLote);
		assertEquals(respostaEsperada, lote.getIngressos());
	}
	
	@Test
	void testComprarIngressos() {
		Set<Ingresso> respostaEsperada = new HashSet<Ingresso>();
		int precoBase = 10;
		Ingresso i1 = new Ingresso(precoBase, Status.NORMAL);
		respostaEsperada.add(i1);
		Ingresso i2 = new Ingresso(precoBase, Status.NORMAL);
		respostaEsperada.add(i2);
		Ingresso i3 = new Ingresso(precoBase, Status.NORMAL);
		respostaEsperada.add(i3);
		Ingresso i4 = new Ingresso(precoBase, Status.NORMAL);
		respostaEsperada.add(i4);
		Ingresso i5 = new Ingresso(precoBase, Status.NORMAL);
		respostaEsperada.add(i5);
		Ingresso i6 = new Ingresso(precoBase, Status.NORMAL);
		respostaEsperada.add(i6);
		Ingresso i7 = new Ingresso(precoBase, Status.NORMAL);
		respostaEsperada.add(i7);
		Ingresso i8 = new Ingresso(precoBase, Status.MEIA_ENTRADA);
		respostaEsperada.add(i8);
		Ingresso i9 = new Ingresso(precoBase, Status.VIP);
		respostaEsperada.add(i9);
		Ingresso i10 = new Ingresso(precoBase, Status.VIP);
		respostaEsperada.add(i10);
		
		for (String ingresso : respostaEsperada) { 
            ingresso.setStatus(Status.VENDIDO);
        } 
		
		int numIngressos = 10;
		double porcentagemVIP = 0.2;
		double desconto = 0.25;
		int precoBaseLote = 10;
		
		Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBaseLote);
		lote.compraIngressos();
		assertEquals(respostaEsperada, lote.getIngressos());
	}
	
	@Test
	void testGetNumIngressosNormalVendidos() {
		int respostaEsperada = 7;
		int numIngressos = 10;
		double porcentagemVIP = 0.2;
		double desconto = 0.1;
		int precoBase = 10;
		
		Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
		lote.compraIngressos();
		assertEquals(respostaEsperada, lote.GetNumIngressosNormalVendidos());
	}

	@Test
	void testGetNumIngressosVipVendidos() {
		int respostaEsperada = 2;
		int numIngressos = 10;
		double porcentagemVIP = 0.2;
		double desconto = 0.1;
		int precoBase = 10;
		
		Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
		lote.compraIngressos();
		assertEquals(respostaEsperada, lote.GetNumIngressosVipVendidos());
	}
	
	@Test
	void testGetNumIngressosMeialVendidos() {
		int respostaEsperada = 1;
		int numIngressos = 10;
		double porcentagemVIP = 0.2;
		double desconto = 0.1;
		int precoBase = 10;
		
		Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
		lote.compraIngressos();
		assertEquals(respostaEsperada, lote.GetNumIngressosMeiaVendidos());
	}
	
	@Test
	void testGetReceita() {
		double respostaEsperada = 7*10*0.9 + 2*20*0.9 + 1*5;
		int numIngressos = 10;
		double porcentagemVIP = 0.2;
		double desconto = 0.1;
		int precoBase = 10;
		
		Lote lote = new Lote(numIngressos, desconto, porcentagemVIP, precoBase);
		lote.compraIngressos();
		assertEquals(respostaEsperada, lote.getReceita());
	}
}
