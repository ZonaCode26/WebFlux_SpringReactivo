package com.mitocode.service;

import org.springframework.data.domain.Pageable;

import com.mitocode.pagination.PageSupport;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICRUD<T, ID> {

	Mono<T> registrar(T obj);
	Mono<T> modificar(T obj);
	Flux<T> listar();
	Mono<PageSupport<T>> listarPage(Pageable page);
	Mono<T> listarPorId(ID id);
	Mono<Void> eliminar(ID id);
}
