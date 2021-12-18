package net.atos.api.cliente.events;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import net.atos.api.cliente.domain.ClienteVO;

@Service
public class ClienteEventProcess {
	
	private RabbitTemplate rabbitTemplate;
	
	public ClienteEventProcess(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	@Async
	@TransactionalEventListener
	public void handleEvent(ClienteCreatedEvent event) {
		
		ClienteVO clienteVO = event.getClienteVO();
		
		this.rabbitTemplate.convertAndSend("cliente",
				"cliente.created", clienteVO);
		
	}
	
	@Async
	@TransactionalEventListener
	public void handleEvent(ClienteDeletedEvent event) {
		
		ClienteVO clienteVO = event.getClienteVO();
		
		this.rabbitTemplate.convertAndSend("cliente",
				"cliente.deleted", clienteVO);
		
	}

}
