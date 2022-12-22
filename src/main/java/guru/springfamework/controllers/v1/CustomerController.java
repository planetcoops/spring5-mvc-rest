package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerService customerService,
                              CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<CustomerListDTO> getAllCategories() {
        return new ResponseEntity<CustomerListDTO>(new CustomerListDTO(customerService.getAllCustomers()), HttpStatus.OK);
    }

    @GetMapping("/{lastname}")
    public ResponseEntity<CustomerDTO> getCustomerByIdOrLastname(@PathVariable String lastname) {
        if (lastname.matches("[0-9]+")) {
            return new ResponseEntity<CustomerDTO>(customerService.getCustomerById(Long.parseLong(lastname)), HttpStatus.OK);
        } else {
            return new ResponseEntity<CustomerDTO>(customerService.getCustomerByLastname(lastname), HttpStatus.OK);
        }
    }

    @PostMapping({"", "/"})
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customerDTO) {
        return customerService.createNewCustomer(customerDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO existingDTO = customerService.getCustomerById(id);
        if (existingDTO != null) {
            existingDTO.setFirstname(customerDTO.getFirstname());
            existingDTO.setLastname(customerDTO.getLastname());
            return customerService.updateCustomer(existingDTO);
        }
        return null;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        CustomerDTO existingDTO = customerService.getCustomerById(id);
        if (existingDTO != null) {
            customerDTO.setId(id);
            return customerService.patchCustomer(customerDTO);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomerById(id);
    }
}
