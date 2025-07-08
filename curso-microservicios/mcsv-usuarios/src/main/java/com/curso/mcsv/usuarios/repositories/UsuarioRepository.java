package com.curso.mcsv.usuarios.repositories;

import com.curso.mcsv.usuarios.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
}
