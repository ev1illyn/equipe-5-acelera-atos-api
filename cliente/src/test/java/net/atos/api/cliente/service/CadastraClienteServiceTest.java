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

import net.atos.api.cliente.domain.ClienteVO;
import net.atos.api.cliente.domain.EnderecoVO;
import net.atos.api.cliente.domain.TipoEndereco;
import net.atos.api.cliente.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class CadastraClienteServiceTest {

	private CadastraClienteService cadastraCliente;
	
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
		
		cadastraCliente = new CadastraClienteService(validator, clienteRepository);
	}
		
	@Test
	@DisplayName("Testa obrigatoriedade dos campos de Cliente")
	void test_CamposObrigatoriosNulos_lancaExcecao() {
		
		assertNotNull(cadastraCliente);
		 
		ClienteVO cliente = new ClienteVO();
		
		/*cliente.setNome("Loki da Silva Oliveira");
		cliente.setCpf("05362695860");
		cliente.setRg("20556585221");
		cliente.setNascimento(LocalDate.now());
		cliente.setEmail("loki@gmail.com");
		cliente.setAtivo(true);
		cliente.setCelular(899554415l);
		cliente.setEnderecos(new ArrayList<EnderecoVO>());*/

		var assertThrows = assertThrows(ConstraintViolationException.class, ()->
							cadastraCliente.persistir(cliente));
		
		assertEquals(8, assertThrows.getConstraintViolations().size());
		
		List<String> mensagens = assertThrows.getConstraintViolations()
			.stream()
			.map(ConstraintViolation::getMessage)
			.collect(Collectors.toList());
		
		assertThat(mensagens, hasItems("Campo nome não pode ser nulo",
				"Campo cpf não pode ser nulo",
				"Campo rg não pode ser nulo",
				"Campo nascimento não pode ser nulo",
				"Campo email não pode ser nulo",
				"Campo ativo não pode ser nulo",
				"Campo celular não pode ser nulo",
				"Campo endereço não pode ser nulo"));
		
	}
	
	@Test
	@DisplayName("Testa se Cliente é nulo")
	void test_ClienteNulo_lancaExcecao() {
		
		assertNotNull(cadastraCliente);
		 
		ClienteVO cliente = null; 
		//ClienteVO cliente = new ClienteVO(); 

		var assertThrows = assertThrows(IllegalArgumentException.class, ()->
							cadastraCliente.persistir(cliente));
		
		assertNotNull(assertThrows);
		
	}
	
	@Test
	@DisplayName("Testa obrigatoriedade de um endereço")
	void test_UmEndereco_lancaExcecao() {
		
		assertNotNull(cadastraCliente);
		
		ClienteVO cliente = new ClienteVO();

		cliente.setNome("Loki da Silva Oliveira");
		cliente.setCpf("05362695860");
		cliente.setRg("20556585221");
		cliente.setNascimento(LocalDate.now());
		cliente.setEmail("loki@gmail.com");
		cliente.setAtivo(true);
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
				cadastraCliente.persistir(cliente));
		
		assertEquals(1, assertThrows.getConstraintViolations().size());

		List<String> mensagens = assertThrows.getConstraintViolations()
			.stream()
			.map(ConstraintViolation::getMessage)
			.collect(Collectors.toList());

		assertThat(mensagens, hasItems("Campo endereço deve ter pelo menos um item"));
		
	}

	@Test
	@DisplayName("Testa obrigatoriedade dos campos de um endereço")
	void test_enderecoComCamposNulos_lancaExcecao() {
		
		assertNotNull(cadastraCliente);
		
		ClienteVO cliente = new ClienteVO();
		
		cliente.setNome("Loki da Silva Oliveira");
		cliente.setCpf("05362695860");
		cliente.setRg("20556585221");
		cliente.setNascimento(LocalDate.now());
		cliente.setEmail("loki@gmail.com");
		cliente.setAtivo(true);
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
				cadastraCliente.persistir(cliente));
		
		assertEquals(9, assertThrows.getConstraintViolations().size());

		List<String> mensagens = assertThrows.getConstraintViolations()
			.stream()
			.map(ConstraintViolation::getMessage)
			.collect(Collectors.toList());

		assertThat(mensagens, hasItems("Campo rua não pode ser nulo",
				"Campo número não pode ser nulo",
				"Campo bairro não pode ser nulo",
				"Campo cidade não pode ser nulo",
				"Campo UF não pode ser nulo",
				"Campo país não pode ser nulo",
				"Campo cep não pode ser nulo",
				"Campo telefone fixo não pode ser nulo",
				"Campo tipo Endereco não pode ser nulo"));
		
	}

	@Test
	@DisplayName("Testa persistência do cliente")
	void test_dadosClientePreenchidos_clienteCadastrado() {

		assertNotNull(cadastraCliente);
		
		ClienteVO cliente = new ClienteVO();
		
		cliente.setNome("Loki da Silva Oliveira");
		cliente.setCpf("05362695860");
		cliente.setRg("20556585221");
		cliente.setNascimento(LocalDate.now());
		cliente.setEmail("loki@gmail.com");
		cliente.setAtivo(true);
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
		
		cadastraCliente.persistir(cliente);
		
	}

}