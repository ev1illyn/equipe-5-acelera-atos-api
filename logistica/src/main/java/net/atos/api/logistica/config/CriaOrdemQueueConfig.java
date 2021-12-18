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
public class CriaOrdemQueueConfig {
	
	@Bean
	public Queue criaOrdemQueue() {
		return QueueBuilder
				.durable("cria-ordem-servico")
				.deadLetterExchange("cliente")
				.deadLetterRoutingKey("cliente.created-dead-letter")
				.build();
	}
	
	@Bean
	public Exchange criaOrdemExchange() {
		return ExchangeBuilder
				.topicExchange("cliente")
				.durable(true)
				.build();
	}
	
	// bind da queue criaOrdemQueue com exchange
	@Bean
	public Binding criaOrdemBindind() {
		return BindingBuilder
				.bind(this.criaOrdemQueue())
				.to(this.criaOrdemExchange())
				.with("cliente.created")
				.noargs();
	}
	
	@Bean
	public Queue criaOrdemQueueDeadLetter() {
		return QueueBuilder
				.durable("cria-ordem-servico-deadletter")
				.build();
	}

	// bind da criaOrdemQueueDeadLetter com exchange
	@Bean
	public Binding criaOrdemDeadLetterBindind() {
		return BindingBuilder
				.bind(this.criaOrdemQueueDeadLetter())
				.to(this.criaOrdemExchange())
				.with("cliente.created-dead-letter")
				.noargs();
	}
	
}
