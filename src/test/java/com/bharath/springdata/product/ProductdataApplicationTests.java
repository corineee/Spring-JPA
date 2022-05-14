package com.bharath.springdata.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.querydsl.QPageRequest;

import com.bharath.springdata.product.entities.Product;
import com.bharath.springdata.product.repos.ProductRepository;

@SpringBootTest
class ProductdataApplicationTests {

	@Autowired
	ProductRepository repository;

	@Test
	void contextLoads() {
	}

	@Test
	public void testCreate() {
		Product product = new Product();
		product.setId(1);
		product.setName("Iphone");
		product.setDecs("Awesome");
		product.setPrice(1000d);

		repository.save(product);
	}

	@Test
	public void testRead() {
		Product product = repository.findById(1).get();
		assertNotNull(product);
		assertEquals("Iphone", product.getName());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>" + product.getDecs());
	}

	@Test
	public void testUpdate() {
		Product product = repository.findById(1).get();
		product.setPrice(1200d);
		repository.save(product);
	}

	@Test
	public void testDelete() {
		if (repository.existsById(1)) { // id가 존재하는 경우에만 삭제
			repository.deleteById(1);
		}
	}

	@Test
	public void testCount() { // record 개수
		System.out.println("Total Records========>>>>>>>>>>" + repository.count());
	}

	@Test
	public void testFindByName() {
		List<Product> products = repository.findByName("TV");
		products.forEach(p -> System.out.println(p.getPrice()));
	}

	@Test
	public void testFindByNameAndDesc() {
		List<Product> products = repository.findByNameAndDesc("TV", "From Sansung Inc");
		products.forEach(p -> System.out.println(p.getPrice()));
	}

	@Test
	public void testFindByPriceGreaterThan() {
		List<Product> products = repository.findByPriceGreaterThan(1000d);
		products.forEach(p -> System.out.println(p.getPrice()));
	}

	@Test
	public void testFindByDescContains() {
		List<Product> products = repository.findByDescContains("Apple");
		products.forEach(p -> System.out.println(p.getPrice()));
	}

	@Test
	public void testFindByPriceBetween() {
		List<Product> products = repository.findByPriceBetween(500d, 2500d);
		products.forEach(p -> System.out.println(p.getPrice()));
	}

	@Test
	public void testFindByDescLike() {
		List<Product> products = repository.findByDescLike("%LG%");
		products.forEach(p -> System.out.println(p.getPrice()));
	}

	@Test
	public void testFindByIdsIn() {
		Pageable pageable = new QPageRequest(1, 2);
		List<Product> products = repository.findByIdsIn(Arrays.asList(1, 2, 3, 4), pageable);
		products.forEach(p -> System.out.println(p.getPrice()));
	}

	@Test
	public void testFindAllPaging() {
		Pageable pageable = new QPageRequest(0, 2);

		Page<Product> results = repository.findAll(pageable);
		results.forEach(p -> System.out.println(p.getName()));
	}

	@Test
	public void testFindAllSorting() {
		repository.findAll(new Sort(new Sort.Order(Direction.DESC, "name"), new Sort.Order("price")));

//		repository.findAll(new Sort(Direction.DESC ,"name", "price")).forEach(p->System.out.println(p.getName()));
	}

	@Test
	public void testFindAllPaginAndSortin() {
		Pageable pageable = new QPageRequest(0, 2, Direction.DESC, "name");
		repository.findAll(pageable).forEach(p -> System.out.println(p.getName()));
	}

}
