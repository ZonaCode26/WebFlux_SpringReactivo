package com.mitocode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;

import com.mitocode.document.Factura;
import com.mitocode.document.Plato;
import com.mitocode.repo.IFacturaRepo;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IPlatoRepo;
import com.mitocode.service.IFacturaService;

import reactor.core.publisher.Mono;

@Service
public class FacturaServiceImpl extends CRUDImpl<Factura, String> implements IFacturaService{

	@Autowired
	private IFacturaRepo repo;
	
	@Autowired
	private IPlatoRepo pRepo;
	
//	@Autowired
//	private TransactionalOperator txo;
	
	@Override
	protected IGenericRepo<Factura, String> getRepo() {
		return repo; 
	}
	
	@Override
	public Mono<Factura> registrarTransaccional(Factura f){
		Plato p = new Plato();		
		p.setEstado(true);
		p.setNombre("CHAUFA");
		p.setPrecio(25.0);
		
		Plato p2 = new Plato();		
		p2.setEstado(true);
		p2.setNombre("CECINA");
		p2.setPrecio(27.0);
		
//		return this.txo.execute(status -> pRepo.save(p)).then(pRepo.save(p2)).then(repo.save(f));
		return  Mono.empty();
	}
	
}
