package net.atos.api.cliente.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.NotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import net.atos.api.cliente.domain.ClienteVO;
import net.atos.api.cliente.repository.ClienteRepository;
import net.atos.api.cliente.repository.entity.ClienteEntity;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class BuscaClienteServiceTest {

	private BuscaClienteService buscaClienteService;
	
	private Validator validator;
	
	@Mock
	private ClienteRepository clienteRepository;
	
	private Pageable pageable;
	
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
		this.pageable = Mockito.mock(Pageable.class);
		buscaClienteService = new BuscaClienteService(clienteRepository);
	}
	

	@Test
	@Disabled
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
	@Disabled
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
	
	/*
	@Test
	@DisplayName("Testa quando encontra clientes cadastrados")
	void test_consultaTodos_clientesCadastrados_retornaListaClientes() {
		
		assertNotNull(buscaClienteService);
		
		List<ClienteEntity> clientesTreinados = new ArrayList<>();
		clientesTreinados.add(new ClienteEntity());
		clientesTreinados.add(new ClienteEntity());
		clientesTreinados.add(new ClienteEntity());
		
		Page<ClienteEntity> clientesPaginados = new PageImpl<>(clientesTreinados, this.pageable, 0l);

		when(this.clienteRepository.findAll(any()))
				.thenReturn(Optional.of(clientesTreinados));

		Page<ClienteVO> clientesEncontrados = this.buscaClienteService.recuperarTodosVO(this.pageable);
		 
		then(this.clienteRepository).should(times(1)).findAll(any());
		
		assertNotNull(clientesEncontrados);
		assertEquals(3, clientesEncontrados.getSize());

		
	}*/
	
	
}
