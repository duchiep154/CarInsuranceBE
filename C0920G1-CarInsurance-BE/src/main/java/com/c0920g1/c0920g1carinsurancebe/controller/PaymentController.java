
package com.c0920g1.c0920g1carinsurancebe.controller;

import com.c0920g1.c0920g1carinsurancebe.DTO.BillContractListDTO;
import com.c0920g1.c0920g1carinsurancebe.DTO.PayDTO;
import com.c0920g1.c0920g1carinsurancebe.config.PaypalPaymentIntent;
import com.c0920g1.c0920g1carinsurancebe.config.PaypalPaymentMethod;
import com.c0920g1.c0920g1carinsurancebe.DTO.Bill;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.Contract;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.ContractDetail;
import com.c0920g1.c0920g1carinsurancebe.repository.ContractRepository;

import com.c0920g1.c0920g1carinsurancebe.service.ContractDetailService;
import com.c0920g1.c0920g1carinsurancebe.service.ContractService;
import com.c0920g1.c0920g1carinsurancebe.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/checkout")
public class PaymentController {


    public static final String PAYPAL_SUCCESS_URL = "pay/success";
    public static final String PAYPAL_CANCEL_URL = "pay/cancel";

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private PaypalService paypalService;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    ContractService contractService;
    @Autowired
    ContractDetailService contractDetailService;
    private Object Bill;


    // payment online with paypal @param  billDTO
    @RequestMapping(value = "/pay", method=RequestMethod.POST)
    public String checkoutPost(@RequestBody BillContractListDTO bill
            ,HttpServletRequest request
    ){
        String paymentc="online";
        String payment_type="paypal";
        long id;
        int size = bill.getListContract().size();
        Contract contract=new Contract();
        ContractDetail   contractDetail=new ContractDetail();
        for (int i = 0; i < size; i++){
            contract=contractService.findContractByIdToUpdateStatusPay(bill.getListContract().get(i).getId());
            contract.setStatusPay("done");
            contractService.updateContractStatusPay(contract);
            contractDetail=contractDetailService.findById(contract.getId());
            contractDetail.setPayment("online");
            contractDetail.setPayment_type("PayPal");
            contractDetailService.save(contractDetail);

        }


//paypal
        String cancelUrl = "http://localhost:4200/contract";
        String successUrl = "http://localhost:4200/success";
        try {
            Payment payment = paypalService.createPayment(
                    bill.getTotal_pay(),
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    String.valueOf(bill.getIdUser() ),
                    cancelUrl,
                    successUrl);
            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";

    }


    //  method khi user cancel payment
    @RequestMapping(method = RequestMethod.GET, value = PAYPAL_CANCEL_URL)
    public String cancelPay(){

        return "http://localhost:4200/api/contract";
    }

    @RequestMapping(method = RequestMethod.GET, value = PAYPAL_SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId){
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            System.out.println();
            if(payment.getState().equals("approved")){

                return "http://localhost:4200/success";
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return "redirect:/";
    }


    // hiển thị thông tin bill cho payer
//    @param = id contract
    @GetMapping("/bill/{id}")
    public ResponseEntity<Bill> getBillByContractId(@PathVariable(value = "id") Long id){
        Bill bill = contractRepository.findBillByContractId(id);
        if (bill == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return ResponseEntity.ok().body(bill);
        }
    }



    @PostMapping(value = "/pay-paypal")
    public ResponseEntity<PayDTO> createBillPaymentPayPal(@RequestBody PayDTO pay ) {
        Contract contract = contractService.findById(pay.getId_contract());
        ContractDetail contractDetail = contractDetailService.findById(pay.getId_contract_detail());
        contract.setStatusPay("1");
        contractDetail.setPayment("Online");
        contractDetail.setPayment_type("paypal");
        contractService.save(contract);
        contractDetailService.save(contractDetail);
        System.out.println(contract);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping(value = "/payment")
    public ResponseEntity<PayDTO> createBillPaymentOffline(@RequestBody PayDTO pay ) {
        Contract contract = contractService.findById(pay.getId_contract());
        ContractDetail contractDetail = contractDetailService.findById(pay.getId_contract_detail());
        Bill bill = contractRepository.findBillByContractId(pay.getId_contract());


        contract.setStatusPay("1");
        contractDetail.setPayment("offline");
        contractDetail.setPayment_type("nhân viên" + bill.getEmployee_name() +"sẽ liên lạc với bạn nhé");

        contractService.save(contract);
        contractDetailService.save(contractDetail);

        System.out.println(contract);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/contract/{id}")
    public ResponseEntity<Contract> getByContractId(@PathVariable(value = "id") Long id){
        Contract contract = contractService.findById(id);
        if (contract == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return ResponseEntity.ok().body(contract);
        }
    }




}

