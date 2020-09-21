package com.mitocode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.document.Plato;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IPlatoRepo;
import com.mitocode.service.IPlatoService;

import reactor.core.publisher.Flux;

@Service
public class PlatoServiceImpl extends CRUDImpl<Plato, String> implements IPlatoService{

	@Autowired
	private IPlatoRepo repo;
	
	@Override
	protected IGenericRepo<Plato, String> getRepo(){
		return repo;
	}
	
	@Override
	public Flux<Plato> buscarPorNombre(String nombre) {
		return repo.findByNombre(nombre);
	}

	/*@Override
	public Mono<Plato> registrar(Plato p) {
		return repo.save(p);
	}

	@Override
	public Mono<Plato> modificar(Plato p) {
		return repo.save(p);
	}

	@Override
	public Flux<Plato> listar() {
		//List<Plato> lista = repo.findAll();
		//return Flux.fromIterable(lista);
		return repo.findAll();
	}

	@Override
	public Mono<Plato> listarPorId(String id) {
		return repo.findById(id);
	}

	@Override
	public Mono<Void> eliminar(String id) {
		return repo.deleteById(id);
	}
	
	*/
}
