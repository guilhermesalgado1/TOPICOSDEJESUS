package br.edu.utfpr.api1.repository;

import br.edu.utfpr.api1.model.TipoSolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoSoloRepository extends JpaRepository<TipoSolo, Long> {
  
}
