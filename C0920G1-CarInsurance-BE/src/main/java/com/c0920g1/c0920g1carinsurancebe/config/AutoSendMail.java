package com.c0920g1.c0920g1carinsurancebe.config;

import com.c0920g1.c0920g1carinsurancebe.controller.ContractController;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.Contract;
import com.c0920g1.c0920g1carinsurancebe.entities.contract.TimePay;
import com.c0920g1.c0920g1carinsurancebe.service.ContractService;
import com.c0920g1.c0920g1carinsurancebe.service.TimePayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class AutoSendMail {
    @Autowired
    ContractService contractService;

    @Autowired
    TimePayService timePayService;

    @Autowired
    ContractController contractController;

    @Scheduled(fixedDelay = 10000)
    public void scheduleFixedDelayTask() {
//        List<Contract> contracts = contractService.findAllToCheckApproval();
//        for (Contract contract: contracts){
//            System.out.println(contract.getStatusApproval());
//            if (contract.getDuration() == 1){
//                contractController.sendMail("mainamkhanh92@gmail.com", content);
//                break;
//            }
//        }
        String content = "Quý khách có hợp đồng gần đến hạn thanh toán. Chi tiết xem tại <a href='http://localhost:4200/list-contract'>'http://localhost:4200/list-contract'</a>";
        List<TimePay> timePays = timePayService.findAll();
        for (TimePay timePay: timePays) {
            Contract contract = contractService.findContractToGetStatusPay(timePay.getId());
            System.out.println(timePay.getCountDown());
            System.out.println(contract.getStatusPay().equals("0"));
            if(contract.getStatusPay().equals("0") && timePay.getCountDown() > 1){
                timePay.setCountDown(timePay.getCountDown() - 1);
                timePayService.save(timePay);
            }else if (contract.getStatusPay().equals("0") && timePay.getCountDown() == 1){
                timePay.setCountDown(timePay.getCountDown() - 1);
                timePayService.save(timePay);
                contractController.sendMail("mainamkhanh92@gmail.com", content);
            }else if (contract.getStatusPay().equals("0") && timePay.getCountDown() < 1){
                contractService.deleteById(contract.getId());
                timePayService.deleteById(timePay.getId());
            }else {
                timePayService.deleteById(timePay.getId());
            }
        }
    }
}
