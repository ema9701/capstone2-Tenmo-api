package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import com.techelevator.tenmo.Exceptions.InvalidMoneyWireException;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.RequestDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Request;
import com.techelevator.tenmo.model.RequestDTO;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping("/request")
public class RequestController {

    private UserDao userDao;
    private AccountDao accountDao;
    private RequestDao requestDao;

    public RequestController(UserDao userDao, AccountDao accountDao, RequestDao requestDao) {
        this.userDao = userDao;
        this.accountDao = accountDao;
        this.requestDao = requestDao;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list/{userId}")
    public List<Request> listRequests(@PathVariable Long userId) {
        return requestDao.listRequests(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{requestId}")
    public Request getRequestById(@PathVariable int requestId) {
        return requestDao.getRequestById(requestId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public void postRequest(@Valid @RequestBody RequestDTO newRequest, Principal principal) {
        User from = userDao.findByUsername(principal.getName());
        User to = userDao.getUserById(newRequest.getGrantorId());
        if (from.getId().equals(newRequest.getGrantorId())) {
            throw new InvalidMoneyWireException();
        } else {
            requestDao.postRequest(newRequest);
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{requestId}")
    public void updateStatus(@PathVariable int requestId, @RequestParam boolean approve, Principal principal) {
        User to = userDao.findByUsername(principal.getName());
        Account grantor = accountDao.findAccountByUserId(to.getId());
        Request updatedRequest = requestDao.getRequestById(requestId);
        if (updatedRequest != null && grantor.getAccountId() == updatedRequest.getGrantor()) {
            if (approve) {
                updatedRequest.finalizeRequest(true);
                requestDao.updateStatus(updatedRequest, updatedRequest.getRequestId());
                accountDao.transact(updatedRequest.getAmount(), updatedRequest.getGrantor(), updatedRequest.getRequester());
            } else {
                updatedRequest.finalizeRequest(false);
                requestDao.updateStatus(updatedRequest, updatedRequest.getRequestId());
            }
        } else {
            throw new InvalidMoneyWireException();
        }
    }
}