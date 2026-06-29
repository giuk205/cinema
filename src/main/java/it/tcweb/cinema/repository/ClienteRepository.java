package it.tcweb.cinema.repository;

import it.tcweb.cinema.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Ereditiamo tutti i metodi CRUD standard (findAll, findById, save, delete, ecc.)
}
