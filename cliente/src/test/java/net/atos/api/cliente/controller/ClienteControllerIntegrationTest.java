package net.atos.api.cliente.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import javax.ws.rs.core.MediaType;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.atos.api.cliente.domain.ClienteVO;
import net.atos.api.cliente.domain.EnderecoVO;
import net.atos.api.cliente.domain.TipoEndereco;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
class ClienteControllerIntegrationTest {

	private static final String URI_NOTA_FISCAL = "/v1/clientes";
	
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	private ObjectMapper mapper;
	
	private MockMvc mockMvc;
	
	@BeforeAll
	public void setup() {
		
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	@DisplayName("Cadastra cliente")
	public void test_cadastraCliente_retornoCriado() throws Exception {
		
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
		
		this.mockMvc.perform(
				MockMvcRequestBuilders.post(URI_NOTA_FISCAL)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(cliente))
				).andDo(print())
				.andExpect(status().isCreated());
	}
	
	@Test
	@DisplayName("Envio de nota fiscal sem os campos obrigatórios")
	public void test_envioCamposSemDadosCadastroCliente_retorna400() throws Exception {
		 
		ClienteVO cliente = new ClienteVO();
		
		this.mockMvc.perform(
				MockMvcRequestBuilders.post(URI_NOTA_FISCAL)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(cliente))
				).andDo(print())
				.andExpect(status().isBadRequest());
	}

}