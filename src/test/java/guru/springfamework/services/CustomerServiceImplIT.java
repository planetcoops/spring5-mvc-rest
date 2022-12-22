package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.bootstrap.Bootstrap;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.repositories.CustomerRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class) // Spring context
@DataJpaTest // Repositories
public class CustomerServiceImplIT {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CustomerRepository customerRepository;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        System.out.println("Loading Customer Data");
        System.out.println(customerRepository.findAll().size());

        // Setup data for testing
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    public void testPatchCustomerFirstname() {
        String updatedName = "Betty";
        long id = getCustomerIdValue();
        Customer customer = customerRepository.getOne(id);
        String firstname = customer.getFirstname();
        String lastname = customer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(id);
        customerDTO.setFirstname(updatedName);

        customerService.patchCustomer(customerDTO);
        Customer patchedCustomer = customerRepository.getOne(id);
        assertNotNull(patchedCustomer);
        assertEquals(updatedName, patchedCustomer.getFirstname());
        assertEquals(lastname, patchedCustomer.getLastname());
    }

    @Test
    public void testPatchCustomerLastname() {
        String updatedName = "Boothroyd";
        long id = getCustomerIdValue();
        Customer customer = customerRepository.getOne(id);
        String firstname = customer.getFirstname();
        String lastname = customer.getLastname();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(id);
        customerDTO.setLastname(updatedName);

        customerService.patchCustomer(customerDTO);
        Customer patchedCustomer = customerRepository.getOne(id);
        assertNotNull(patchedCustomer);
        assertEquals(firstname, patchedCustomer.getFirstname());
        assertEquals(updatedName, patchedCustomer.getLastname());
    }

    private Long getCustomerIdValue() {
        List<Customer> customerList = customerRepository.findAll();
        System.out.println("Customers found: " + customerList.size());
        return customerList.get(0).getId();
    }
}
