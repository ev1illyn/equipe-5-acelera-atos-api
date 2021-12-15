package net.atos.api.cliente.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import net.atos.api.cliente.repository.entity.ClienteEntity;

@Repository
public interface ClienteRepository extends PagingAndSortingRepository<ClienteEntity, Long>{
	
	public Optional<ClienteEntity> findById(Long idCliente);

	public Page<ClienteEntity> findAll(Pageable pagination);
		
}
