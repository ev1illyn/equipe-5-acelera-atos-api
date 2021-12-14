package net.atos.api.cliente.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import net.atos.api.cliente.domain.ClienteVO;
import net.atos.api.cliente.service.BuscaClienteService;
import net.atos.api.cliente.service.CadastraClienteService;

@RestController
@RequestMapping("/v1/clientes")
public class ClienteController {

	private CadastraClienteService cadastraClienteService;
	private BuscaClienteService buscaClienteService;

	public ClienteController(CadastraClienteService cadastraClienteService, BuscaClienteService buscaClienteService) {
		super();
		this.cadastraClienteService = cadastraClienteService;
		this.buscaClienteService = buscaClienteService;
	}
	
	@PostMapping(produces = {MediaType.APPLICATION_JSON}, consumes = {MediaType.APPLICATION_JSON})
	public ResponseEntity<ClienteVO> cadastraCliente(@Valid @RequestBody ClienteVO cliente) {
		
		ClienteVO clienteVO = cadastraClienteService.persistir(cliente);
		
		URI uri = MvcUriComponentsBuilder.fromController(getClass())
				.path("/v1/{id}")
				.buildAndExpand(clienteVO.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(clienteVO);
	}
	
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON })
	public ResponseEntity<ClienteVO> buscaClientePorId(@PathVariable("id") Long id){
		
		ClienteVO cliente = buscaClienteService.recuperarPorIdVO(id);
		return ResponseEntity.ok(cliente);
		
	}
	
}
