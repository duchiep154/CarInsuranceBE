package com.c0920g1.c0920g1carinsurancebe.service;

import javax.mail.Multipart;

public interface MailService {

    void sendMail(String mailTo, Multipart file);
}
