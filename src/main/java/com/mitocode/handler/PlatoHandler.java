package com.mitocode.handler;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mitocode.document.Plato;
import com.mitocode.service.IPlatoService;
import com.mitocode.validator.RequestValidator;

import reactor.core.publisher.Mono;

@Component
public class PlatoHandler {

	@Autowired
	private IPlatoService service;
	
	@Autowired
	private RequestValidator validadorGeneral;
	
	public Mono<ServerResponse> listar(ServerRequest request) {
		// TODO Auto-generated method stub
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).
				body(service.listar(), Plato.class);
	}
	
	public Mono<ServerResponse> listarPorId(ServerRequest request) {
		// TODO Auto-generated method stub
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
		Mono<Plato> platoMono = request.bodyToMono(Plato.class);
		
		/*return platoMono.flatMap(p -> {
		Errors errores = new BeanPropertyBindingResult(p, Plato.class.getName());
		validador.validate(p, errores);
		
		if(errores.hasErrors()) {
			return Flux.fromIterable(errores.getFieldErrors())
					.map(error -> new ValidacionDTO(error.getField(), error.getDefaultMessage()))						
					.collectList()
					.flatMap(listaErrores -> {							
						return ServerResponse.badRequest()
								.contentType(MediaType.APPLICATION_JSON)
								.body(fromValue(listaErrores));	
								}
							); 
		}else {
			return service.registrar(p)
					.flatMap(pdb -> ServerResponse
					.created(URI.create(req.uri().toString().concat("/").concat(p.getId())))
					.contentType(MediaType.APPLICATION_JSON)
					.body(fromValue(pdb))
					);
		}
		
	});*/
		
		return  platoMono
				.flatMap(validadorGeneral::validate)
				.flatMap( service::registrar)
				.flatMap(p->ServerResponse.created(URI.create(request.uri().toString().concat("/").concat(p.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(fromValue(p)));
	}
	
	public Mono<ServerResponse> modificar(ServerRequest request){
		Mono<Plato> platoMono = request.bodyToMono(Plato.class);
		return  platoMono.flatMap( service::modificar)
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
			//CORREGIDO (Ver en el video que el switchIfEmpty estaba dentro del bloque flatMap, debió estar fuera)
			).switchIfEmpty(ServerResponse
						.notFound()
						.build()
			);
	}
	
	
	
	
}
