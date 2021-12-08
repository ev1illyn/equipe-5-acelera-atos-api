package net.atos.api.cliente.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.NotFoundException;

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

import net.atos.api.cliente.repository.ClienteRepository;
import net.atos.api.cliente.repository.entity.ClienteEntity;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class BuscaClienteServiceTest {

	private BuscaClienteService buscaClienteService;
	
	private Validator validator;
	
	@Mock
	private ClienteRepository clienteRepository;
	
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
	@DisplayName("Testa quando não encontra cliente por Id")
	void test_consultaPorIdNaoEncontrado_clienteCadastrado_lancaExcecao() {

		assertNotNull(buscaClienteService);

		Long idTeste = 5648l;
		
		var assertThrows = assertThrows(NotFoundException.class, ()->
			buscaClienteService.recuperarPorId(idTeste));
		
		then(clienteRepository).should(times(1)).findById(anyLong());
		
		assertEquals(assertThrows.getMessage(), "Cliente "+ idTeste +" não encontrado");
		
	}
	
	@Test
	@DisplayName("Testa quando encontra cliente por Id")
	void test_consultaPorIdEncontrado_clienteCadastrado_retornaCliente() {

		assertNotNull(buscaClienteService);

		Long idTeste = 1234l;
		
		ClienteEntity clienteEntityTreinado = new ClienteEntity();
		clienteEntityTreinado.setId(idTeste);
		
		when(clienteRepository.findById(anyLong()))
			.thenReturn(Optional.of(clienteEntityTreinado));
		
		ClienteEntity clienteEntity = buscaClienteService.recuperarPorId(idTeste);
		
		then(clienteRepository).should(times(1)).findById(anyLong());
		
		assertNotNull(clienteEntity);
		
		assertEquals(idTeste, clienteEntity.getId());
		
	}
	
}
