package com.jumia.task.core.controller;

import com.jumia.task.core.dao.PhoneNumberDAO;
import com.jumia.task.core.service.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/phone-numbers")
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    @Autowired
    public PhoneNumberController(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @GetMapping
    public List<PhoneNumberDAO> listPhoneNumbers(@RequestParam(required = false) String countryName,
                                                 @RequestParam(required = false) Boolean isValid){

        return phoneNumberService.listPhoneNumbers(countryName, isValid);
    }
}
