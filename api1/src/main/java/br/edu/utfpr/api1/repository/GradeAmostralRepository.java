package br.edu.utfpr.api1.repository;

import br.edu.utfpr.api1.model.GradeAmostral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeAmostralRepository extends JpaRepository<GradeAmostral, Long> {
    
}
