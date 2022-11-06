package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.Exceptions.InvalidMoneyWireException;
import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferDTO;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.Account;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping("/transfer")
public class TransferController {

    private UserDao userDao;
    private AccountDao accountDao;
    private TransferDao transferDao;

    public TransferController(UserDao userDao, AccountDao accountDao, TransferDao transferDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.transferDao = transferDao;
    }

    @GetMapping("/{userId}")
    public List<Transfer> listTransactions(@PathVariable Long userId) {
        return transferDao.listTransfers(userId);
    }


    @GetMapping("/id/{transferId}")
    public Transfer getTransferById(@PathVariable int transferId) {
        return transferDao.getTransferById(transferId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Transfer postTransfer(@Valid @RequestBody TransferDTO newTransfer, Principal principal) {
        User from = userDao.findByUsername(principal.getName());
        Account sender = accountDao.findAccountByUserId(from.getId());
        User to = userDao.getUserById(newTransfer.getReceivableUserId());
        Account recipient = accountDao.findAccountByUserId(to.getId());
        if (sender.getUserId().equals(recipient.getUserId())) {
            throw new InvalidMoneyWireException();
        }
        Integer newId = transferDao.postTransfer(newTransfer);
        accountDao.transact(newTransfer.getAmount(), sender.getAccountId(), recipient.getAccountId());
        return transferDao.getTransferById(newId);
    }
}