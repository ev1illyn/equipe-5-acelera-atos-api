package net.atos.api.logistica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.atos.api.logistica.domain.ExcluiOrdemServicoVO;
import net.atos.api.logistica.domain.OrdemServicoVO;
import net.atos.api.logistica.repository.LogisticaRepository;
import net.atos.api.logistica.repository.entity.OrdemServicoEntity;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class ExcluiOrdemServicoServiceTest {

	private ExcluiOrdemServicoService excluiOrdemServicoService;
	
	private LogisticaRepository logisticaRepository;
	
	private Validator validator;
	
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
		 
		excluiOrdemServicoService = new ExcluiOrdemServicoService(this.validator, this.logisticaRepository);
	}
 
	@Test
	@DisplayName("Testa se Ordem de Serviço é nula ao excluir")
	public void teste_OrdemDeServicoNulo_lancaExcecao() {
		
		assertNotNull(excluiOrdemServicoService);
 
		ExcluiOrdemServicoVO excluiOrdemServicoVO = new ExcluiOrdemServicoVO();
		
		var exception = assertThrows(ConstraintViolationException.class,()-> 
		excluiOrdemServicoService.processar(excluiOrdemServicoVO));
						
		assertEquals(1, exception.getConstraintViolations().size());
		
		List<String> mensagens = exception.getConstraintViolations()
				.stream()
				.map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());
		
		assertThat(mensagens, hasItems("Campo id do cliente não pode ser nulo"));
	}
	
	@Test
	@DisplayName("Testa Cliente não encontrado")
	public void teste_ClienteNaoEncontradoOrdemDeServico_lancaExcecao() {
		
		ExcluiOrdemServicoVO excluiOrdemServicoVO = new ExcluiOrdemServicoVO();
		excluiOrdemServicoVO.setIdCliente(1l);
		
		when(this.logisticaRepository.findByIdCliente(anyLong()))
			.thenReturn(Optional.empty());
		
		var exception = assertThrows(NotFoundException.class, 
				()-> excluiOrdemServicoService.processar(excluiOrdemServicoVO));
		
		assertNotNull(exception);
		
		then(this.logisticaRepository).should(times(1)).findByIdCliente(anyLong());
	}
	

	@Test
	@DisplayName("Testa exclusão da ordem de servico")
	public void teste_ExclusaoOrdemDeServico_lancaExcecao() {
		
		ExcluiOrdemServicoVO excluiOrdemServicoVO = new ExcluiOrdemServicoVO();
		excluiOrdemServicoVO.setIdCliente(1l);
		
		OrdemServicoEntity ordemServicoEncontrada = new OrdemServicoEntity();
		ordemServicoEncontrada.setId(1l);
		
		when(this.logisticaRepository.findByIdCliente(anyLong()))
			.thenReturn(Optional.of(ordemServicoEncontrada));
		
        OrdemServicoVO ordemCancelado = excluiOrdemServicoService.processar(excluiOrdemServicoVO);
		
        assertNotNull(ordemCancelado);
        assertEquals(1l, ordemCancelado.getId());
        
		//then(this.logisticaRepository).should(times(1)).findByIdCliente(anyLong());
		//then(this.logisticaRepository).should(times(1)).delete(any());
	}
	
}
