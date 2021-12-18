package net.atos.api.logistica.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import net.atos.api.logistica.domain.ClienteVO;
import net.atos.api.logistica.domain.OrdemServicoVO;
import net.atos.api.logistica.service.CriaOrdemServicoService;

@Service
public class ClienteCadastradoListener {
	
	private CriaOrdemServicoService criaOrdemServicoService;
	
	public ClienteCadastradoListener(CriaOrdemServicoService criaOrdemServicoService) {
	
		this.criaOrdemServicoService = criaOrdemServicoService;
	}

	@RabbitListener(queues = "cria-ordem-servico")
	public void execute(ClienteVO clienteVO) {
		
		OrdemServicoVO ordemServico = new OrdemServicoVO();
		ordemServico.setIdCliente(clienteVO.getId());
		ordemServico.setDataNascimento(clienteVO.getNascimento());
		
		this.criaOrdemServicoService.processar(ordemServico);
		
	}

}
