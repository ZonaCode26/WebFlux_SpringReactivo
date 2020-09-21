package com.mitocode;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.mitocode.handler.CursoHandler;
import com.mitocode.handler.EstudianteHandler;
import com.mitocode.handler.MatriculaHandler;
import com.mitocode.handler.PlatoHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
@Configuration
public class RouterConfig {

	@Bean
	public RouterFunction<ServerResponse> rutas(PlatoHandler handler) {
		return route(GET("/v3/platos"), handler::listar)// request -> handler.listar(request)
				.andRoute(GET("/v3/platos/{id}"), handler::listarPorId)
				.andRoute(POST("/v3/platos"), handler::registrar)
				.andRoute(PUT("/v3/platos"), handler::modificar)
				.andRoute(DELETE("/v3/platos/{id}"), handler::eliminar);

	}
	
	@Bean
	public RouterFunction<ServerResponse> rutasEstudiante(EstudianteHandler handler) {
		return route(GET("/v2/estudiantes"), handler::listar)// request -> handler.listar(request)
				.andRoute(GET("/v2/estudiantes/{id}"), handler::listarPorId)
				.andRoute(POST("/v2/estudiantes"), handler::registrar)
				.andRoute(PUT("/v2/estudiantes"), handler::modificar)
				.andRoute(DELETE("/v2/estudiantes/{id}"), handler::eliminar);

	}
	
	@Bean
	public RouterFunction<ServerResponse> rutasCurso(CursoHandler handler) {
		return route(GET("/v2/cursos"), handler::listar)// request -> handler.listar(request)
				.andRoute(GET("/v2/cursos/{id}"), handler::listarPorId)
				.andRoute(POST("/v2/cursos"), handler::registrar)
				.andRoute(PUT("/v2/cursos"), handler::modificar)
				.andRoute(DELETE("/v2/cursos/{id}"), handler::eliminar);

	}
	
	
	@Bean
	public RouterFunction<ServerResponse> rutasMatriculas(MatriculaHandler handler) {
		return route(GET("/v2/matriculas"), handler::listar)// request -> handler.listar(request)
				.andRoute(GET("/v2/matriculas/{id}"), handler::listarPorId)
				.andRoute(POST("/v2/matriculas"), handler::registrar)
				.andRoute(PUT("/v2/matriculas"), handler::modificar)
				.andRoute(DELETE("/v2/matriculas/{id}"), handler::eliminar)
				.andRoute(GET("/v3/matriculas"), handler::listarV3)
				.andRoute(GET("/v3/matriculas/{id}"), handler::listarPorIdV3);

	}
	
}
