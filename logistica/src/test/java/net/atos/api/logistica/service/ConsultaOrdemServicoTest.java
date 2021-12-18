package net.atos.api.logistica.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.NotFoundException;

import org.hibernate.mapping.Array;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.atos.api.logistica.domain.ExcluiOrdemServicoVO;
import net.atos.api.logistica.domain.OrdemServicoVO;
import net.atos.api.logistica.repository.LogisticaRepository;
import net.atos.api.logistica.repository.entity.OrdemServicoEntity;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
class ConsultaOrdemServicoTest {

	private ConsultaOrdemServicoService consultaOrdemServicoService;
	
	private Validator validator;
	
	private LogisticaRepository logisticaRepository;
	
	private Pageable pageable;

	@BeforeAll
	@DisplayName("Teste executado antes de todos os testes")
	public void inicioGeral() {
		ValidatorFactory validatorFactor =
				Validation.buildDefaultValidatorFactory();
		
		this.validator = validatorFactor.getValidator();
	}
	
	@BeforeEach
	@DisplayName("Teste executado antes cada teste")
	public void iniciaCadaTeste() {
		
		this.logisticaRepository = Mockito.mock(LogisticaRepository.class);
		this.pageable = Mockito.mock(Pageable.class);
		 
		consultaOrdemServicoService = new ConsultaOrdemServicoService(this.logisticaRepository);
	}

	@Test
	@DisplayName("Testa Consulta de Ordem de Serviço sem dados")
	public void teste_OrdemDeServicoSemDados_lancaExcecao() {
		
		assertNotNull(consultaOrdemServicoService);
		
		Page<OrdemServicoEntity> pageOrdemVazio = Mockito.mock(Page.class);
		
		when(pageOrdemVazio.isEmpty()).thenReturn(Boolean.TRUE);
		
		when(this.logisticaRepository.findByDataNascimentoBetween(any(), any(), any()))
				.thenReturn(pageOrdemVazio);
 
		var exception = assertThrows(NotFoundException.class,()-> 
			consultaOrdemServicoService.porPeriodo(LocalDate.now(), LocalDate.now(), this.pageable));

		assertNotNull(exception);
	}
	
	@Test
	@DisplayName("Testa Consulta de Ordem de Serviço com dados")
	public void teste_OrdemDeServicoSemDados_retornaVOs() {
		
		assertNotNull(consultaOrdemServicoService);
		
		Stream<OrdemServicoEntity> entities = Stream.of(new OrdemServicoEntity(), new OrdemServicoEntity());
		
		Page<OrdemServicoEntity> pageOrdemComDados = Mockito.mock(Page.class);
		Pageable pageableMock = Mockito.mock(Pageable.class);
		
		when(pageOrdemComDados.stream()).thenReturn(entities);
		
		when(pageOrdemComDados.getPageable()).thenReturn(pageableMock);
		
		when(this.logisticaRepository.findByDataNascimentoBetween(any(), any(), any()))
				.thenReturn(pageOrdemComDados);
 
		Page<OrdemServicoVO> ordensEncontradas = consultaOrdemServicoService.porPeriodo(LocalDate.now(), LocalDate.now(), this.pageable);
		
		assertNotNull(ordensEncontradas);
		assertEquals(2, ordensEncontradas.getContent().size());
		
		then(this.logisticaRepository).should(times(1)).findByDataNascimentoBetween(any(), any(), any());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
