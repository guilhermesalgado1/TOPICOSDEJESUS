package br.edu.utfpr.api1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.utfpr.api1.model.GradeAmostral;

public interface GradeAmostralRepository extends JpaRepository<GradeAmostral, Long> {
    
}
