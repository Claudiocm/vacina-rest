package br.com.imuniza.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ConfigSwagger {
	private final ResponseMessage msg201 = simpleMessage(201,"Recurso criado");
	private final ResponseMessage msg204put = simpleMessage(204,"Atualizado Ok");
	private final ResponseMessage msg204delete = simpleMessage(204,"Detetado Ok");
	private final ResponseMessage msg403 = simpleMessage(403,"Não autorizado");
	private final ResponseMessage msg404 = simpleMessage(404,"Nao encontrado");
	private final ResponseMessage msg422 = simpleMessage(422,"Erro de validação");
	private final ResponseMessage msg500 = simpleMessage(500,"Erro inesperado");
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, Arrays.asList(msg403,msg404, msg500))
				.globalResponseMessage(RequestMethod.POST, Arrays.asList(msg201, msg403, msg422, msg500))
				.globalResponseMessage(RequestMethod.PUT, Arrays.asList(msg204put, msg403, msg404, msg422, msg500))
				.globalResponseMessage(RequestMethod.DELETE, Arrays.asList(msg204delete, msg403,msg404, msg500))
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.imuniza.controller")).paths(PathSelectors.any())
				.build().apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
		return  new ApiInfo(
				"Api projeto Busca Vacina",
				"Este projeto tem por finalidade ajudar os usuários  que busca  orientaçãoes quanto a imunização",
				"Versão: 1.0.2", "",
				new Contact("Claudio Costa Matos","", "claudio.c.matos@gmail.com"),
				"Permitido o uso para estudos",
				"",
				Collections.emptyList()
				);
	}
	
	private ResponseMessage simpleMessage(int code, String msg) {
		return new ResponseMessageBuilder().code(code).message(msg).build();
	}

}
