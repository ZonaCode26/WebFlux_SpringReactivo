package com.mitocode.service.impl;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitocode.document.Usuario;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IRolRepo;
import com.mitocode.repo.IUsuarioRepo;
import com.mitocode.security.User;
import com.mitocode.service.IUsuarioService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//clase 8
@Service
public class UsuarioServiceImpl  extends CRUDImpl<Usuario, String> implements IUsuarioService{

	@Autowired
	private IUsuarioRepo repo;
	
	@Autowired
	private IRolRepo rolRepo;

	@Override
	protected IGenericRepo<Usuario, String> getRepo(){
		return repo;
	}
	

	
	@Override
	public Mono<User> buscarPorUsuario(String usuario) {
	
		Mono<Usuario> monoUsuario = repo.findOneByUsuario(usuario);
		if (monoUsuario == null) {
			System.err.println("vacio");
		}else {
		}
		List<String> roles = new ArrayList<String>();
		
		return monoUsuario.flatMap(u -> {
			return Flux.fromIterable(u.getRoles())
					.flatMap(rol -> {System.err.println("entro**");
						return rolRepo.findById(rol.getId())
								.map(r -> {System.err.println(r.getNombre());
									roles.add(r.getNombre());
									return r;
								});
					}).collectList().flatMap(list -> {
						u.setRoles(list);
						return Mono.just(u);
					});
		})	
		.flatMap(u -> {			
			return Mono.just(new User(u.getUsuario(), u.getClave(), u.getEstado(), roles));
		});
	}


}
