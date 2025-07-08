package com.curso.mcsv.usuarios.controllers;

import com.curso.mcsv.usuarios.models.entity.Usuario;
import com.curso.mcsv.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/findAll")
    public List<Usuario> findAll() {
        return usuarioService.findAll();
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.findById(id);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/createUser")
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario createUser(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@RequestBody Usuario usuario, @PathVariable Long id) {
        return usuarioService.findById(id)
                .map(u -> {
                    u.setNombre(usuario.getNombre());
                    u.setEmail(usuario.getEmail());
                    u.setPassword(usuario.getPassword());
                    return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(u));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return usuarioService.findById(id)
                .map(usuario -> {
                    usuarioService.deleteById(id);
                    return ResponseEntity.ok().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
