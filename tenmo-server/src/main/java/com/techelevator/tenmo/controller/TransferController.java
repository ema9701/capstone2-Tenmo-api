package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.InvalidMoneyWireException;

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

@PreAuthorize("isAuthenticated()")
@RequestMapping("/transfer")
@RestController
@CrossOrigin
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
    public Transfer postTransfer(@Valid @RequestBody Transfer newTransfer, Principal principal) {

        Account sender = accountDao.findAccountByUserId((long) userDao.findIdByUsername(principal.getName()));
        Account recipient = accountDao.findByAccountId(newTransfer.getAccountTo());
        BigDecimal cash = newTransfer.getAmount();

        if (sender.getaccountId() != recipient.getaccountId()
                && sender.getaccountId() == newTransfer.getAccountFrom()) {
            accountDao.withdrawAmount(cash, sender.getaccountId());
            accountDao.depositAmount(cash, recipient.getaccountId());
            transferDao.createTransfer(newTransfer);
        } else {
            throw new AccessDeniedException("Please select a valid recipient");
        }
        return newTransfer;
    }
}
