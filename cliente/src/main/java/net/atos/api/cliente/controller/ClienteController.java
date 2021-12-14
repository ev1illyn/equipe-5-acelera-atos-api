package net.atos.api.cliente.controller;

import java.net.URI;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import net.atos.api.cliente.domain.ClienteVO;
import net.atos.api.cliente.service.BuscaClienteService;
import net.atos.api.cliente.service.CadastraClienteService;
import net.atos.api.cliente.service.EditaClienteService;
import net.atos.api.cliente.service.ExcluiClienteService;

@RestController
@RequestMapping("/v1/clientes")
public class ClienteController {

	private CadastraClienteService cadastraClienteService;
	private BuscaClienteService buscaClienteService;
	private EditaClienteService editaClienteService;
	private ExcluiClienteService excluiClienteService;

	public ClienteController(
			CadastraClienteService cadastraClienteService, 
			BuscaClienteService buscaClienteService,
			EditaClienteService editaClienteService,
			ExcluiClienteService excluiClienteService) {
		super();
		this.cadastraClienteService = cadastraClienteService;
		this.buscaClienteService = buscaClienteService;
		this.editaClienteService = editaClienteService;
		this.excluiClienteService = excluiClienteService;
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
	
	@PatchMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON }, consumes = { MediaType.APPLICATION_JSON })
	public ResponseEntity<ClienteVO> editaCliente(@Valid @PathVariable("id") Long id,
			@Valid @RequestBody ClienteVO cliente) {
		
		ClienteVO clienteVO = editaClienteService.persistir(id, cliente);
		
		URI uri = MvcUriComponentsBuilder.fromController(getClass())
				.path("/v1/{id}")
				.buildAndExpand(clienteVO.getId())
				.toUri();
		
		return ResponseEntity.created(uri).body(clienteVO);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClienteVO> excluiCliente(@Valid @PathVariable("id") Long id) {
		
		ClienteVO clienteVO = excluiClienteService.remover(id);
		
		return ResponseEntity.ok(clienteVO);
		
	}

}
