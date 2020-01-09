package edu.uark.registerapp.commands.products;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.entities.ProductEntity;
import edu.uark.registerapp.models.repositories.ProductRepository;

@Service
public class ProductByLookupCodeQuery implements ResultCommandInterface<Product> {
	@Override
	public Product execute() {
		this.validateProperties();
		
		final Optional<ProductEntity> productEntity =
			this.productRepository.findByLookupCode(this.lookupCode);
		if (productEntity.isPresent()) {
			return new Product(productEntity.get());
		} else {
			throw new NotFoundException("Product");
		}
	}

	// Helper methods
	private void validateProperties() {
		if (StringUtils.isBlank(this.lookupCode)) {
			throw new UnprocessableEntityException("lookupcode");
		}
	}

	// Properties
	private String lookupCode;
	public String getLookupCode() {
		return this.lookupCode;
	}
	public ProductByLookupCodeQuery setLookupCode(final String lookupCode) {
		this.lookupCode = lookupCode;
		return this;
	}

	@Autowired
	private ProductRepository productRepository;
}
