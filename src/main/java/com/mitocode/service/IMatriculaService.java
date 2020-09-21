package com.mitocode.service;

import com.mitocode.document.Matricula;

import reactor.core.publisher.Mono;


public interface IMatriculaService extends ICRUD<Matricula, String>{

	Mono<Matricula> registrarTransaccional(Matricula f);
}
