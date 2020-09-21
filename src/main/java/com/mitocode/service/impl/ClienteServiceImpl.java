package com.mitocode.service.impl;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.document.Cliente;
import com.mitocode.repo.IClienteRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.service.IClienteService;

import reactor.core.publisher.Flux;

@Service
public class ClienteServiceImpl extends CRUDImpl<Cliente, String> implements IClienteService{

	@Autowired
	private IClienteRepo repo;
	
	@Override
	protected IGenericRepo<Cliente, String> getRepo(){
		return repo;
	}

	@Override
	public Flux<Cliente> listarDemorado() {
		return repo.findAll().repeat(30).delayElements(Duration.ofSeconds(1));
	}

	@Override
	public Flux<Cliente> listarSobrecargado() {
		return repo.findAll().repeat(500);		
	}

	/*@Override
	public Mono<Cliente> registrar(Cliente p) {
		return repo.save(p);
	}

	@Override
	public Mono<Cliente> modificar(Cliente p) {
		return repo.save(p);
	}

	@Override
	public Flux<Cliente> listar() {
		//List<Cliente> lista = repo.findAll();
		//return Flux.fromIterable(lista);
		return repo.findAll();
	}

	@Override
	public Mono<Cliente> listarPorId(String id) {
		return repo.findById(id);
	}

	@Override
	public Mono<Void> eliminar(String id) {
		return repo.deleteById(id);
	}
	*/
	
}
