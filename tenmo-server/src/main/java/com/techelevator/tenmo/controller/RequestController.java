package com.techelevator.tenmo.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.AccessDeniedException;
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
    public Request postRequestTransfer(@Valid @RequestBody Request newRequest, Principal principal) {
        Account to = accountDao.findAccountByUserId((long) userDao.findIdByUsername(principal.getName()));

        if (to.getaccountId() != newRequest.getAccountTo() || to.getaccountId() == newRequest.getAccountFrom() ||
                accountDao.findByAccountId(newRequest.getAccountFrom()) == null) {
            throw new AccessDeniedException("Invalid request made");
        } else {
            requestDao.createRequest(newRequest);
        }
        return newRequest;
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{requestId}")
    public boolean approveOrDenyRequest(@Valid @RequestBody Request request, @PathVariable int requestId,
            Principal principal) {

        Request requestToUpdate = requestDao.getRequestById(requestId);
        requestDao.updateRequest(request, requestToUpdate.getRequestId());
        if (request.isApproveRequest()) {
            accountDao.withdrawAmount(request.getAmount(), request.getAccountFrom());
            accountDao.depositAmount(request.getAmount(), request.getAccountTo());
        }
        return true;

        // Account from = accountDao.findAccountByUserId((long)
        // userDao.findIdByUsername(principal.getName()));

        // if (from.getaccountId() != request.getAccountFrom() ||
        // accountDao.findByAccountId(request.getAccountTo()) == null) {
        // throw new AccessDeniedException("Invalid request made");
        // }

        // if (!request.isApproveRequest()) {
        // requestDao.updateRequest(request, requestId);
        // } else {
        // requestDao.updateRequest(request, requestId);
        // accountDao.withdrawAmount(request.getAmount(), request.getAccountFrom());
        // accountDao.depositAmount(request.getAmount(), request.getAccountTo());
        // }

        // return true;
    }

}