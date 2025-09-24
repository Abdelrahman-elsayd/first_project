package example;

import com.example.entities.Customer;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
class CustomerControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setName("ali");
        customer.setEmail("ali@example.com");

        HttpRequest<Customer> request = HttpRequest.POST("/customers", customer);
        Customer response = client.toBlocking().retrieve(request, Customer.class);

        assertNotNull(response.getId());
        assertEquals("ali", response.getName());
        assertEquals("ali@example.com", response.getEmail());
    }

    @Test
    void testGetCustomerById() {
        Customer customer = new Customer();
        customer.setName("Jane Doe");
        customer.setEmail("jane@example.com");

        Customer created = client.toBlocking().retrieve(HttpRequest.POST("/customers", customer), Customer.class);
        Customer fetched = client.toBlocking().retrieve(HttpRequest.GET("/customers/" + created.getId()), Customer.class);

        assertEquals(created.getId(), fetched.getId());
        assertEquals("Jane Doe", fetched.getName());
    }

    @Test
    void testUpdateCustomer() {
        Customer customer = new Customer();
        customer.setName("Old");
        customer.setEmail("old@example.com");

        Customer created = client.toBlocking().retrieve(HttpRequest.POST("/customers", customer), Customer.class);;
        created.setName("New");
        created.setEmail("new@example.com");
        Customer updated = client.toBlocking().retrieve(HttpRequest.PUT("/customers/" + created.getId(), created), Customer.class);

        assertEquals("New Name", updated.getName());
        assertEquals("new@example.com", updated.getEmail());
    }

    @Test
    void testDeleteCustomer() {
        Customer customer = new Customer();
        customer.setName("ToDelete");
        customer.setEmail("delete@example.com");

        Customer created = client.toBlocking().retrieve(HttpRequest.POST("/customers", customer), Customer.class);

        HttpResponse<?> deleteResponse = client.toBlocking().exchange(HttpRequest.DELETE("/customers/" + created.getId()));
        assertEquals(204, deleteResponse.getStatus().getCode());

        assertThrows(Exception.class, () -> client.toBlocking().retrieve(HttpRequest.GET("/customers/" + created.getId()), Customer.class));
    }
    @Test
    void testGetAllCustomers(){
        Customer customer = new Customer();
        customer.setName(" ali");
        customer.setEmail("ali@example.com");
        HttpRequest<Customer> request = HttpRequest.POST("/customers", customer);
        Customer response = client.toBlocking().retrieve(request, Customer.class);

        Customer customer2 = new Customer();
        customer2.setName(" ahmed");
        customer2.setEmail("ahmed@example.com");
        HttpRequest<Customer> request2 = HttpRequest.POST("/customers", customer2);
        Customer response2 = client.toBlocking().retrieve(request2, Customer.class);

        List<Customer> allCustomers = client.toBlocking().retrieve(HttpRequest.GET("/customers"), Argument.listOf(Customer.class));

        allCustomers.forEach(c -> System.out.println("Customer: " + c.getId() + " - " + c.getName() + " - " + c.getEmail()));
    }
}
