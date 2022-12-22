package guru.springfamework.controllers.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import junit.framework.TestCase;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static guru.springfamework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest extends TestCase {

    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    public void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    public void testGetAllCustomers() throws Exception {
        //given
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setId(1L);
        customerDTO1.setLastname("Fruits");
        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setId(2L);
        customerDTO2.setLastname("Vegetables");

        List<CustomerDTO> customers = Arrays.asList(customerDTO1, customerDTO2);
        when(customerService.getAllCustomers()).thenReturn(customers);

        //when, then
        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    public void testGetCustomerByLastname() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(1L);
        customerDTO.setLastname("Fruits");
        when(customerService.getCustomerByLastname(anyString())).thenReturn(customerDTO);

        //when, then
        mockMvc.perform(get("/api/v1/customers/Fruits")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastname", equalTo(customerDTO.getLastname())));
    }

    public void testCreateNewCustomer() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Summer");
        customerDTO.setLastname("Fruits");

        CustomerDTO savedCustomer = new CustomerDTO();
        savedCustomer.setId(1L);
        savedCustomer.setFirstname(customerDTO.getFirstname());
        savedCustomer.setLastname(customerDTO.getLastname());
        when(customerService.createNewCustomer(any(CustomerDTO.class))).thenReturn(savedCustomer);

        //when, then
        mockMvc.perform(post("/api/v1/customers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.lastname", equalTo(savedCustomer.getLastname())));
    }

    public void testUpdateCustomer() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname("Summer");
        customerDTO.setLastname("Fruits");

        CustomerDTO existingCustomer = new CustomerDTO();
        existingCustomer.setId(1L);
        existingCustomer.setFirstname("Amanda");
        existingCustomer.setLastname("Darnton");
        when(customerService.getCustomerById(any(Long.class))).thenReturn(existingCustomer);
        when(customerService.updateCustomer(any(CustomerDTO.class))).thenReturn(existingCustomer);

        //when, then
        mockMvc.perform(put("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(customerDTO.getFirstname())))
                .andExpect(jsonPath("$.lastname", equalTo(customerDTO.getLastname())));
    }

    public void testPatchCustomer() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname("Baird");

        CustomerDTO existingCustomer = new CustomerDTO();
        existingCustomer.setId(1L);
        existingCustomer.setFirstname("Amanda");
        existingCustomer.setLastname("Darnton");
        when(customerService.getCustomerById(any(Long.class))).thenReturn(existingCustomer);
        CustomerDTO patchedCustomer = new CustomerDTO();
        patchedCustomer.setId(existingCustomer.getId());
        patchedCustomer.setFirstname(existingCustomer.getFirstname());
        patchedCustomer.setLastname(customerDTO.getLastname());
        when(customerService.patchCustomer(any(CustomerDTO.class))).thenReturn(patchedCustomer);

        //when, then
        mockMvc.perform(patch("/api/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname", equalTo(existingCustomer.getFirstname())))
                .andExpect(jsonPath("$.lastname", equalTo(customerDTO.getLastname())));
    }

    public void testDeleteCustomer() throws Exception {
        //given

        //when, then
        mockMvc.perform(delete("/api/v1/customers/1"))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(1L);
    }
}