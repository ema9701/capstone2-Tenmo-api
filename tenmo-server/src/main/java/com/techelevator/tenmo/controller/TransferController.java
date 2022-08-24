package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User; 
import com.techelevator.tenmo.model.Account;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.security.Principal;
import java.security.cert.CertPathValidatorException.Reason;
import java.util.List;

import javax.validation.Valid;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping("/transfers")
public class TransferController {

    private UserDao userDao; 
    private AccountDao accountDao; 
    private TransferDao transferDao; 

    public TransferController(UserDao userDao, AccountDao accountDao, TransferDao transferDao) {
        this.userDao = userDao; 
        this.accountDao = accountDao; 
        this.transferDao = transferDao; 
    }

    @GetMapping("/{user_id}") 
    public List<Transfer> listTransactions(@PathVariable Long user_id) {
        return transferDao.listTransfers(user_id);
    }
    
    @GetMapping("/id/{transferId}")
    public Transfer getTransferById(@PathVariable int transferId) {
        return transferDao.getTransferById(transferId);
    }

   
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("") 
    public Transfer postTransfer(@Valid @RequestBody Transfer newTransfer, Principal principal) {
    // accountDao.accountIdByUserName(principal.getName());
      Account from =  accountDao.findAccountByUserId((long)userDao.findIdByUsername(principal.getName()));
      
        if (from.getAccount_id() != newTransfer.getAccountFrom() || 
            accountDao.findByAccountId(newTransfer.getAccountTo()) == null) {
            throw new AccessDeniedException("Please select a valid recipient");
        } else { 
            accountDao.withdrawAmount(newTransfer.getAmount(), newTransfer.getAccountFrom());
            accountDao.depositAmount(newTransfer.getAmount(), newTransfer.getAccountTo());
            transferDao.createTransfer(newTransfer);      
        }
      return  newTransfer; 
    }
}
