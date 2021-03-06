package com.c0920g1.c0920g1carinsurancebe.controller;

import com.c0920g1.c0920g1carinsurancebe.DTO.AccidentDTO;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.Accident;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.PdfAccident;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.StatusAccident;
import com.c0920g1.c0920g1carinsurancebe.entities.qvsa.Answer;
import com.c0920g1.c0920g1carinsurancebe.service.AccidentService;
import com.c0920g1.c0920g1carinsurancebe.service.MailService;
import com.c0920g1.c0920g1carinsurancebe.service.PdfAccidentService;
import com.c0920g1.c0920g1carinsurancebe.service.StatusAccidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;
import java.util.regex.Pattern;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "api/admin/accident")
public class AccidentAdminController {

    //Quoc bao
    @Autowired
    private AccidentService accidentService;

    @Autowired
    private PdfAccidentService pdfAccidentService;

    @Autowired
    private MailService mailService;

    @Autowired
    private StatusAccidentService statusAccidentService;

    private boolean checkSendMail = false;

    //List accident waiting approval
    @GetMapping(value = "/listAccidentByStatus")
    public ResponseEntity<Page<Accident>> getAccidentWaitingApproval(@RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "5") int size,
                                                                     @RequestParam String search,
                                                                     @RequestParam Long idStatus) {
        Page<Accident> accidents = accidentService.findAccidentWaitingApproval(idStatus, search, PageRequest.of(page, size));
        if (accidents == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accidents, HttpStatus.OK);
    }

    //Find accident by id
    @GetMapping(value = "/{id}")
    public ResponseEntity<AccidentDTO> findAccidentById(@PathVariable("id") Long id) {
        AccidentDTO accidentDTO = accidentService.findAccidentById(id);
        if (accidentDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accidentDTO, HttpStatus.OK);
    }

    //Update accident was approval
    @PostMapping(value = "/wasApproval/{id}")
    public ResponseEntity<AccidentDTO> updateAccidentWasApproval(@PathVariable("id") Long id,
                                                                 @RequestBody String file) {
        if (!checkSendMail) {
            checkSendMail = true;
            String[] arr = file.split(",");
            Long idAccident = new Long(arr[2]);
            if (sendMailWasApproval(id, file) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                mailService.sendMail(arr[4], sendMailWasApproval(id, file));
            }
            AccidentDTO accidentDTO = accidentService.findAccidentById(idAccident);
            if (accidentDTO == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            checkSendMail = false;
            return new ResponseEntity<>(accidentDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //c?????ng-thanh to??n v?? g???i mail
    @PostMapping(value = "/wasRefunded/{id}")
    public ResponseEntity<StatusAccident> updateAccidentWasRefunded(@PathVariable("id") Long id,
                                                                    @RequestBody String mail) {
        StatusAccident statusAccident = statusAccidentService.findById(4L);
        if (statusAccident == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accidentService.updateAccidentWasRefund(statusAccident, id);
        sendMailWasRefund(id, mail);
        return new ResponseEntity<>(statusAccident, HttpStatus.OK);
    }

    //Update accident not approval
    @PostMapping(value = "/notApproval/{id}")
    public ResponseEntity<AccidentDTO> updateAccidentNotApproval(@PathVariable("id") Long id,
                                                                 @RequestBody String file) {
        if (!checkSendMail) {
            checkSendMail = true;
            String[] arr = file.split(",");
            Long idAccident = new Long(arr[2]);
            if (sendMailNotApproval(id, file) == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                mailService.sendMail(arr[4], sendMailNotApproval(id, file));
            }
            AccidentDTO accidentDTO = accidentService.findAccidentById(idAccident);
            if (accidentDTO == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            checkSendMail = false;
            return new ResponseEntity<>(accidentDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    //Chuy???n ?????i chu???i c?? d???u th??nh kh??ng ?????u
    private String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(temp).replaceAll("");
    }

    //function send mail for was approval
    private Multipart sendMailWasApproval(Long id, String file) {
        Multipart multipart = new MimeMultipart();
        try {
            String[] arr = file.split(",");
            String namePdf = arr[2] + "-" + arr[3] + "-" + LocalDate.now() + ".pdf";
            File filePath = new File("src/main/resources/static/pdf/wasApproval/" + namePdf);
            PdfAccident pdfAccident = new PdfAccident();
            pdfAccident.setUrlPdf(namePdf);
            pdfAccidentService.save(pdfAccident);
            FileOutputStream fop = new FileOutputStream(filePath);
            fop.write(Base64.getDecoder().decode(arr[1]));
            MimeBodyPart mimeBodyPartText = new MimeBodyPart();
            String text = "<p>C???m ??n b???n ???? x??? d???ng d???ch v??? c???a ch??ng t??i</p>"
                    + "<p>Ch??ng t??i ???? ph?? duy???t ????n y??u c???u b???i th?????ng c???a c??c b???n</p>"
                    + "<p>Ch??ng t??i ??ang c??? g???n thanh to??n ti???n cho c??c b???n. Mong c??c b???n vui l??ng ?????i</p>"
                    + "<p>B??n d?????i l?? file chi ti???t. N???u c?? g?? th?? b???n h??y li??n l???c l???i v???i ch??ng t??i ????? x??? l??." +
                    " C???m ??n b???n ???? xem</p>"
                    + "<p>---------------------------<p>"
                    + "<h4>C??ng ty B???o hi???m C09</h4>"
                    + "<p>Email</p>"
                    + "<p style=\"color:blue;\">c0920g1.2021@gmail.com</p>"
                    + "<img src=\"https://firebasestorage.googleapis.com/v0/b/car-insurace-image.appspot.com/o/" +
                    "undefined%2Fpix[???]?alt=media&token=b1b9ff37-e60a-42d3-b439-0b9428974558\"" +
                    " width=\"150px\" height=\"150px\">";
            mimeBodyPartText.setContent(text, "text/html; charset=utf-8");
            multipart.addBodyPart(mimeBodyPartText);
            MimeBodyPart mimeBodyPartFile = new MimeBodyPart();
            mimeBodyPartFile.attachFile("src/main/resources/static/pdf/wasApproval/" + namePdf);
            multipart.addBodyPart(mimeBodyPartFile);
            StatusAccident statusAccident = statusAccidentService.findById(id);
            Long idAccident = new Long(arr[2]);
            accidentService.updateAccidentWasApproval(arr[3], statusAccident, idAccident);
        } catch (IOException | MessagingException e) {
            return null;
        }
        return multipart;
    }

    //function send mail for not approval
    private Multipart sendMailNotApproval(Long id, String file) {
        Multipart multipart = new MimeMultipart();
        try {
            String[] arr = file.split(",");
            String name = removeAccent(arr[3]);
            name = name.replace(" ", "-");
            String namePdf = arr[2] + "-" + name + "-" + LocalDate.now() + ".pdf";
            File filePath = new File("src/main/resources/static/pdf/notApproval/" + namePdf);
            PdfAccident pdfAccident = new PdfAccident();
            pdfAccident.setUrlPdf(namePdf);
            pdfAccidentService.save(pdfAccident);
            FileOutputStream fop = new FileOutputStream(filePath);
            fop.write(Base64.getDecoder().decode(arr[1]));
            MimeBodyPart mimeBodyPartText = new MimeBodyPart();
            String text = "<p>C???m ??n b???n ???? x??? d???ng d???ch v??? c???a ch??ng t??i</p>"
                    + "<p>Ch??ng t??i ???? kh??ng ph?? duy???t ????n y??u c???u b???i th?????ng c???a c??c b???n</p>"
                    + "<p>Ch??ng t??i th???y nhi???u ch??? ch??a th???a ????ng. Mong c??c b???n vui l??ng xem x??t l???i</p>"
                    + "<p>B??n d?????i l?? file chi ti???t. N???u c?? g?? th?? b???n h??y li??n l???c l???i v???i ch??ng t??i ????? x??? l??." +
                    " C???m ??n b???n ???? xem</p>"
                    + "<p>---------------------------<p>"
                    + "<h4>C??ng ty B???o hi???m C09</h4>"
                    + "<p>Email:</p>"
                    + "<p style=\"color:blue;\">--> c0920g1.2021@gmail.com</p>"
                    + "<img src=\"https://firebasestorage.googleapis.com/v0/b/car-insurace-image.appspot.com/o/" +
                    "undefined%2Fpix[???]?alt=media&token=b1b9ff37-e60a-42d3-b439-0b9428974558\"" +
                    " width=\"150px\" height=\"150px\">";
            mimeBodyPartText.setContent(text, "text/html; charset=utf-8");
            multipart.addBodyPart(mimeBodyPartText);
            MimeBodyPart mimeBodyPartFile = new MimeBodyPart();
            mimeBodyPartFile.attachFile("src/main/resources/static/pdf/notApproval/" + namePdf);
            multipart.addBodyPart(mimeBodyPartFile);
            Long idAccident = new Long(arr[2]);
            StatusAccident statusAccident = statusAccidentService.findById(id);
            accidentService.updateAccidentNotApproval(arr[3], statusAccident, idAccident);
        } catch (IOException | MessagingException e) {
            return null;
        }
        return multipart;
    }

    //c?????ng
    private Multipart sendMailWasRefund(Long id, String mail) {
        Multipart multipart = new MimeMultipart();
        try {
            MimeBodyPart mimeBodyPartAnswer = new MimeBodyPart();
            String text = "<p>C???m ??n b???n ???? s??? d???ng d???ch v??? c???a ch??ng t??i</p>"
                    + "<p>Ch??ng t??i ???? thanh to??n cho y??u c???u b???i th?????ng c???a b???n </p>"
                    + " C???m ??n b???n ???? xem</p>"
                    + "<h4>C??ng ty B???o hi???m C09</h4>"
                    + "<p>Email:</p>"
                    + "<p style=\"color:blue;\"> c0920g1.2021@gmail.com</p>"
                    + "<img src=\"https://firebasestorage.googleapis.com/v0/b/car-insurace-image.appspot.com/o/" +
                    "undefined%2Fpix[???]?alt=media&token=b1b9ff37-e60a-42d3-b439-0b9428974558\"" +
                    " width=\"150px\" height=\"150px\">";
            mimeBodyPartAnswer.setContent(text, "text/html; charset=utf-8");
            multipart.addBodyPart(mimeBodyPartAnswer);
            mailService.sendMail(mail, multipart);
        } catch (MessagingException e) {
            return null;
        }
        mailService.sendMail(mail, multipart);
        return multipart;
    }
}
