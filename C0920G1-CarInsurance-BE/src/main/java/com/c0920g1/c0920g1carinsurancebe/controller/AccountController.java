package com.c0920g1.c0920g1carinsurancebe.controller;

import com.c0920g1.c0920g1carinsurancebe.entities.account.ERole;
import com.c0920g1.c0920g1carinsurancebe.entities.account.Role;
import com.c0920g1.c0920g1carinsurancebe.entities.account.Users;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Customer;
import com.c0920g1.c0920g1carinsurancebe.repository.CustomerRepository;
import com.c0920g1.c0920g1carinsurancebe.repository.RoleRepository;
import com.c0920g1.c0920g1carinsurancebe.repository.UserRepository;
import com.c0920g1.c0920g1carinsurancebe.security.jwt.JwtUtils;
import com.c0920g1.c0920g1carinsurancebe.security.services.UserDetailsImpl;
import com.c0920g1.c0920g1carinsurancebe.service.CustomerService;
import com.c0920g1.c0920g1carinsurancebe.userDTO.request.LoginRequest;
import com.c0920g1.c0920g1carinsurancebe.userDTO.request.SignupRequest;
import com.c0920g1.c0920g1carinsurancebe.userDTO.response.JwtResponse;
import com.c0920g1.c0920g1carinsurancebe.userDTO.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

//Tran Minh Chien
//Dang Nhap, Dang Ky
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AccountController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;

    //  Chuc Nang Dang Nhap ---Chien TM---
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles));
    }

    //    Dang ky tai khoan moi ---TM Chien---
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        Map<String, String> listError = new HashMap<>();
        if (signUpRequest == null) {
            listError.put("null","Vui l??ng nh???p t???t c??? tr?????ng!");
        }


//        Kiem tra ten tai khoan da co trong database chua
        if (userRepository.existsUsersByUsername(signUpRequest.getUsername())) {

            listError.put("existUser","T??n t??i kho???n ???? t???n t???i!");
        }

//        Ki???m tra Email ???? ???????c ????ng k?? ch??a.
        if (customerRepository.existsCustomerByEmail(signUpRequest.getEmail())) {
            listError.put("existEmail","Email ???? ???????c ????ng k??!");
        }

//        Ki???m tra ch???ng minh nh??n d??n
        if (customerRepository.existsCustomerByIdCard(signUpRequest.getIdCard())) {
            listError.put("existIdcard","S??? Ch???ng minh nh??n d??n ???? ???????c ????ng k??!");
        }
//        Kiem tra mat khau xac nhan dung hay chua
        if (!signUpRequest.getPassword().equals(signUpRequest.getCheckPassword())) {
            listError.put("checkPass","X??c nh???n m???t kh???u kh??ng ????ng!");
        }

        if (!listError.isEmpty()){
            return ResponseEntity
                    .badRequest()
                    .body(listError);
        }
//       Tao moi tai khoan.
        Users user = null;
        user = new Users(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

//      Kh??ch h??ng t???o m???i t??i kho???n m???c ?????nh l?? ROLE CUSTOMER
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "employee":
                        Role modRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
//        L??u th??ng tin user xu???ng database
        user.setRoles(roles);
        userRepository.save(user);
//        T???o tr?????c t??i kho???n r???i l???y User l??n t???o Customer.
            Customer customer = new Customer(signUpRequest.getName(),
                    signUpRequest.getEmail(),
                    signUpRequest.getPhone(),
                    signUpRequest.getIdCard(),
                    signUpRequest.getAddress(),
                    user);
            customerRepository.save(customer);
            return ResponseEntity.ok(new MessageResponse("success","????ng k?? t??i kho???n th??nh c??ng!"));
    }
    //handle exception Chien TM
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
