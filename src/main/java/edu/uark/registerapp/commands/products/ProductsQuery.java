package edu.uark.registerapp.commands.products;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.entities.ProductEntity;
import edu.uark.registerapp.models.repositories.ProductRepository;

@Service
public class ProductsQuery implements ResultCommandInterface<List<Product>> {
	@Override
	public List<Product> execute() {
		final LinkedList<Product> products = new LinkedList<Product>();

		for (final ProductEntity productEntity : productRepository.findAll()) {
			products.addLast(new Product(productEntity));
		}
		
		return products;
	}

	@Autowired
	ProductRepository productRepository;
}
