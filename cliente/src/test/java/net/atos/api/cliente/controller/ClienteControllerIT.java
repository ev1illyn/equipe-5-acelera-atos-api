package net.atos.api.cliente.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.atos.api.cliente.domain.ClienteVO;
import net.atos.api.cliente.domain.EnderecoVO;
import net.atos.api.cliente.domain.TipoEndereco;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
public class ClienteControllerIT {

	private static final String URI_CLIENTES = "/v1/clientes";
	
	@Autowired
	private WebApplicationContext wac;

	//transforma objeto em json e vice-versa
	@Autowired
	private ObjectMapper mapper;
	
	//mockar
	private MockMvc mockMvc;
		
	@BeforeAll
	public void setup() {
		
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	@DisplayName("Edita cliente")
	public void test_editaCliente_retornoCriado() throws Exception {

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

		EnderecoVO enderecoRESIDENCIAL = new EnderecoVO();
		enderecoRESIDENCIAL.setRua("rua do anjos");
		enderecoRESIDENCIAL.setNumero("543");
		enderecoRESIDENCIAL.setBairro("Benjamin Franklin");
		enderecoRESIDENCIAL.setCidade("Cidade dos Anjos");
		enderecoRESIDENCIAL.setUF("LA");
		enderecoRESIDENCIAL.setPais("Estados Unidos");
		enderecoRESIDENCIAL.setCep(65625596);
		enderecoRESIDENCIAL.setTelefone_fixo(835229566);
		enderecoRESIDENCIAL.setTipoEndereco(TipoEndereco.RESIDENCIAL);
		
		cliente.add(enderecoRESIDENCIAL);	
		
		ResultActions resultCreated = this.mockMvc.perform(
    			MockMvcRequestBuilders.post(URI_CLIENTES)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(cliente))
    			).andDo(print())
    			.andExpect(status().isCreated());
    	
		ClienteVO clienteCadastrado = mapper.readValue(resultCreated
    						.andReturn()
    						.getResponse()
    						.getContentAsString(),
    						ClienteVO.class);
		
		clienteCadastrado.setNome(clienteCadastrado.getNome() + "cliente editado, " + clienteCadastrado.getId());
    	
		ResultActions resultEdited = this.mockMvc.perform(
    			MockMvcRequestBuilders.patch(URI_CLIENTES.concat("/{id}"),
    			clienteCadastrado.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(clienteCadastrado))
    			).andDo(print())
    			.andExpect(status().isCreated());
    	
    	ClienteVO clienteEditado = mapper.readValue(resultEdited
				.andReturn()
				.getResponse()
				.getContentAsString(),
				ClienteVO.class);

    	ResultActions resultConsulted = this.mockMvc.perform(
    			MockMvcRequestBuilders.get(URI_CLIENTES.concat("/{id}"),
    					clienteEditado.getId()))
    					.andDo(print())
    					.andExpect(status().isOk());
    	
    	ClienteVO clienteEditadoConsultada = mapper.readValue(resultConsulted
				.andReturn()
				.getResponse()
				.getContentAsString(),
				ClienteVO.class);

    	assertEquals(2, clienteEditadoConsultada.getEnderecos().size());

	}
	
	@Test
	@DisplayName("Cadastra cliente e busca cliente")
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

		EnderecoVO enderecoRESIDENCIAL = new EnderecoVO();
		enderecoRESIDENCIAL.setRua("rua do anjos");
		enderecoRESIDENCIAL.setNumero("543");
		enderecoRESIDENCIAL.setBairro("Benjamin Franklin");
		enderecoRESIDENCIAL.setCidade("Cidade dos Anjos");
		enderecoRESIDENCIAL.setUF("LA");
		enderecoRESIDENCIAL.setPais("Estados Unidos");
		enderecoRESIDENCIAL.setCep(65625596);
		enderecoRESIDENCIAL.setTelefone_fixo(835229566);
		enderecoRESIDENCIAL.setTipoEndereco(TipoEndereco.RESIDENCIAL);
		
		cliente.add(enderecoRESIDENCIAL);		
		
		ResultActions resultCreated = this.mockMvc.perform(
				MockMvcRequestBuilders.post(URI_CLIENTES)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(cliente))
				).andDo(print())
				.andExpect(status().isCreated());
		
		ClienteVO clienteCadastrado = mapper.readValue(resultCreated
				.andReturn()
				.getResponse()
				.getContentAsString(), 
				ClienteVO.class);
		
		ResultActions resultConsulted = this.mockMvc.perform(
				MockMvcRequestBuilders.get(URI_CLIENTES.concat("/{id}"),
						clienteCadastrado.getId()))
    					.andDo(print())
    					.andExpect(status().isOk());
	
		ClienteVO clienteConsultado = mapper.readValue(resultConsulted
				.andReturn()
				.getResponse()
				.getContentAsString(), 
				ClienteVO.class);
		
		assertEquals(2, clienteConsultado.getEnderecos().size());
	}
		
	@Test
	@DisplayName("Testa exclusão de cliente")
	public void test_envioCamposSemDadosEdicaoCliente_retornaOk() throws Exception{

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

		EnderecoVO enderecoRESIDENCIAL = new EnderecoVO();
		enderecoRESIDENCIAL.setRua("rua do anjos");
		enderecoRESIDENCIAL.setNumero("543");
		enderecoRESIDENCIAL.setBairro("Benjamin Franklin");
		enderecoRESIDENCIAL.setCidade("Cidade dos Anjos");
		enderecoRESIDENCIAL.setUF("LA");
		enderecoRESIDENCIAL.setPais("Estados Unidos");
		enderecoRESIDENCIAL.setCep(65625596);
		enderecoRESIDENCIAL.setTelefone_fixo(835229566);
		enderecoRESIDENCIAL.setTipoEndereco(TipoEndereco.RESIDENCIAL);
		
		cliente.add(enderecoRESIDENCIAL);		
		
		ResultActions resultCreated = this.mockMvc.perform(
				MockMvcRequestBuilders.post(URI_CLIENTES)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(cliente))
				).andDo(print())
				.andExpect(status().isCreated());
		
		ClienteVO clienteCadastrado = mapper.readValue(resultCreated
				.andReturn()
				.getResponse()
				.getContentAsString(), 
				ClienteVO.class);

		ResultActions resultDeleted = this.mockMvc.perform(
				MockMvcRequestBuilders.delete(URI_CLIENTES.concat("/{id}"),
					clienteCadastrado.getId()))
    				.andDo(print())
    				.andExpect(status().isOk());

		ClienteVO clienteDeletado = mapper.readValue(resultCreated
				.andReturn()
				.getResponse()
				.getContentAsString(), 
				ClienteVO.class);		
	}
	
	@Test
	@DisplayName("Envio de cliente sem os campos obrigatórios")
	public void test_envioCamposSemDadosCadastroCliente_retorna400() throws Exception {
		 
		ClienteVO cliente = new ClienteVO();
		
		/* mock performa um builder de requisição do tipo post com esses atributos que
		espera uma exceção do tipo bad request*/
		this.mockMvc.perform(
				MockMvcRequestBuilders.post(URI_CLIENTES)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(cliente))
				).andDo(print())
				.andExpect(status().isBadRequest());
	}
	
	@Test
	@DisplayName("Testa edição de cliente null")
	public void test_envioCamposSemDadosEdicaoCliente_retorna400() throws Exception {
		
		Long idTeste = 123l;
		ClienteVO cliente = null;
		
		this.mockMvc.perform(
    			MockMvcRequestBuilders.patch(URI_CLIENTES.concat("/{id}"),
    					idTeste)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(cliente))
    			).andDo(print())
    			.andExpect(status().isBadRequest());
		
	}

}