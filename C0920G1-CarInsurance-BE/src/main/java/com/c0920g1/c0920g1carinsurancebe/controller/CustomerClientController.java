package com.c0920g1.c0920g1carinsurancebe.controller;

import com.c0920g1.c0920g1carinsurancebe.entities.account.Users;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Customer;
import com.c0920g1.c0920g1carinsurancebe.service.CustomerClientService;
import com.c0920g1.c0920g1carinsurancebe.service.impl.EmailService;
import com.c0920g1.c0920g1carinsurancebe.service.impl.OTPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

// cua trang
@RestController
@RequestMapping(value = "/api/")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerClientController {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private OTPService otpService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CustomerClientService customerClientService;


    @GetMapping("/customer-client/{id}")
    public ResponseEntity<Customer> getCusByUserId(@PathVariable(value = "id") Long id) {
        Customer customer = customerClientService.findCustomerByUserId(id);
        return ResponseEntity.ok().body(customer);
    }

    //chỉnh sửa customer Trang Hồ.
    @PutMapping("/customer-client/{id}")
    public ResponseEntity<Customer> updateCus(@PathVariable(value = "id") Long id, @Valid @RequestBody Customer c) {

           if (!customerClientService.checkIDCard(c.getIdCard(), id)){
               return new ResponseEntity("CMND này đã được sử dụng bởi tài khoản khác. Vui lòng nhập đúng CMNN của quý khách", HttpStatus.BAD_REQUEST);
           } else {
               try{

               Customer customer = customerClientService.findCustomerByUserId(id);
               c.setId(customer.getId());
               customerClientService.saveCustomer(c.getId(), c.getName(), c.getGender(), c.getDateOfBirth(),
                       c.getIdCard(), c.getPhone(), c.getEmail(), c.getAddress(), c.getCity(),
                       c.getImg().replace("undefined/", "undefined%2F"));

                   return new ResponseEntity<>(HttpStatus.OK);
               }
               catch (Exception e) {

                   return new ResponseEntity("Chỉnh sửa không thành công",HttpStatus.INTERNAL_SERVER_ERROR);
               }
            }

           }



    @PostMapping(value = "/customer-client/getPass/{id}")
    public ResponseEntity<Integer> getPass(@PathVariable("id") Long id, @RequestBody String password) {
        boolean check = customerClientService.checkPass(id, password);
        if (check) {
            return ResponseEntity.ok().body(1);
        } else {
            return ResponseEntity.ok().body(2);
        }
    }

    @GetMapping(value = "/customer-client/OTP/{id}")
    public void sendOTP(@PathVariable(value = "id") Long id) {
        Customer customer = customerClientService.findCustomerByUserId(id);
        Users users = customerClientService.findUsersById(id);

        String username = users.getUsername();
        int otp = otpService.generateOTP(username);
        Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("user", username);
        replacements.put("otpnum", String.valueOf(otp));
        System.out.println(replacements);
        String emailAddress = customer.getEmail();
        emailService.setMailTo(emailAddress, String.valueOf(otp));
    }

    @PostMapping(value = "/customer-client/OTP/{id}")
    public ResponseEntity<String> checkOTP(@PathVariable(value = "id") Long id, @RequestBody String OTPClientAndNewPass) {

        String[] arr = OTPClientAndNewPass.split(",");
        System.out.println(arr[0]);
        String OTPClient = arr[0];
        System.out.println(arr[1]);
        String newPass = arr[1];
        Users users = customerClientService.findUsersById(id);
        String username = users.getUsername();
        String serverOtp = String.valueOf(otpService.getOtp(username));
        System.out.println("serverOtp" + serverOtp);
        if (OTPClient.equals(serverOtp)) {
//            encoder.encode(signUpRequest.getPassword()));
            users.setPassword(encoder.encode(newPass));

            customerClientService.saveUsers(users.getId(), encoder.encode(newPass));

            otpService.clearOTP(username);
            System.out.println("updateOK");
            return ResponseEntity.ok().body("1");
        } else {
            System.out.println("Update Not OK");
            return ResponseEntity.ok().body("2");
        }
    }

}



