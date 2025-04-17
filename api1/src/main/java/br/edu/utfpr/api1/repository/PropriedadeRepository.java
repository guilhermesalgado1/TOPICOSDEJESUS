package br.edu.utfpr.api1.repository;

import br.edu.utfpr.api1.model.Propriedade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface PropriedadeRepository extends JpaRepository<Propriedade, Long> {

    List<Propriedade> findByNome(String nome);
}
