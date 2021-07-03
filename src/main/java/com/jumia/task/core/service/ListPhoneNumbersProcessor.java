package com.jumia.task.core.service;

import com.jumia.task.core.model.Customer;
import com.jumia.task.core.repo.CustomerRepository;
import com.jumia.task.core.dto.PhoneNumberDTO;
import com.jumia.task.core.model.Country;
import com.jumia.task.core.repo.CountryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListPhoneNumbersProcessor {
    private final CustomerRepository customerRepository;
    private final CountryRepository countryRepository;
    private final String countryName;
    private final Boolean isValid;

    public ListPhoneNumbersProcessor(String countryName, Boolean isValid, CustomerRepository customerRepository, CountryRepository countryRepository) {
        this.countryName = countryName;
        this.isValid = isValid;
        this.customerRepository = customerRepository;
        this.countryRepository = countryRepository;
    }

    public List<PhoneNumberDTO> execute(){
        List<Customer> customers = customerRepository.findAll();
        List<Customer> customersFilteredByPhoneValidity = filterCustomersByPhoneValidity(customers, isValid);
        List<Customer> customersFilteredByCountryName = filterCustomersByCountryName(customersFilteredByPhoneValidity, countryName);

        return constructPhoneNumbersList(customersFilteredByCountryName);
    }

    private List<Customer> filterCustomersByPhoneValidity(List<Customer> customers, Boolean isValid){
        if(isValid == null){ return customers; }
        else {
            List<Country> countries = countryRepository.findAll();
            List<String> validRegularExpressions = countries.stream().map(Country::getRegex).collect(Collectors.toList());
            return customers.stream().filter(customer -> validatePhoneNumber(customer.getPhone(), validRegularExpressions) == isValid)
                    .collect(Collectors.toList());
        }
    }

    private List<Customer> filterCustomersByCountryName(List<Customer> customers, String countryName){
        if(countryName == null){ return customers; }
        else {
            Country country = countryRepository.findByName(countryName).orElse(new Country());
            return customers.stream().filter(customer -> extractCountryCodeFromFullPhoneNumber(customer.getPhone()).equals(country.getCode()))
                    .collect(Collectors.toList());
        }
    }

    private String extractCountryCodeFromFullPhoneNumber(String phoneNumber){
        return phoneNumber.split(" ")[0].replaceAll("[()]", "");
    }

    private String extractNumberFromFullPhoneNumber(String phoneNumber){
        return phoneNumber.split(" ")[1];
    }

    private Boolean validatePhoneNumber(String phoneNumber, List<String> validRegularExpressions){
        for(String validRegex : validRegularExpressions){
            if(phoneNumber.matches(validRegex)) return true;
        }
        return false;
    }

    private ArrayList<PhoneNumberDTO> constructPhoneNumbersList(List<Customer> customers){

        ArrayList<PhoneNumberDTO> phoneNumbers = new ArrayList<>();
        List<Country> countries = countryRepository.findAll();

        for(Customer customer : customers){
            String countryCode = extractCountryCodeFromFullPhoneNumber(customer.getPhone());
            String number = extractNumberFromFullPhoneNumber(customer.getPhone());

            Country country = countries.stream().filter( c -> c.getCode().equals(countryCode)).findFirst().orElse(new Country());
            String countryName = country.getName();
            String countryValidRegex = country.getRegex();

            Boolean isValid = validatePhoneNumber(customer.getPhone(), List.of(countryValidRegex));

            phoneNumbers.add(new PhoneNumberDTO(countryName, isValid, countryCode, number));
        }

        return phoneNumbers;
    }
}
