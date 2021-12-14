package net.atos.api.cliente.service;

import javax.validation.Validator;
import javax.ws.rs.NotFoundException;

import com.sun.jdi.LongValue;
import net.atos.api.cliente.repository.entity.ClienteEntity;
import org.springframework.stereotype.Service;

import net.atos.api.cliente.domain.ClienteVO;
import net.atos.api.cliente.repository.ClienteRepository;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class ExcluiClienteService {

	private Validator validator;
	private ClienteRepository clienteRepository;
	private BuscaClienteService buscaClienteService;

	public ExcluiClienteService(Validator validator, ClienteRepository clienteRepository,
								BuscaClienteService buscaClienteService) {
		this.validator = validator;
		this.clienteRepository = clienteRepository;
		this.buscaClienteService = buscaClienteService;
	}

	public void excluir(Long id) throws Exception {


		ClienteEntity clienteEcontrado =
				this.buscaClienteService.recuperarPorId(id);


		long idRecebido = clienteEcontrado.getId();

		if ((idRecebido == id))  {
			this.clienteRepository.deleteById(idRecebido);

			ClienteEntity teste = this.buscaClienteService.recuperarPorId(id);
			teste.getId();

			throw new Exception("Deletado");

			}
		}


	}

