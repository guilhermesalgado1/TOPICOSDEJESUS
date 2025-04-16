package br.edu.utfpr.api1.service;

import br.edu.utfpr.api1.repository.GradeAmostralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeAmostralService {

    private final GradeAmostralRepository gradeAmostralRepository;

    public List<GradeAmostralRepository> findAll() {
        return gradeAmostralRepository.findAll();
    }

    public Optional<GradeAmostralRepository> findById(Long id) {
        return gradeAmostralRepository.findById(id);
    }

    public GradeAmostralRepository save(GradeAmostralRepository gradeAmostral) {
        return gradeAmostralRepository.save(gradeAmostral);
    }

    public void deleteById(Long id) {
        gradeAmostralRepository.deleteById(id);
    }
}
