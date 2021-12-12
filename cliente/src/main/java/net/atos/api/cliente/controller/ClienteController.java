package net.atos.api.cliente.controller;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.atos.api.cliente.domain.ClienteVO;
import net.atos.api.cliente.service.CadastraClienteService;

@RestController
@RequestMapping("/v1/clientes")
public class ClienteController {

	private CadastraClienteService cadastraClienteService;

	public ClienteController(CadastraClienteService cadastraClienteService) {
		super();
		this.cadastraClienteService = cadastraClienteService;
	}
	
	@PostMapping(produces = {MediaType.APPLICATION_JSON}, consumes = {MediaType.APPLICATION_JSON})
	public ResponseEntity<ClienteVO> cadastraCliente(@Valid @RequestBody ClienteVO cliente) {
		
		ClienteVO clienteVO = cadastraClienteService.persistir(cliente);
		
		return ResponseEntity.ok().body(cliente);
	}
	
}
