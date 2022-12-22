package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return CustomerMapper.INSTANCE.map(customerRepository.findAll());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        return CustomerMapper.INSTANCE.customerToCustomerDTO(customerRepository.findById(id).orElse(null));
    }

    @Override
    public CustomerDTO getCustomerByLastname(String lastname) {
        return CustomerMapper.INSTANCE.customerToCustomerDTO(customerRepository.findCustomerByLastname(lastname).orElse(null));
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        return CustomerMapper.INSTANCE.customerToCustomerDTO(customerRepository.save(CustomerMapper.INSTANCE.customerDTOToCustomer(customerDTO)));
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        return CustomerMapper.INSTANCE.customerToCustomerDTO(customerRepository.save(CustomerMapper.INSTANCE.customerDTOToCustomer(customerDTO)));
    }

    @Override
    public CustomerDTO patchCustomer(CustomerDTO customerDTO) {
        return customerRepository.findById(customerDTO.getId()).map(customer -> {
            if (customerDTO.getFirstname() != null) {
                customer.setFirstname(customerDTO.getFirstname());
            }
            if (customerDTO.getLastname() != null) {
                customer.setLastname(customerDTO.getLastname());
            }
            return CustomerMapper.INSTANCE.customerToCustomerDTO(customerRepository.save(customer));
        }).orElse(null);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
