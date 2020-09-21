package com.mitocode.service;

import com.mitocode.document.Factura;

import reactor.core.publisher.Mono;

public interface IFacturaService extends ICRUD<Factura, String>{

	Mono<Factura> registrarTransaccional(Factura f);
}
