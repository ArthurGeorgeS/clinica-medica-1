package com.clinica.controller;
import com.clinica.model.Base;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** CRUD genérico: GET (lista/um), POST, PUT, DELETE. */
public abstract class CrudController<T extends Base> {
    protected final JpaRepository<T, Long> repo;
    protected CrudController(JpaRepository<T, Long> repo) { this.repo = repo; }

    protected List<T> consultar() { return repo.findAll(); }
    protected T preparar(T novo, T antigo) { return novo; }

    @GetMapping public List<T> listar() { return consultar(); }

    @GetMapping("/{id}") public ResponseEntity<T> buscar(@PathVariable Long id) {
        return ResponseEntity.of(repo.findById(id));
    }
    @PostMapping public T criar(@RequestBody T o) {
        o.setId(null);
        return repo.save(preparar(o, null));
    }
    @PutMapping("/{id}") public ResponseEntity<T> atualizar(@PathVariable Long id, @RequestBody T o) {
        return repo.findById(id).map(antigo -> { o.setId(id); return ResponseEntity.ok(repo.save(preparar(o, antigo))); })
                   .orElse(ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}") public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
