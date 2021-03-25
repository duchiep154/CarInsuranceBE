package com.c0920g1.c0920g1carinsurancebe.controller;
import com.c0920g1.c0920g1carinsurancebe.DTO.CustomerDTO;
import com.c0920g1.c0920g1carinsurancebe.entities.account.ERole;
import com.c0920g1.c0920g1carinsurancebe.entities.account.Role;
import com.c0920g1.c0920g1carinsurancebe.entities.account.Users;
import com.c0920g1.c0920g1carinsurancebe.entities.customer.Customer;
import com.c0920g1.c0920g1carinsurancebe.repository.RoleRepository;
import com.c0920g1.c0920g1carinsurancebe.repository.UserRepository;
import com.c0920g1.c0920g1carinsurancebe.DTO.EditCustomerEmployeeDTO;
import com.c0920g1.c0920g1carinsurancebe.service.CustomerService;
import com.c0920g1.c0920g1carinsurancebe.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//Thế anh
@RestController
@RequestMapping(value = "/api/employee")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {


    @Autowired
    private CustomerService customerService;


    @Autowired
    private UsersService usersService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    //Tuấn lấy danh sách nhân viên không phân trang
    @GetMapping("/customer-list-contract")
    public List<Customer> getCustomers() {
        return this.customerService.findAllCustomer();
    }

    //    Thế Anh làm Controller in ra list customer + phân trang + sort theo nhiều trường
    @GetMapping(value = "/customer-list", params = {"page", "size", "onSorting", "textSorting"})
    public ResponseEntity<Page<Customer>> listCustomer(@RequestParam("page") int page,
                                                       @RequestParam("size") int size,
                                                       @RequestParam("onSorting") boolean onSorting,
                                                       @RequestParam("textSorting") String textSorting) {
        try {
            Page<Customer> customers;
            if (textSorting.equals("")) {
                customers = customerService.findAllCustomers(PageRequest.of(page, size));
            } else {
                if (!onSorting) {
                    customers = customerService.findAllCustomers(PageRequest.of(page, size, Sort.by(textSorting).ascending()));

                } else {
                    customers = customerService.findAllCustomers(PageRequest.of(page, size, Sort.by(textSorting).descending()));
                }
            }
            if (customers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(customers, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    Thế Anh làm tìm kiếm theo tên khách hàng và thành phố có phân trang
    @GetMapping(value = "/customer-list", params = {"size", "inputSearch"})
    public ResponseEntity<Page<Customer>> listCustomerByNameContainingOrCityContaining(
            @RequestParam("inputSearch") String inputSearch, @RequestParam("size") int size) {
        try {
            Page<Customer> customers = customerService.findAllCustomersByNameContainingOrCityContaining(PageRequest.of(0, size), inputSearch);
            if (customers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
//    Thế anh Làm tìm kiếm theo khoảng ngày sinh
    @GetMapping(value = "/customer-list")
    public ResponseEntity<Page<Customer>> findCustomerByBirthday(@RequestParam String birthdayStart,
                                                                @RequestParam String birthdayEnd,
                                                                @RequestParam int size) {
        Page<Customer> customers = customerService.findByBirthday(birthdayStart, birthdayEnd, PageRequest.of(0, size));

        if (customers == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(customers, HttpStatus.OK);
        }
    }
//    Thế Anh Tìm Kiếm theo ngày bắt đầu làm hợp đồng
    @GetMapping(value = "/customer-list", params = {"size", "dayByContract"})
    public ResponseEntity<Page<Customer>> findCustomerByContractStartDate(
            @RequestParam("dayByContract") String dayByContract, @RequestParam("size") int size) {
        try {
            Page<Customer> customers = customerService.findByDayByContract(dayByContract, PageRequest.of(0,size));
            if (customers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    Check user có tồn tại hay chưa
    @GetMapping(value = "/check-user", params = {"user"})
    public boolean checkUser(@RequestParam("user") String user) {
        List<Users> usersList = usersService.getAllUsers();
        if (!usersList.isEmpty()) {
            for (Users u : usersList) {
                if (u.getUsername().equals(user)) {
                    return false;
                }
            }
        }
        return true;
    }

    //    Thế anh viết contrller tạo khách hàng
    @PostMapping(value = "/customer-create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        try {
            List<Users> usersList = usersService.getAllUsers();
            if (!usersList.isEmpty()) {
                for (Users u : usersList) {
                    if (u.getUsername().equals(customerDTO.getUsers())) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                    }
                }
            }
            if (!customerDTO.getNewPassword().equals(customerDTO.getConfirmPassword())){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<Customer> customerList = customerService.findAllCustomer();
            if (!customerList.isEmpty()) {
                Map<String,String> listError = new HashMap<>();
                for (Customer e : customerList) {
                    if (e.getEmail().equals(customerDTO.getEmail())) {
                        listError.put("existEmail", "Email đã được đăng ký!");
                    }
                    if (e.getIdCard().equals(customerDTO.getIdCard())) {
                        listError.put("existIdCard", "CMND đã được đăng ký!");
                    }
                    if (e.getPhone().equals(customerDTO.getPhone())) {
                        listError.put("existPhone", "Điện thoại đã được đăng ký!");
                    }
                }
                if (!listError.isEmpty()){
                    return ResponseEntity
                            .badRequest()
                            .body(listError);
                }
            }
            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
            Users users = new Users();
            users.setUsername(customerDTO.getUsers());
            users.setPassword(encoder.encode(customerDTO.getNewPassword()));
            users.setUsername(customerDTO.getUsers());
            users.setRoles(roles);
            userRepository.save(users);
            Customer customer = new Customer();
            customer.setName(customerDTO.getName());
            customer.setGender(customerDTO.getGender());
            customer.setDateOfBirth(customerDTO.getDateOfBirth());
            customer.setPhone(customerDTO.getPhone());
            customer.setIdCard(customerDTO.getIdCard());
            customer.setEmail(customerDTO.getEmail());
            customer.setAddress(customerDTO.getAddress());
            customer.setCity(customerDTO.getCity());
            customer.setImg(customerDTO.getImg());
            customer.setUsers(users);
            customerService.save(customer);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    //    Thế anh viết controller bắt validate backEnd
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
//    Thế Anh

    //////////////////// Cre: Nguyen Bao //////////////////
    // nguyen bao lấy ra khách hàng theo id
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.findCustomerById(id);
        if (customer == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }


// nguyen bao cập nhật khách hàng theo id

    @RequestMapping(value = "/customer-edit/{id}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> editCustomer(@Valid @RequestBody EditCustomerEmployeeDTO c, @PathVariable Long id) {
        c.setId(id);
        List<Customer> customerList = customerService.findAllCustomer();
        try {
            if (!customerList.isEmpty()) {
                Map<String, String> listError = new HashMap<>();
                for (Customer customer : customerList) {
                    if (customer.getEmail().equals(c.getEmail()) && !customer.getId().equals(c.getId())) {
                        listError.put("existEmail", "Email đã được đăng ký!");
                    }
                    if (customer.getIdCard().equals(c.getIdCard()) && !customer.getId().equals(c.getId())) {
                        listError.put("existIdCard", "CMND đã được đăng ký!");
                    }
                    if (customer.getPhone().equals(c.getPhone()) && !customer.getId().equals(c.getId())) {
                        listError.put("existPhone", "Điện thoại đã được đăng ký!");
                    }
                }
                if (!listError.isEmpty()) {
                    return ResponseEntity
                            .badRequest()
                            .body(listError);
                }
            }
            customerService.saveCustomer(c.getId(), c.getName(), c.getGender(), c.getDateOfBirth(), c.getIdCard(),
                    c.getPhone(), c.getEmail(), c.getAddress(), c.getCity(),
                    c.getImg().replace("undefined/", "undefined%2F"));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

// nguyen bao xóa khách hàng theo id

    @RequestMapping(value = "/customer-delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Customer> delete(@PathVariable Long id) {
        try {
            if (!customerService.checkExistContract(id)) {
                customerService.deleteCustomer(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /////////////End of Cre: nguyen bao/////////////

    @GetMapping("/car/customers-list")
    public List<Customer> getCustomer() {
        return this.customerService.findAllCustomer();
    }
}
