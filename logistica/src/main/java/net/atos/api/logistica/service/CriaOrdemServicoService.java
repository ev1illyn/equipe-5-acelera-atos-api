package net.atos.api.logistica.service;

import java.time.LocalDate;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.stereotype.Service;

import net.atos.api.logistica.domain.OrdemServicoVO;
import net.atos.api.logistica.factory.OrdemServicoFactory;
import net.atos.api.logistica.repository.LogisticaRepository;
import net.atos.api.logistica.repository.entity.OrdemServicoEntity;

@Service
public class CriaOrdemServicoService {
	
	private Validator validator;

	private LogisticaRepository logisticaRepository;
	
	public CriaOrdemServicoService(Validator validator, LogisticaRepository logisticaRepository) {
		this.validator = validator;
		this.logisticaRepository = logisticaRepository;
	}

	@Transactional
	public OrdemServicoVO processar(OrdemServicoVO ordemServicoVO) {
		
		Set<ConstraintViolation<OrdemServicoVO>>
		validate = this.validator.validate(ordemServicoVO);
		
		if(!validate.isEmpty()) {
			throw new ConstraintViolationException("Ordem de Serviço Inválida", validate);
		}
		
		OrdemServicoEntity ordemServicoEntity = new OrdemServicoFactory(ordemServicoVO).toEntity();
		
		ordemServicoEntity.setDataEvento(LocalDate.now());
		
		ordemServicoEntity = this.logisticaRepository.save(ordemServicoEntity);
		
		ordemServicoVO.setId(ordemServicoEntity.getId()); 
		
		return ordemServicoVO;
	}

}
