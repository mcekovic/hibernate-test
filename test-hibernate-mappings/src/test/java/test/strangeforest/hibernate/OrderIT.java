package test.strangeforest.hibernate;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.testng.*;
import org.strangeforest.hibernate.entities.*;
import org.strangeforest.hibernate.repositories.*;
import org.testng.annotations.*;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@ContextConfiguration(locations = "classpath:test-hibernate.xml")
public class OrderIT extends AbstractTestNGSpringContextTests {

	@Autowired private OrderRepository orders;
	private long orderId;

	@Test(dependsOnGroups = "SetUp")
	public void createOrder() {
		Order order = new Order();
		order.setDescription("Nabavka");
		order.setCreated(new Date());
		orders.create(order);
		orderId = order.getId();
		assertThat(getOrder().getDescription(), is(equalTo("Nabavka")));
	}

	@Test(dependsOnMethods = "createOrder")
	public void orderItemIsAdded() {
		Order order = getOrder();
		order.addItem(1, "Pasulj", 3);
		order.addItem(2, "Rotkvice", 2);
		order.addItem(3, "Biber", 1);
		orders.save(order);

		assertThat(getOrder().getItemCount(), is(equalTo(6)));
		assertThat(getOrder().getItemCount(), is(equalTo(6)));
		assertThat(getOrder().getItemCount(), is(equalTo(6)));
		assertThat(getOrder().getItemCount(), is(equalTo(6)));
	}



	// Util

	private Order getOrder() {
		return orders.findById(orderId);
	}
}
