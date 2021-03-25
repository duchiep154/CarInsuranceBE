package com.c0920g1.c0920g1carinsurancebe.service;

import com.c0920g1.c0920g1carinsurancebe.entities.contract.PdfAccident;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PdfAccidentService {

    Page<PdfAccident> findAll(Pageable pageable);

    PdfAccident save(PdfAccident pdfAccident);
}
