package net.atos.api.cliente.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.BadRequestException;

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
import net.atos.api.cliente.domain.EnderecoVO;
import net.atos.api.cliente.domain.TipoEndereco;
import net.atos.api.cliente.factory.ClienteFactory;
import net.atos.api.cliente.repository.ClienteRepository;
import net.atos.api.cliente.repository.entity.ClienteEntity;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ExcluiClienteServiceTest {
	
	private Validator validator;
	
	private BuscaClienteService buscaClienteService;

	private ExcluiClienteService excluiClienteService;
	
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
		excluiClienteService = new ExcluiClienteService(validator, clienteRepository,
				buscaClienteService);
		
	}

	@Test
	@DisplayName("Testa se id do Cliente é nulo")
	void test_ClienteNulo_lancaExcecao() {

		assertNotNull(excluiClienteService);
		
		Long clienteId = null;
		
		var assertThrows = assertThrows(BadRequestException.class, ()->
			excluiClienteService.remover(clienteId));
		
		assertNotNull(assertThrows);
		assertEquals(assertThrows.getMessage(),
				"Identificador de exclusão do cliente inválido");
		
	}
	
	@Test
	@DisplayName("Testa exclusão do Cliente")
	void test_ClienteExcluido_retornaOk() {
		
		assertNotNull(excluiClienteService);
		
		Long clienteId = 123l;
		ClienteVO cliente = new ClienteVO();
		
		cliente.setNome("Loki da Silva Oliveira");
		cliente.setCpf("05362695860");
		cliente.setRg("20556585221");
		cliente.setNascimento(LocalDate.now());
		cliente.setEmail("loki@gmail.com");
		cliente.setCelular(899554415l);
		cliente.setEnderecos(new ArrayList<EnderecoVO>());
		
		EnderecoVO endereco = new EnderecoVO();
		endereco.setRua("rua do husky");
		endereco.setNumero("123A");
		endereco.setBairro("Benjamin Franklin");
		endereco.setCidade("Cidade dos Anjos");
		endereco.setUF("CE");
		endereco.setPais("Brasil");
		endereco.setCep(65625596);
		endereco.setTelefone_fixo(835629566);
		endereco.setTipoEndereco(TipoEndereco.COMERCIAL);
		
		cliente.add(endereco);
				
		ClienteEntity clienteEntity = new ClienteFactory(cliente).toEntity();

        when(clienteRepository.findById(clienteId))
        	.thenReturn(Optional.of(clienteEntity));
        
        excluiClienteService.remover(clienteId);
        
		then(clienteRepository).should(times(1)).findById(anyLong());
		then(clienteRepository).should(times(1)).deleteById(anyLong());

		assertNotNull(clienteId);
		
	}
	
}
