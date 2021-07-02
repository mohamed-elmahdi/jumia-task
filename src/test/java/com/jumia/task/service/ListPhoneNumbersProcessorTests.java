package com.jumia.task.service;

import com.jumia.task.core.dao.PhoneNumberDAO;
import com.jumia.task.core.model.Customer;
import com.jumia.task.core.repo.CustomerRepository;
import com.jumia.task.core.service.ListPhoneNumbersProcessor;
import com.jumia.task.core.model.Country;
import com.jumia.task.core.repo.CountryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

public class ListPhoneNumbersProcessorTests {
    private final CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
    private final CountryRepository countryRepository = Mockito.mock(CountryRepository.class);

    @Test
    void ListingPhoneNumbersWithNoFiltersReturnsAllRecords(){
        Customer invalidCustomerFromMorocco = new Customer("Customer 1", "(212) 6007989253");
        Customer customerFromMozambique = new Customer("Customer 2", "(258) 847651504");
        Customer customerFromUganda = new Customer("Customer 3", "(256) 775069443");
        Customer invalidCustomerFromEthiopia = new Customer("Customer 4", "(251) 9773199405");

        List<Customer> customers = List.of(invalidCustomerFromMorocco, customerFromMozambique, customerFromUganda, invalidCustomerFromEthiopia);
        Mockito.when(customerRepository.findAll()).thenReturn(customers);

        Country cameroon = new Country("Cameroon", "237", "\\(237\\)\\ ?[2368]\\d{7,8}$");
        Country ethiopia = new Country("Ethiopia", "251", "\\(251\\)\\ ?[1-59]\\d{8}$");
        Country morocco = new Country("Morocco", "212", "\\(212\\)\\ ?[5-9]\\d{8}$");
        Country mozambique = new Country("Mozambique", "258", "\\(258\\)\\ ?[28]\\d{7,8}$");
        Country uganda = new Country("Uganda", "256", "\\(256\\)\\ ?\\d{9}$");

        List<Country> countries = List.of(cameroon, ethiopia, morocco, uganda, mozambique);
        Mockito.when(countryRepository.findAll()).thenReturn(countries);

        List<PhoneNumberDAO> response = new ListPhoneNumbersProcessor(null, null, customerRepository, countryRepository).execute();

        Assertions.assertEquals(4, response.size());
    }

    @Test
    void  ListingPhoneNumbersWithOnlyCountryFilterWorks(){
        Customer invalidCustomerFromMorocco = new Customer("Customer 1", "(212) 6007989253");
        Customer customerFromMozambique = new Customer("Customer 2", "(258) 847651504");
        Customer customerFromUganda = new Customer("Customer 3", "(256) 775069443");
        Customer invalidCustomerFromEthiopia = new Customer("Customer 4", "(251) 9773199405");
        Customer anotherInvalidCustomerFromMorocco = new Customer("Customer 5", "(212) 6617344445");

        List<Customer> customers = List.of(invalidCustomerFromMorocco, customerFromMozambique, customerFromUganda, invalidCustomerFromEthiopia,
                anotherInvalidCustomerFromMorocco);
        Mockito.when(customerRepository.findAll()).thenReturn(customers);

        Country cameroon = new Country("Cameroon", "237", "\\(237\\)\\ ?[2368]\\d{7,8}$");
        Country ethiopia = new Country("Ethiopia", "251", "\\(251\\)\\ ?[1-59]\\d{8}$");
        Country morocco = new Country("Morocco", "212", "\\(212\\)\\ ?[5-9]\\d{8}$");
        Country mozambique = new Country("Mozambique", "258", "\\(258\\)\\ ?[28]\\d{7,8}$");
        Country uganda = new Country("Uganda", "256", "\\(256\\)\\ ?\\d{9}$");

        List<Country> countries = List.of(cameroon, ethiopia, morocco, uganda, mozambique);
        Mockito.when(countryRepository.findAll()).thenReturn(countries);
        Mockito.when(countryRepository.findByName("Morocco")).thenReturn(Optional.of(morocco));

        List<PhoneNumberDAO> response = new ListPhoneNumbersProcessor("Morocco", null, customerRepository, countryRepository).execute();

        Assertions.assertEquals(2, response.size());

        Assertions.assertEquals("212", response.get(0).getCountryCode());
        Assertions.assertEquals("6007989253", response.get(0).getNumber());
        Assertions.assertEquals("Morocco", response.get(0).getCountry());
        Assertions.assertEquals(false, response.get(0).getValid());

        Assertions.assertEquals("212", response.get(1).getCountryCode());
        Assertions.assertEquals("6617344445", response.get(1).getNumber());
        Assertions.assertEquals("Morocco", response.get(1).getCountry());
        Assertions.assertEquals(false, response.get(1).getValid());
    }

    @Test
    void  ListingPhoneNumbersWithOnlyValidityFilterWorks(){
        Customer invalidCustomerFromMorocco = new Customer("Customer 1", "(212) 6007989253");
        Customer customerFromMozambique = new Customer("Customer 2", "(258) 847651504");
        Customer customerFromUganda = new Customer("Customer 3", "(256) 775069443");
        Customer invalidCustomerFromEthiopia = new Customer("Customer 4", "(251) 9773199405");
        Customer anotherCustomerFromMorocco = new Customer("Customer 5", "(212) 6617344445");

        List<Customer> customers = List.of(invalidCustomerFromMorocco, customerFromMozambique, customerFromUganda, invalidCustomerFromEthiopia,
                anotherCustomerFromMorocco);
        Mockito.when(customerRepository.findAll()).thenReturn(customers);

        Country cameroon = new Country("Cameroon", "237", "\\(237\\)\\ ?[2368]\\d{7,8}$");
        Country ethiopia = new Country("Ethiopia", "251", "\\(251\\)\\ ?[1-59]\\d{8}$");
        Country morocco = new Country("Morocco", "212", "\\(212\\)\\ ?[5-9]\\d{8}$");
        Country mozambique = new Country("Mozambique", "258", "\\(258\\)\\ ?[28]\\d{7,8}$");
        Country uganda = new Country("Uganda", "256", "\\(256\\)\\ ?\\d{9}$");

        List<Country> countries = List.of(cameroon, ethiopia, morocco, uganda, mozambique);
        Mockito.when(countryRepository.findAll()).thenReturn(countries);

        List<PhoneNumberDAO> response = new ListPhoneNumbersProcessor(null, false, customerRepository, countryRepository).execute();

        Assertions.assertEquals(3, response.size());

        Assertions.assertEquals("212", response.get(0).getCountryCode());
        Assertions.assertEquals("6007989253", response.get(0).getNumber());
        Assertions.assertEquals("Morocco", response.get(0).getCountry());
        Assertions.assertEquals(false, response.get(0).getValid());

        Assertions.assertEquals("251", response.get(1).getCountryCode());
        Assertions.assertEquals("9773199405", response.get(1).getNumber());
        Assertions.assertEquals("Ethiopia", response.get(1).getCountry());
        Assertions.assertEquals(false, response.get(1).getValid());

        Assertions.assertEquals("212", response.get(2).getCountryCode());
        Assertions.assertEquals("6617344445", response.get(2).getNumber());
        Assertions.assertEquals("Morocco", response.get(2).getCountry());
        Assertions.assertEquals(false, response.get(2).getValid());
    }

    @Test
    void  ListingPhoneNumbersWithBothFiltersWorks(){
        Customer invalidCustomerFromMorocco = new Customer("Customer 1", "(212) 6007989253");
        Customer customerFromMozambique = new Customer("Customer 2", "(258) 847651504");
        Customer customerFromUganda = new Customer("Customer 3", "(256) 775069443");
        Customer invalidCustomerFromEthiopia = new Customer("Customer 4", "(251) 9773199405");
        Customer anotherCustomerFromMorocco = new Customer("Customer 5", "(212) 6617344445");
        Customer customerFromMorocco = new Customer("Customer 6", "(212) 633963130");

        List<Customer> customers = List.of(invalidCustomerFromMorocco, customerFromMozambique, customerFromUganda, invalidCustomerFromEthiopia,
                anotherCustomerFromMorocco,  customerFromMorocco);
        Mockito.when(customerRepository.findAll()).thenReturn(customers);

        Country cameroon = new Country("Cameroon", "237", "\\(237\\)\\ ?[2368]\\d{7,8}$");
        Country ethiopia = new Country("Ethiopia", "251", "\\(251\\)\\ ?[1-59]\\d{8}$");
        Country morocco = new Country("Morocco", "212", "\\(212\\)\\ ?[5-9]\\d{8}$");
        Country mozambique = new Country("Mozambique", "258", "\\(258\\)\\ ?[28]\\d{7,8}$");
        Country uganda = new Country("Uganda", "256", "\\(256\\)\\ ?\\d{9}$");

        List<Country> countries = List.of(cameroon, ethiopia, morocco, uganda, mozambique);
        Mockito.when(countryRepository.findAll()).thenReturn(countries);
        Mockito.when(countryRepository.findByName("Morocco")).thenReturn(Optional.of(morocco));

        List<PhoneNumberDAO> response = new ListPhoneNumbersProcessor("Morocco", true, customerRepository, countryRepository).execute();

        Assertions.assertEquals(1, response.size());

        Assertions.assertEquals("212", response.get(0).getCountryCode());
        Assertions.assertEquals("633963130", response.get(0).getNumber());
        Assertions.assertEquals("Morocco", response.get(0).getCountry());
        Assertions.assertEquals(true, response.get(0).getValid());
    }
}
