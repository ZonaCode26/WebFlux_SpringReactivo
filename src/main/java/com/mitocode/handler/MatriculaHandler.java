package com.mitocode.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mitocode.document.Matricula;
import com.mitocode.service.ICursoService;
import com.mitocode.service.IEstudianteService;
import com.mitocode.service.IMatriculaService;
import com.mitocode.validator.RequestValidator;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MatriculaHandler {

	@Autowired
	private IMatriculaService service;
	
	@Autowired
	private IEstudianteService studentService;
	
	@Autowired
	private ICursoService cursoService;
	
	@Autowired
	private RequestValidator validatorGeneral;
	
	public Mono<ServerResponse> listar(ServerRequest request){
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(service.listar(),Matricula.class);
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
		Mono<Matricula> entityMono = request.bodyToMono(Matricula.class);
		
		return  entityMono
				.flatMap(validatorGeneral::validate)
				.flatMap( service::registrar)
				.flatMap(p->ServerResponse.created(URI.create(request.uri().toString().concat("/").concat(p.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(fromValue(p)));
	}
	
	public Mono<ServerResponse> modificar(ServerRequest request){
		Mono<Matricula> entityMono = request.bodyToMono(Matricula.class);
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
	
	
	// v3
	
	public Mono<ServerResponse> listarV3(ServerRequest request){
		
		Flux<Matricula> flxEntity = service.listar()
				.flatMap(matricula->{
					return Mono.just(matricula)
							.zipWith(studentService.listarPorId(matricula.getEstudiante().getId()),(matEntity,estudiante)->{
								matEntity.setEstudiante(estudiante);
								return matEntity;
							});
				})
				.flatMap(matricula->{
					return Flux.fromIterable(matricula.getCursos()).flatMap(curso->{
						return cursoService.listarPorId(curso.getId());
					}).collectList().flatMap(list->{
						matricula.setCursos(list);
						return Mono.just(matricula);
					});
				});
		
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
				.body(flxEntity,Matricula.class);
	}
	
	public Mono<ServerResponse> listarPorIdV3(ServerRequest request){
		String id = request.pathVariable("id");
		
		return service.listarPorId(id)
				.flatMap( matricula -> {
					return Mono.just(matricula)
							.zipWith(studentService.listarPorId(matricula.getEstudiante().getId()),(matEntity, estudiante)->{
								matEntity.setEstudiante(estudiante);
								return matEntity;
							});
				})
				.flatMap(matricula->{
					return Flux.fromIterable(matricula.getCursos()).flatMap(curso->{
						return cursoService.listarPorId(curso.getId());
					}).collectList().flatMap(list->{
						matricula.setCursos(list);
						return Mono.just(matricula);
					});
				})
				.flatMap(matricula -> ServerResponse
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(fromValue(matricula)))
				.switchIfEmpty(ServerResponse
						.notFound()
						.build());
		
	}
}
