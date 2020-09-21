package com.mitocode.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mitocode.document.Curso;
import com.mitocode.service.ICursoService;
import com.mitocode.validator.RequestValidator;

import reactor.core.publisher.Mono;

@Component
public class CursoHandler {

	@Autowired
	private ICursoService service;
	
	@Autowired
	private RequestValidator validatorGeneral;
	
	public Mono<ServerResponse> listar(ServerRequest request){
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(service.listar(),Curso.class);
	}
	
	public Mono<ServerResponse> listarPorId(ServerRequest request){
		String id = request.pathVariable("id");
		return service.listarPorId(id)
				.flatMap(p -> ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(fromValue(p))
				).switchIfEmpty(ServerResponse
						.notFound()
						.build());		
	}
	
	
	public Mono<ServerResponse> registrar(ServerRequest request){
		Mono<Curso> entityMono = request.bodyToMono(Curso.class);
		
		return  entityMono
				.flatMap(validatorGeneral::validate)
				.flatMap( service::registrar)
				.flatMap(p->ServerResponse.created(URI.create(request.uri().toString().concat("/").concat(p.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(fromValue(p)));
	}
	
	public Mono<ServerResponse> modificar(ServerRequest request){
		Mono<Curso> entityMono = request.bodyToMono(Curso.class);
		return  entityMono.flatMap( service::modificar)
				.flatMap(p->ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(fromValue(p))
				);
	}
	
	public Mono<ServerResponse> eliminar(ServerRequest request){
		String id = request.pathVariable("id");
		return service.listarPorId(id)
				.flatMap(p-> service.eliminar(p.getId())
				.then(ServerResponse
						.noContent()
						.build()
				)
			).switchIfEmpty(ServerResponse
						.notFound()
						.build()
			);
	}
	
}
