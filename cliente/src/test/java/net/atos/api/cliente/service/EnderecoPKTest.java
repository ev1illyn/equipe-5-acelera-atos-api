package net.atos.api.cliente.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import net.atos.api.cliente.repository.entity.ClienteEntity;
import net.atos.api.cliente.repository.entity.EnderecoPK;


@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class EnderecoPKTest {

	@Test
	@DisplayName("Testa método equals da classe, false")
	public void testaEquals_retornaFalse(){
		
		EnderecoPK enderecoPK = new EnderecoPK();
		enderecoPK.setCliente(new ClienteEntity());
		enderecoPK.setCodigoEndereco(1l);
		
		EnderecoPK enderecoPKNotEqual = new EnderecoPK();
		enderecoPKNotEqual.setCliente(new ClienteEntity());
		enderecoPKNotEqual.setCodigoEndereco(2l);
		
		var equals = enderecoPK.equals(enderecoPKNotEqual);
	
		assertFalse(equals);
		
	}
	

	@Test
	@DisplayName("Testa método equals da classe, true")
	public void testaEquals_retornaTrue(){
		
		EnderecoPK enderecoPK = new EnderecoPK();
		enderecoPK.setCliente(new ClienteEntity());
		enderecoPK.setCodigoEndereco(1l);
		
		EnderecoPK enderecoPKEqual = enderecoPK;
		
		var equals = enderecoPK.equals(enderecoPKEqual);
		assertTrue(equals);
		
	}
	
}
