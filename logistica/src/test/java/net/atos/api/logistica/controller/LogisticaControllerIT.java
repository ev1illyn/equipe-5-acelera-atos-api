package net.atos.api.logistica.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
public class LogisticaControllerIT {

	private static final String URI_ORDENS_SERVICOS = "/api/ordens-servicos";
	
	@Autowired
	private WebApplicationContext wac;

	//transforma objeto em json e vice-versa
	@Autowired
	private ObjectMapper mapper;
	
	//mockar
	private MockMvc mockMvc;
	
	@BeforeAll
	@DisplayName("Teste executado antes de todos os testes")
	public void setup() {
		
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	
	}	

	@Test
	@DisplayName("Consulta ordem de serviço sem dados obrigatórios")
	public void test_consultaOrdemDeServicoSemDados_retorna400() throws Exception {
		
		String dataInicio = LocalDate.now().minusDays(1l).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		String dataFim = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
	
		this.mockMvc.perform(
    			MockMvcRequestBuilders.get(URI_ORDENS_SERVICOS
    					.concat("/periodo-inicial/{inicio}/periodo-final/{fim}"),
    					dataInicio, dataFim))
    					.andDo(print())
    					//.andExpect(status().isNotFound());
    					.andExpect(status().isBadRequest());
		
	}
}
