package net.atos.api.cliente.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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

import net.atos.api.cliente.domain.ClienteVO;
import net.atos.api.cliente.repository.ClienteRepository;

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
		
		buscaClienteService = new BuscaClienteService(validator, clienteRepository);
		
	}

	@Test
	@DisplayName("Testa se Cliente é nulo")
	void test_ClienteNulo_lancaExcecao() {

		assertNotNull(excluiClienteService);
		
		ClienteVO cliente = null;
		
		var assertThrows = assertThrows(IllegalArgumentException.class, ()->
			excluiClienteService.persistir(cliente));
		
		assertNotNull(assertThrows);
		
	}
	
}
