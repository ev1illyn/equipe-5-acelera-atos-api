package net.atos.api.cliente.service;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.NotFoundException;

import com.sun.jdi.LongValue;
import net.atos.api.cliente.repository.entity.ClienteEntity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import net.atos.api.cliente.domain.ClienteVO;
import net.atos.api.cliente.repository.ClienteRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class ExcluiClienteServiceTest {
	
	private Validator validator;
	
	private BuscaClienteService buscaClienteService;

	private ExcluiClienteService excluiClienteService;

	@Mock
	private ClienteRepository clienteRepository;

	public ExcluiClienteServiceTest() {}


	@BeforeAll
	public void inicioGeral() {
		
		ValidatorFactory validatorFactor =
				Validation.buildDefaultValidatorFactory();
		
		this.validator = validatorFactor.getValidator();
	}

	@BeforeEach
	@DisplayName("Teste executado antes cada teste")
	public void iniciarCadaTeste() {

		this.clienteRepository = Mockito.mock(ClienteRepository.class);

		this.buscaClienteService = Mockito.mock(BuscaClienteService.class);

		this.excluiClienteService = new ExcluiClienteService(this.validator,this.clienteRepository,this.buscaClienteService);
	}

	@Test
	@DisplayName("Testa se Cliente existe")
	void test_Se_ClinteExiste_LancaExcecao() {
		assertNotNull(excluiClienteService);

		ClienteVO cliente = null;

		var assertThrows = assertThrows(NullPointerException.class, ()->
				excluiClienteService.excluir(cliente.getId()));

		assertNotNull(assertThrows);
	}

	@Test
	@DisplayName("testa delete Cliente")
	public void test_Deleta_ClinteExistente_LancaExcecao() {
		ClienteEntity clienteTreinado = new ClienteEntity();
		clienteTreinado.setId(3l);

		when(this.buscaClienteService.recuperarPorId(anyLong()))
				.thenReturn(clienteTreinado);

		var assertThrows = assertThrows(Exception.class,
				()-> this.excluiClienteService.excluir(Long.valueOf(3l)));

		assertNotNull(assertThrows);
		assertEquals("Deletado",assertThrows.getMessage());


	}

}
