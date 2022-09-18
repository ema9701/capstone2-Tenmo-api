package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import com.techelevator.tenmo.Exceptions.InvalidMoneyWireException;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
    @PutMapping("/{requestId}/approve")
    public void approve(@Valid @PathVariable int requestId, Principal principal) {
        Request requestToUpdate = requestDao.getRequestById(requestId);
        String status = requestToUpdate.getStatus();
        Account grantor = accountDao.findAccountByUserId((long) userDao.findIdByUsername(principal.getName()));
        if (grantor.getAccountId() == requestToUpdate.getGrantor() && status.equalsIgnoreCase("PENDING")) {
            requestDao.approve(requestToUpdate, requestId);
            accountDao.withdrawAmount(requestToUpdate.getAmount(), requestToUpdate.getGrantor());
            accountDao.depositAmount(requestToUpdate.getAmount(), requestToUpdate.getRequester());
        } else {
            throw new InvalidMoneyWireException();
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{requestId}/reject")
    public void reject(@Valid @PathVariable int requestId, Principal principal) {
        Request requestToUpdate = requestDao.getRequestById(requestId);
        String status = requestToUpdate.getStatus();
        Account grantor = accountDao.findAccountByUserId((long) userDao.findIdByUsername(principal.getName()));
        if (grantor.getAccountId() == requestToUpdate.getGrantor() && status.equalsIgnoreCase("PENDING")) {
            requestDao.reject(requestToUpdate, requestId);
        } else {
            throw new InvalidMoneyWireException();
        }
    }
}