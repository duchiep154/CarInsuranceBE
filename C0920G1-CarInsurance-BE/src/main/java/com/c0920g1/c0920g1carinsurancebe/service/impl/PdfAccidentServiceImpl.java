package com.c0920g1.c0920g1carinsurancebe.service.impl;

import com.c0920g1.c0920g1carinsurancebe.entities.contract.PdfAccident;
import com.c0920g1.c0920g1carinsurancebe.repository.PdfAccidentRepository;
import com.c0920g1.c0920g1carinsurancebe.service.PdfAccidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PdfAccidentServiceImpl implements PdfAccidentService {

    @Autowired
    PdfAccidentRepository pdfAccidentRepository;

    @Override
    public Page<PdfAccident> findAll(Pageable pageable) {
        return pdfAccidentRepository.findAll(pageable);
    }

    @Override
    public PdfAccident save(PdfAccident pdfAccident) {
        return pdfAccidentRepository.save(pdfAccident);
    }
}
