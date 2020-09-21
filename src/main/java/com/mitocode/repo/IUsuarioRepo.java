package com.mitocode.repo;

import com.mitocode.document.Usuario;

import reactor.core.publisher.Mono;

public interface IUsuarioRepo extends IGenericRepo<Usuario, String>{

	Mono<Usuario> findOneByUsuario(String usuario);
}
