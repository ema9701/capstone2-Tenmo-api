package com.techelevator.tenmo.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.techelevator.tenmo.model.Transfer;

public interface TransferDao {

    List<Transfer> listTransfers(Long userId);

    Transfer getTransferById(int transferId);

    boolean createTransfer(Transfer newTransfer);

}
