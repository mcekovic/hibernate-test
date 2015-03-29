package test.strangeforest.hibernate;

import java.math.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.test.context.*;
import org.springframework.test.context.testng.*;
import org.strangeforest.hibernate.entities.*;
import org.strangeforest.hibernate.repositories.*;
import org.testng.annotations.*;

import static org.assertj.core.api.Assertions.*;

@ContextConfiguration(locations = "classpath:test-hibernate.xml")
public class OrderIT extends AbstractTestNGSpringContextTests {

	@Autowired private OrderRepository orders;
	private long orderId;

	@Test(dependsOnGroups = "SetUp")
	public void createOrder() {
		Order order = new Order("Nabavka");
		orders.create(order);
		orderId = order.getId();
		assertThat(getOrder().getDescription()).isEqualTo("Nabavka");
	}

	@Test(dependsOnMethods = "createOrder")
	public void orderItemsAreAdded() {
		Order order = getOrder();
		order.addItem(1, "Pasulj", 3);
		order.addItem(2, "Rotkvice", 2);
		order.addItem(3, "Biber", 1);
		orders.save(order);

		assertThat(getOrder().getItemCount()).isEqualTo(6);
		assertThat(getOrder().getItemCount()).isEqualTo(6);
		assertThat(getOrder().getItemCount()).isEqualTo(6);
		assertThat(getOrder().getItemCount()).isEqualTo(6);
	}

	@Test(dependsOnMethods = "orderItemsAreAdded")
	public void orderItemsAreUpdated() {
		Order order = getOrder();
		order.getItem(1).setCount(5);
		order.getItem(2).setCount(4);
		orders.save(order);

		assertThat(getOrder().getItemCount()).isEqualTo(10);
	}

	@Test(dependsOnMethods = "orderItemsAreUpdated")
	public void orderPaymentIsAdded() {
		Order order = getOrder();
		order.addPayment(1, new BigDecimal("5"));
		order.getItem(2).setCount(4);
		orders.save(order);

		assertThat(getOrder().getPayment(1).getAmount(), comparesEqualTo(new BigDecimal("5")));
	}

	@Test(dependsOnMethods = "orderPaymentIsAdded")
	public void orderPaymentIsUpdated() {
		Order order = getOrder();
		order.getPayment(1).setAmount(new BigDecimal("6"));
		orders.save(order);

		assertThat(getOrder().getPayment(1).getAmount(), comparesEqualTo(new BigDecimal("6")));
	}

	@Test(dependsOnMethods = "orderPaymentIsUpdated")
	public void newOrderPaymentIsAdded() {
		Order order = getOrder();
		order.addPayment(2, new BigDecimal("10"));
		orders.save(order);

		assertThat(getOrder().getPaymentAmount(), comparesEqualTo(new BigDecimal("16")));
	}


	// Util

	private Order getOrder() {
		return orders.find(orderId);
	}
}
