package net.atos.api.cliente.events;

import net.atos.api.cliente.domain.ClienteVO;

public class ClienteDeletedEvent {

	private ClienteVO clienteVO;

	public ClienteDeletedEvent(ClienteVO clienteVO) {
		super();
		this.clienteVO = clienteVO;
	}

	public ClienteVO getClienteVO() {
		return clienteVO;
	}
	
}
