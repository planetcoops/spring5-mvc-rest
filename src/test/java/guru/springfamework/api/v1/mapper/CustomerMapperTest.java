package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import junit.framework.TestCase;

import java.util.Arrays;
import java.util.List;

public class CustomerMapperTest extends TestCase {

    public static final long ID = 1L;
    public static final String FIRSTNAME = "Amanda";
    public static final String LASTNAME = "Darnton";
    CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    public void testCustomerToCustomerDTO() {
        //Given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);

        //When
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);

        //Then
        assertEquals(customerDTO.getId(), customer.getId());
        assertEquals(customerDTO.getFirstname(), customer.getFirstname());
        assertEquals(customerDTO.getLastname(), customer.getLastname());
    }

    public void testMap() {
        //Given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstname(FIRSTNAME);
        customer.setLastname(LASTNAME);

        Customer customer1 = new Customer();
        customer1.setId(ID);
        customer1.setFirstname(FIRSTNAME);
        customer1.setLastname(LASTNAME);

        //When
        List<CustomerDTO> customerDTOList = customerMapper.map(Arrays.asList(customer, customer1));

        //Then
        assertEquals(customerDTOList.size(), 2);
    }
}