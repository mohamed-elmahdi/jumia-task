package com.jumia.task.core.service;

import com.jumia.task.core.dao.PhoneNumberDAO;
import com.jumia.task.core.repo.CountryRepository;
import com.jumia.task.core.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhoneNumberService {

    private final CustomerRepository customerRepository;
    private final CountryRepository countryRepository;

    @Autowired
    public PhoneNumberService(CustomerRepository customerRepository, CountryRepository countryRepository) {
        this.customerRepository = customerRepository;
        this.countryRepository = countryRepository;
    }

    public List<PhoneNumberDAO> listPhoneNumbers(String countryName, Boolean isValid){

        return new ListPhoneNumbersProcessor(countryName, isValid, customerRepository, countryRepository).execute();

    }
}
