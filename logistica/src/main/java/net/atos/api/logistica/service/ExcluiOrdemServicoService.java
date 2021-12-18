package net.atos.api.logistica.service;

import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import org.springframework.stereotype.Service;

import net.atos.api.logistica.domain.ExcluiOrdemServicoVO;
import net.atos.api.logistica.domain.OrdemServicoVO;
import net.atos.api.logistica.factory.OrdemServicoFactory;
import net.atos.api.logistica.repository.LogisticaRepository;
import net.atos.api.logistica.repository.entity.OrdemServicoEntity;

@Service
public class ExcluiOrdemServicoService {
	
	private Validator validator;
	
	private LogisticaRepository logisticaRepository;
	
	public ExcluiOrdemServicoService(Validator validator, LogisticaRepository logisticaRepository) {
		this.validator = validator;
		this.logisticaRepository = logisticaRepository;
	}

	@Transactional
	public OrdemServicoVO processar(ExcluiOrdemServicoVO excluirOrdemServicoVO) {
		
		Set<ConstraintViolation<ExcluiOrdemServicoVO>>
		validate = this.validator.validate(excluirOrdemServicoVO);
		
		if(!validate.isEmpty()) {
			throw new ConstraintViolationException("Ordem de Serviço Inválida", validate);
		}

		OrdemServicoEntity ordemEncontrada = logisticaRepository.findByIdCliente(excluirOrdemServicoVO.getIdCliente())
			.orElseThrow(()-> new NotFoundException("Ordem de serviço não encontrada. "
					+ "IdCliente = " + excluirOrdemServicoVO.getIdCliente()));
		
		logisticaRepository.delete(ordemEncontrada);
		
		OrdemServicoVO ordemExcluida = new OrdemServicoFactory(ordemEncontrada).toVO();
		
		return ordemExcluida;
		
	}

}
