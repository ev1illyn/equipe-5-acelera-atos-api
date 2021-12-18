package net.atos.api.logistica.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExcluiOrdemQueueConfig {

	@Bean
	public Queue excluiOrdemQueue() {
		return QueueBuilder
				.durable("exclui-ordem-servico")
				.deadLetterExchange("cliente")
				.deadLetterRoutingKey("cliente.deleted-dead-letter")
				.build();
	}
	
	@Bean
	public Exchange excluiOrdemExchange() {
		return ExchangeBuilder
				.topicExchange("cliente")
				.durable(true)
				.build();
	}
	
	// bind da queue excluiOrdemQueue com exchange
	@Bean
	public Binding excluiOrdemBindind() {
		return BindingBuilder
				.bind(this.excluiOrdemQueue())
				.to(this.excluiOrdemExchange())
				.with("cliente.deleted")
				.noargs();
	}
	
	@Bean
	public Queue excluiOrdemQueueDeadLetter() {
		return QueueBuilder
				.durable("exclui-ordem-servico-deadletter")
				.build();
	}

	// bind da excluiOrdemQueueDeadLetter com exchange
	@Bean
	public Binding excluiOrdemDeadLetterBindind() {
		return BindingBuilder
				.bind(this.excluiOrdemQueueDeadLetter())
				.to(this.excluiOrdemExchange())
				.with("cliente.deleted-dead-letter")
				.noargs();
	}
	
}
