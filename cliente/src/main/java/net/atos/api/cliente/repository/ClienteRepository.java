package net.atos.api.cliente.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.atos.api.cliente.repository.entity.ClienteEntity;

@Repository
public interface ClienteRepository extends CrudRepository<ClienteEntity, Long>{
	
	public Optional<ClienteEntity> findById(Long idCliente);
	
}
