package br.edu.utfpr.api1.service;

import br.edu.utfpr.api1.model.GradeAmostral; 
import br.edu.utfpr.api1.repository.GradeAmostralRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeAmostralService {

    private final GradeAmostralRepository gradeAmostralRepository;

    public List<GradeAmostral> findAll(PageRequest pageRequest) {
        return gradeAmostralRepository.findAll();
    }

    public Optional<GradeAmostral> findById(Long id) {
        return gradeAmostralRepository.findById(id);
    }

    public GradeAmostral save(GradeAmostral gradeAmostral) {
        return gradeAmostralRepository.save(gradeAmostral);
    }

    public void deleteById(Long id) {
        gradeAmostralRepository.deleteById(id);
    }


    }

