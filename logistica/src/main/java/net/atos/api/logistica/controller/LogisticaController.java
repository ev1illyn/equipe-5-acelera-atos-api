package net.atos.api.logistica.controller;

import java.time.LocalDate;

import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import net.atos.api.logistica.config.PageableBinding;
import net.atos.api.logistica.domain.OrdemServicoVO;
import net.atos.api.logistica.service.ConsultaOrdemServicoService;

@RestController
@RequestMapping("/api/ordens-servicos")
public class LogisticaController {

	private ConsultaOrdemServicoService consultaOrdemServicoService;

	public LogisticaController(ConsultaOrdemServicoService consultaOrdemServicoService) {
		this.consultaOrdemServicoService = consultaOrdemServicoService;
	}
	
	@PageableBinding
	@GetMapping("/periodo-inicial/{inicio}/periodo-final/{fim}")
	@Operation(description = "Lista todos os clientes")
	public ResponseEntity<Page<OrdemServicoVO>> buscaOrdemPorNascimento(
			@PathVariable("inicio") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataInicial,
			@PathVariable("inicio") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dataFinal,
			@ParameterObject @PageableDefault(sort = {"dataNascimento"},
				direction = Direction.ASC, page = 0, size = 10) Pageable pageable) {
		
		Page<OrdemServicoVO> ordensEncontradas = consultaOrdemServicoService.porPeriodo(dataInicial, dataFinal, pageable);
		
		return ResponseEntity.ok(ordensEncontradas);

	}
	
}