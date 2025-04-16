package br.edu.utfpr.api1.repository;
import br.edu.utfpr.api1.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;



public interface PessoaRepository extends JpaRepository <Pessoa,Long> {
    public Pessoa findByEmail(String email);

    
} 


