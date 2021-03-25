package com.c0920g1.c0920g1carinsurancebe.controller;

import com.c0920g1.c0920g1carinsurancebe.DTO.EmployeeDTO;
import com.c0920g1.c0920g1carinsurancebe.entities.account.ERole;
import com.c0920g1.c0920g1carinsurancebe.entities.account.Role;
import com.c0920g1.c0920g1carinsurancebe.entities.account.Users;
import com.c0920g1.c0920g1carinsurancebe.entities.employee.Employee;
import com.c0920g1.c0920g1carinsurancebe.entities.employee.Position;
import com.c0920g1.c0920g1carinsurancebe.repository.PositionRepository;
import com.c0920g1.c0920g1carinsurancebe.repository.RoleRepository;
import com.c0920g1.c0920g1carinsurancebe.repository.UserRepository;
import com.c0920g1.c0920g1carinsurancebe.repository.UsersRepository;
import com.c0920g1.c0920g1carinsurancebe.service.EmployeeService;
import com.c0920g1.c0920g1carinsurancebe.service.PositionService;
import com.c0920g1.c0920g1carinsurancebe.service.UsersService;

import com.c0920g1.c0920g1carinsurancebe.service.impl.EmailService;
import com.c0920g1.c0920g1carinsurancebe.service.impl.OTPService;
import org.springframework.beans.factory.annotation.Autowired;

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

import java.util.List;
import java.util.*;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/")
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeeController {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private UsersService usersService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    public OTPService otpService;
    @Autowired
    public EmailService emailService;

    //  Tuấn làm list danh sách gồm phân trang, sort theo các trường khác nhau

    //    Tuấn làm create, search, list
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
    
    //    Tuấn làm create, search, list

    //    List danh sách gồm phân trang, sort theo các trường khác nhau

    @GetMapping(value = "admin/employee/list/", params = {"page", "size", "onSorting", "textSorting"})
    public ResponseEntity<Page<Employee>> listEmployee(@RequestParam("page") int page, @RequestParam("size") int size,
                                                       @RequestParam("onSorting") boolean onSorting, @RequestParam("textSorting") String textSorting) {
        System.out.println(textSorting);
        try {
            Page<Employee> employees;
            if (textSorting.equals("")) {
                employees = employeeService.findAllEmployees(PageRequest.of(page, size));
            } else {
                if (!onSorting) {
                    employees = employeeService.findAllEmployees(PageRequest.of(page, size, Sort.by(textSorting).ascending()));

                } else {
                    employees = employeeService.findAllEmployees(PageRequest.of(page, size, Sort.by(textSorting).descending()));
                }
            }
            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(employees, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  Tuấn làm List danh sách tìm bằng tên tương đối và số điện thoại tương đối
    @GetMapping(value = "admin/employee", params = {"size", "inputSearch"})
    public ResponseEntity<Page<Employee>> listEmployeeByNameContainingOrPhoneContaining(
            @RequestParam("inputSearch") String inputSearch, @RequestParam("size") int size) {
        try {
            Page<Employee> employees = employeeService.findAllEmployeesByNameContainingOrPhoneContaining(PageRequest.of(0, size), inputSearch);
            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //  Tuấn tạo 1 nhân viên mới
    @PostMapping(value = "admin/employee", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) {
        try {
            List<Employee> employeeList = employeeService.finAllEmployee();
            if (!employeeList.isEmpty()) {
                Map<String, String> listError = new HashMap<>();
                List<Users> usersList = usersService.getAllUsers();
                if (!usersList.isEmpty()) {
                    for (Users u : usersList) {
                        if (u.getUsername().equals(employeeDTO.getUsers())) {
                            listError.put("existAccount", "Tài khoản đã tồn tại.");
                            break;
                        }
                    }
                }
                if (!employeeDTO.getNewPassword().equals(employeeDTO.getConfirmPassword())) {
                    listError.put("notCorrect", "Mật khẩu nhập không chính xác.");
                }
                for (Employee e : employeeList) {

                    if (e.getEmail().equals(employeeDTO.getEmail())) {
                        listError.put("existEmail", "Email đã được đăng ký.");
                    }
                    if (e.getIdCard().equals(employeeDTO.getIdCard())) {
                        listError.put("existIdCard", "CMND đã được đăng ký.");
                    }
                    if (e.getPhone().equals(employeeDTO.getPhone())) {
                        listError.put("existPhone", "Điện thoại đã được đăng ký.");
                    }
                }
                if (!listError.isEmpty()) {
                    return ResponseEntity
                            .badRequest()
                            .body(listError);
                }
            }
            System.out.println(employeeDTO.getNewPassword());
            Set<Role> roles = new HashSet<>();
            Role userRole = roleRepository.findByName(ERole.ROLE_EMPLOYEE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
            Users users = new Users();
            users.setUsername(employeeDTO.getUsers());
            users.setPassword(encoder.encode(employeeDTO.getNewPassword()));
            users.setUsername(employeeDTO.getUsers());
            users.setRoles(roles);
            userRepository.save(users);
//
            Employee employee = new Employee();
            employee.setName(employeeDTO.getName());
            employee.setGender(employeeDTO.getGender());
            employee.setDateOfBirth(employeeDTO.getDateOfBirth());
            employee.setPhone(employeeDTO.getPhone());
            employee.setIdCard(employeeDTO.getIdCard());
            employee.setEmail(employeeDTO.getEmail());
            employee.setAddress(employeeDTO.getAddress());
            employee.setCity(employeeDTO.getCity());
            employee.setImg(employeeDTO.getImg());
            employee.setPosition(positionRepository.getOne(employeeDTO.getPosition()));
            employee.setUsers(users);
            employeeService.saveEmployee(employee);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    Hưng làm edit và delete

    @GetMapping("admin/employee")
    public List<Employee> getEmployee() {
        return this.employeeService.finAllEmployee();
    }

    @GetMapping("admin/position")

    //    Hưng làm edit và delete
    //lấy dữ liệu chức vụ của hưng
    public ResponseEntity<List<Position>> getPosition() {
        try {
            List<Position> positions = positionService.getAllPosition();
            return ResponseEntity.ok().body(positions);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("admin/users")
    //lấy dữ liệu từ bảng users của hưng
    public ResponseEntity<List<Users>> getUsers() {
        try {
            List<Users> users = usersService.getAllUsers();
            return ResponseEntity.ok().body(users);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("admin/employee/{id}")
    //tìm theo id của hưng
    public ResponseEntity<Employee> getByIdEmployee(@PathVariable(value = "id") Long id) {
        try {
            Employee employee = employeeService.findById(id);
            return ResponseEntity.ok().body(employee);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //update nhân viên của hưng
    @PutMapping("admin/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id") Long id, @RequestBody Employee employee) {
        try {
            Employee employee1 = employeeService.findById(id);
            if (employee1 != null){
                employee1.setName(employee.getName());
                employee1.setGender(employee.getGender());
                employee1.setDateOfBirth(employee.getDateOfBirth().replace("T17:00:00.000Z" ,""));
                employee1.setIdCard(employee.getIdCard());
                employee1.setPhone(employee.getPhone());
                employee1.setEmail(employee.getEmail());
                employee1.setAddress(employee.getAddress());
                employee1.setCity(employee.getCity());
                employee1.setPosition(employee.getPosition());
                employee1.setUsers(employee.getUsers());
                employee1.setId(employee.getId());
                employee1.setImg(employee.getImg().replace("undefined/", "undefined%2F"));
                employeeService.save(employee1);
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    //xóa nhân viên theo id của hưng
    @DeleteMapping("admin/employee/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable long id) {
        try {
            if (!employeeService.getAllContract(id)) {
                employeeService.deleteEmployeeById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //     nhan
    @GetMapping("user/detail/{id}")
    public Users getUserById(@PathVariable(value = "id") long id) {
        return usersService.findUsersById(id);
    }

    @GetMapping("employee/detail/{id}")
    public Employee getEmployeeByIdAccount(@PathVariable(value = "id") Long id) {
        return this.employeeService.getEmployeeByIdAccount(id);
    }

    @PutMapping("employee/detail/{id}")
    public ResponseEntity<Employee> updateEmployeeRoleEmployee(@PathVariable(value = "id") Long id, @Valid @RequestBody
            Employee employee) {
        try {
            Employee employee1 = employeeService.findById(id);
            employee1.setName(employee.getName());
            employee1.setGender(employee.getGender());
            employee1.setDateOfBirth(employee.getDateOfBirth());
            employee1.setIdCard(employee.getIdCard());
            employee1.setPhone(employee.getPhone());
            employee1.setEmail(employee.getEmail());
            employee1.setAddress(employee.getAddress());
            employee1.setCity(employee.getCity());
            employee1.setPosition(employee.getPosition());
            employee1.setUsers(employee.getUsers());
            employee1.setId(employee.getId());
            employee1.setImg(employee.getImg().replace("undefined/", "undefined%2F"));
            employeeService.save(employee1);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(value = "/employee/OTP/{id}")
    public void sendOTP(@PathVariable(value = "id") Long id) {
        Employee employee = employeeService.getEmployeeByIdAccount(id);
        Users users = usersService.findUsersById(id);

        String username = users.getUsername();
        int otp = otpService.generateOTP(username);
        Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("user", username);
        replacements.put("otpnum", String.valueOf(otp));
        System.out.println(replacements + "aaaa");
        String emailAddress = employee.getEmail();
        emailService.setMailTo(emailAddress, String.valueOf(otp));
    }

    @PostMapping(value = "/employee/OTP/{id}")
    public ResponseEntity<String> checkOTP(@PathVariable(value = "id") Long id, @RequestBody String OTPClientAndNewPass) {

        String[] arr = OTPClientAndNewPass.split(",");
        System.out.println(arr[0]);
        String OTPClient = arr[0];
        System.out.println(arr[1]);
        String newPass = arr[1];
        Users users = usersService.findUsersById(id);
        String username = users.getUsername();
        String serverOtp = String.valueOf(otpService.getOtp(username));
        System.out.println("serverOtp" + serverOtp);
        if (OTPClient.equals(serverOtp)) {
//            encoder.encode(signUpRequest.getPassword()));
            users.setPassword(encoder.encode(newPass));
            usersRepository.save(users);
            otpService.clearOTP(username);
            System.out.println("update OK");
            return ResponseEntity.ok().body("1");
        } else {
            System.out.println("Update Not OK");
            return ResponseEntity.ok().body("2");
        }
    }

    @PostMapping(value = "/employee/getPass/{id}")
    public ResponseEntity<Integer> getPass(@PathVariable("id") Long id, @RequestBody String password) {
        System.out.println(password);
        boolean check = employeeService.checkPass(id, password);
        if (check) {
            return ResponseEntity.ok().body(1);
        } else {
            return ResponseEntity.ok().body(2);
        }
    }
}
