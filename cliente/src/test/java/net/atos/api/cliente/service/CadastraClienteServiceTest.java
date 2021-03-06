package net.atos.api.cliente.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
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
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationEventPublisher;

import net.atos.api.cliente.domain.ClienteVO;
import net.atos.api.cliente.domain.EnderecoVO;
import net.atos.api.cliente.domain.TipoEndereco;
import net.atos.api.cliente.repository.ClienteRepository;
import net.atos.api.cliente.repository.entity.ClienteEntity;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CadastraClienteServiceTest {
	
	private CadastraClienteService cadastraClienteService;
	
	private Validator validator;
	
	@Mock
	private ClienteRepository clienteRepository;
	
	private ApplicationEventPublisher eventPublisher;

	@DisplayName("Teste executado antes de todos os testes")
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
		
		this.eventPublisher = Mockito.mock(ApplicationEventPublisher.class);
		
		cadastraClienteService = new CadastraClienteService(validator, clienteRepository, this.eventPublisher);
	}
		
	@Test
	@DisplayName("Testa obrigatoriedade dos campos de Cliente")
	void test_CamposObrigatoriosNulos_lancaExcecao() {
		
		assertNotNull(cadastraClienteService);
		 
		ClienteVO cliente = new ClienteVO();
		
		/*cliente.setNome("Loki da Silva Oliveira");
		cliente.setCpf("05362695860");
		cliente.setRg("20556585221");
		cliente.setNascimento(LocalDate.now());
		cliente.setEmail("loki@gmail.com");
		cliente.setCelular(899554415l);
		cliente.setEnderecos(new ArrayList<EnderecoVO>());*/

		var assertThrows = assertThrows(ConstraintViolationException.class, ()->
							cadastraClienteService.persistir(cliente));
		
		assertEquals(7, assertThrows.getConstraintViolations().size());
		
		List<String> mensagens = assertThrows.getConstraintViolations()
			.stream()
			.map(ConstraintViolation::getMessage)
			.collect(Collectors.toList());
		
		assertThat(mensagens, hasItems("Campo nome n??o pode ser nulo",
				"Campo cpf n??o pode ser nulo",
				"Campo rg n??o pode ser nulo",
				"Campo nascimento n??o pode ser nulo",
				"Campo email n??o pode ser nulo",
				"Campo celular n??o pode ser nulo",
				"Campo endere??o n??o pode ser nulo"));
		
	}
	
	@Test
	@DisplayName("Testa se Cliente ?? nulo")
	void test_ClienteNulo_lancaExcecao() {
		
		assertNotNull(cadastraClienteService);
		 
		ClienteVO cliente = null; 
		//ClienteVO cliente = new ClienteVO(); 

		var assertThrows = assertThrows(IllegalArgumentException.class, ()->
							cadastraClienteService.persistir(cliente));
		
		assertNotNull(assertThrows);
		
	}
	
	@Test
	@DisplayName("Testa obrigatoriedade de um endere??o")
	void test_UmEndereco_lancaExcecao() {
		
		assertNotNull(cadastraClienteService);
		
		ClienteVO cliente = new ClienteVO();

		cliente.setNome("Loki da Silva Oliveira");
		cliente.setCpf("05362695860");
		cliente.setRg("20556585221");
		cliente.setNascimento(LocalDate.now());
		cliente.setEmail("loki@gmail.com");
		cliente.setCelular(899554415l);
		cliente.setEnderecos(new ArrayList<EnderecoVO>());
		
		/*EnderecoVO endereco = new EnderecoVO();
		endereco.setRua("rua do husky");
		endereco.setNumero("123A");
		endereco.setBairro("Benjamin Franklin");
		endereco.setCidade("Cidade dos Anjos");
		endereco.setUF("CE");
		endereco.setPais("Brasil");
		endereco.setCep(65625596);
		endereco.setTelefone_fixo(835629566);
		endereco.setTipoEndereco(TipoEndereco.COMERCIAL);
		cliente.add(endereco);*/
		
		var assertThrows = assertThrows(ConstraintViolationException.class, ()->
				cadastraClienteService.persistir(cliente));
		
		assertEquals(1, assertThrows.getConstraintViolations().size());

		List<String> mensagens = assertThrows.getConstraintViolations()
			.stream()
			.map(ConstraintViolation::getMessage)
			.collect(Collectors.toList());

		assertThat(mensagens, hasItems("Campo endere??o deve ter pelo menos um item"));
		
	}

	@Test
	@DisplayName("Testa obrigatoriedade dos campos de um endere??o")
	void test_enderecoComCamposNulos_lancaExcecao() {
		
		assertNotNull(cadastraClienteService);
		
		ClienteVO cliente = new ClienteVO();
		
		cliente.setNome("Loki da Silva Oliveira");
		cliente.setCpf("05362695860");
		cliente.setRg("20556585221");
		cliente.setNascimento(LocalDate.now());
		cliente.setEmail("loki@gmail.com");
		cliente.setCelular(899554415l);
		cliente.setEnderecos(new ArrayList<EnderecoVO>());
		
		EnderecoVO endereco = new EnderecoVO();
		/*endereco.setRua("rua do husky");
		endereco.setNumero("123A");
		endereco.setBairro("Benjamin Franklin");
		endereco.setCidade("Cidade dos Anjos");
		endereco.setUF("CE");
		endereco.setPais("Brasil");
		endereco.setCep(65625596);
		endereco.setTelefone_fixo(835629566);
		endereco.setTipoEndereco(TipoEndereco.COMERCIAL);
		*/
		cliente.add(endereco);
		
		var assertThrows = assertThrows(ConstraintViolationException.class, ()->
				cadastraClienteService.persistir(cliente));
		
		assertEquals(9, assertThrows.getConstraintViolations().size());

		List<String> mensagens = assertThrows.getConstraintViolations()
			.stream()
			.map(ConstraintViolation::getMessage)
			.collect(Collectors.toList());

		assertThat(mensagens, hasItems("Campo rua n??o pode ser nulo",
				"Campo n??mero n??o pode ser nulo",
				"Campo bairro n??o pode ser nulo",
				"Campo cidade n??o pode ser nulo",
				"Campo UF n??o pode ser nulo",
				"Campo pa??s n??o pode ser nulo",
				"Campo cep n??o pode ser nulo",
				"Campo telefone fixo n??o pode ser nulo",
				"Campo tipo Endereco n??o pode ser nulo"));
		
	}

	@Test
	@DisplayName("Testa persist??ncia do cadastro do cliente")
	void test_dadosClientePreenchidos_clienteCadastrado() {

		assertNotNull(cadastraClienteService);
		
		ClienteVO cliente = new ClienteVO();
		cliente.setNome("Loki da Silva Oliveira");
		cliente.setCpf("05362695860");
		cliente.setRg("20556585221");
		cliente.setNascimento(LocalDate.now());
		cliente.setEmail("loki@gmail.com");
		cliente.setCelular(899554415l);
		cliente.setEnderecos(new ArrayList<EnderecoVO>());
		
		EnderecoVO endereco = new EnderecoVO();
		endereco.setId(123l);
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
		
		ClienteEntity clienteEntity = new ClienteEntity();
		clienteEntity.setId(1l);
		
		when(clienteRepository.save(any())).thenReturn(clienteEntity);
		
		cadastraClienteService.persistir(cliente);
		
		then(clienteRepository).should(times(1)).save(any());
		
	}
	
}
