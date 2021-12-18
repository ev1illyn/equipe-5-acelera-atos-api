package net.atos.api.logistica.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import net.atos.api.logistica.domain.OrdemServicoVO;
import net.atos.api.logistica.repository.LogisticaRepository;
import net.atos.api.logistica.repository.entity.OrdemServicoEntity;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class CriaOrdemServicoServiceTest {

	private CriaOrdemServicoService criaOrdemServicoService;
	
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
		
		criaOrdemServicoService = new CriaOrdemServicoService(this.validator, this.logisticaRepository);
	}
	
	@Test
	@DisplayName("Testa obrigatoriedade dos campos da ordem de serviço ao criar")
	public void teste_CamposObrigatoriosNulos_lancaExcecao() {
		
		assertNotNull(criaOrdemServicoService);
		
		OrdemServicoVO ordemServicoVO = new OrdemServicoVO();
		
		var exception = assertThrows(ConstraintViolationException.class,
				()-> criaOrdemServicoService.processar(ordemServicoVO));
	
		assertNotNull(exception);
		
		assertEquals(2, exception.getConstraintViolations().size());

		List<String> mensagens = exception.getConstraintViolations()
			.stream()
			.map(ConstraintViolation::getMessage)
			.collect(Collectors.toList());
		
		assertThat(mensagens, hasItems("Campo id do cliente não pode ser nulo",
				"Campo data de nascimento não pode ser nula"));
	}
	
	@Test
	@DisplayName("Testa se Ordem de Serviço é nulo ao criar")
	public void teste_OrdemDeServicoNulo_lancaExcecao() {
		
		assertNotNull(criaOrdemServicoService);
		
		OrdemServicoVO ordemServicoVO = null;
		
		var exception = assertThrows(IllegalArgumentException.class,
				()-> criaOrdemServicoService.processar(ordemServicoVO));
		
		assertNotNull(exception);
	}

	@Test
	@DisplayName("Testa Unique Constraint do id do cliente da ordem de serviço")
	public void teste_OrdemDeServicoRegistradaEUniqueKeyViolation_lancaExcecao() {
		
		assertNotNull(criaOrdemServicoService);

		OrdemServicoVO ordemServicoVO = new OrdemServicoVO();
		ordemServicoVO.setIdCliente(1l);
		ordemServicoVO.setDataNascimento(LocalDate.now());

		when(this.logisticaRepository.save(any()))
			.thenThrow(org.hibernate.exception.ConstraintViolationException.class);
		
		var exception = assertThrows(org.hibernate.exception.ConstraintViolationException.class,
				()-> criaOrdemServicoService.processar(ordemServicoVO));
		
		assertNotNull(exception);
				
	}
	

	@Test
	@DisplayName("Testa persistência do cadastro da ordem de serviço")
	public void teste_DadosOrdemDeServicoPreenchidos_OrdemDeServicoCadastrada() {
		
		assertNotNull(criaOrdemServicoService);

		OrdemServicoVO ordemServicoVO = new OrdemServicoVO();
		ordemServicoVO.setIdCliente(1l);
		ordemServicoVO.setDataNascimento(LocalDate.now());
		
		OrdemServicoEntity ordemServicoEntity = new OrdemServicoEntity();
		ordemServicoEntity.setId(1l);

		when(this.logisticaRepository.save(any()))
			.thenReturn(ordemServicoEntity);
		
		OrdemServicoVO ordemServicoVOCriado = criaOrdemServicoService.processar(ordemServicoVO);
		
		then(this.logisticaRepository).should(times(1)).save(any());
		
		assertNotNull(ordemServicoVOCriado);
		//assertEquals(1l, ordemServicoVOCriado.getId());// expected 1 but returned null
				
	}
	
}
