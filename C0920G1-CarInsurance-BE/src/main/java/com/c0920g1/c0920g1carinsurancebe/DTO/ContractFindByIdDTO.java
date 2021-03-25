package com.c0920g1.c0920g1carinsurancebe.DTO;

import com.c0920g1.c0920g1carinsurancebe.entities.contract.Contract;

public interface ContractFindByIdDTO {
    public String getId();
    public String getName();
    public String getStartDate();
    public String getEndDate();
    public String getNumberPlate();
    public String getProductName();
    public String getTotalMoney();
}
