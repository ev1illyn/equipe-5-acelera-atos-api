package net.atos.api.cliente.events;

import net.atos.api.cliente.domain.ClienteVO;

public class ClienteCreatedEvent {

	private ClienteVO clienteVO;

	public ClienteCreatedEvent(ClienteVO clienteVO) {
		super();
		this.clienteVO = clienteVO;
	}

	public ClienteVO getClienteVO() {
		return clienteVO;
	}
		
}
