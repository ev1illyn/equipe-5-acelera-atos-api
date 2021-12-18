package net.atos.api.logistica.factory;

import net.atos.api.logistica.domain.OrdemServicoVO;
import net.atos.api.logistica.repository.entity.OrdemServicoEntity;

public class OrdemServicoFactory {

	private OrdemServicoVO vo;
	private OrdemServicoEntity entity;
	
	public OrdemServicoFactory(OrdemServicoVO vo) {
		this.entity = this.transformaEntity(vo);
		this.vo = vo;
	}
	
	public OrdemServicoFactory(OrdemServicoEntity entity) {
		this.entity = entity;
		this.vo = this.transformaVO(entity);
	}
	
	private OrdemServicoVO transformaVO(OrdemServicoEntity entity) {
		
		OrdemServicoVO ordemServicoVO = new OrdemServicoVO();

		ordemServicoVO.setId(entity.getId());
		ordemServicoVO.setIdCliente(entity.getIdCliente());
		ordemServicoVO.setDataNascimento(entity.getDataNascimento());
				 
		return ordemServicoVO;
		
	}
	
	private OrdemServicoEntity transformaEntity(OrdemServicoVO ordemServico) {

		OrdemServicoEntity ordemServicoEntity = new OrdemServicoEntity();

		ordemServicoEntity.setId(ordemServico.getId());
		ordemServicoEntity.setIdCliente(ordemServico.getIdCliente());
		ordemServicoEntity.setDataNascimento(ordemServico.getDataNascimento());
		
		return ordemServicoEntity;
		
	}

	public OrdemServicoEntity toEntity() {
		return this.entity;
	}

	public OrdemServicoVO toVO() {
		return this.vo;
	}
	
}
