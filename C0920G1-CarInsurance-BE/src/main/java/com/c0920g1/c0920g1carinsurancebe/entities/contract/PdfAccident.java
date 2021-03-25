package com.c0920g1.c0920g1carinsurancebe.entities.contract;

import javax.persistence.*;

@Entity
@Table(name = "pdf_accident")
public class PdfAccident {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String urlPdf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlPdf() {
        return urlPdf;
    }

    public void setUrlPdf(String urlPdf) {
        this.urlPdf = urlPdf;
    }
}
