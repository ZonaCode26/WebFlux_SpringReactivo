package com.mitocode.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.document.Matricula;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IMatriculaRepo;
import com.mitocode.service.IMatriculaService;

import reactor.core.publisher.Mono;

@Service
public class MatriculaServiceImpl extends CRUDImpl<Matricula, String> implements IMatriculaService{

	@Autowired
	private IMatriculaRepo repo;
	
	@Override
	protected IGenericRepo<Matricula, String> getRepo() {
		// TODO Auto-generated method stub
		return repo;
	}

	@Override
	public Mono<Matricula> registrarTransaccional(Matricula f) {
		// TODO Auto-generated method stub
		return null;
	}

}
