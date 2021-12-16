package net.atos.api.cliente.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Parameters({
	@Parameter(name = "page", required = false, example = "123"),
	@Parameter(name = "size",  required = false, example = "123"),
        })
public @interface PageableBinding {

}