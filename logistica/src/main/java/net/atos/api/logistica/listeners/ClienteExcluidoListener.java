package net.atos.api.logistica.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import net.atos.api.logistica.domain.ClienteVO;
import net.atos.api.logistica.domain.ExcluiOrdemServicoVO;
import net.atos.api.logistica.service.ExcluiOrdemServicoService;

@Service
public class ClienteExcluidoListener {
	
	private ExcluiOrdemServicoService excluiOrdemServicoService;
	
	private ClienteExcluidoListener(ExcluiOrdemServicoService excluiOrdemServicoService) {
		this.excluiOrdemServicoService = excluiOrdemServicoService;
	}
	
	@RabbitListener(queues = "exclui-ordem-servico")
	public void execute(ClienteVO clienteVO) {
		
		ExcluiOrdemServicoVO excluiOrdemServicoVO = new ExcluiOrdemServicoVO();
		excluiOrdemServicoVO.setIdCliente(clienteVO.getId());
		
		this.excluiOrdemServicoService.processar(excluiOrdemServicoVO);
		
	}

}
