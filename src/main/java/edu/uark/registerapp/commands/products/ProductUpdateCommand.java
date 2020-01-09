package edu.uark.registerapp.commands.products;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.entities.ProductEntity;
import edu.uark.registerapp.models.repositories.ProductRepository;

@Service
public class ProductUpdateCommand implements ResultCommandInterface<Product> {
	@Transactional
	@Override
	public Product execute() {
		this.validateProperties();

		final Optional<ProductEntity> productEntity =
			this.productRepository.findById(this.productId);
		if (!productEntity.isPresent()) { // No record with the associated record ID exists in the database.
			throw new NotFoundException("Product");
		}

		// Synchronize any incoming changes for UPDATE to the database.
		this.apiProduct = productEntity.get().synchronize(this.apiProduct);

		// Write, via an UPDATE, any changes to the database.
		this.productRepository.save(productEntity.get());

		return this.apiProduct;
	}

	// Helper methods
	private void validateProperties() {
		if (StringUtils.isBlank(this.apiProduct.getLookupCode())) {
			throw new UnprocessableEntityException("lookupcode");
		}
	}

	// Properties
	private UUID productId;
	public UUID getProductId() {
		return this.productId;
	}
	public ProductUpdateCommand setProductId(final UUID productId) {
		this.productId = productId;
		return this;
	}

	private Product apiProduct;
	public Product getApiProduct() {
		return this.apiProduct;
	}
	public ProductUpdateCommand setApiProduct(final Product apiProduct) {
		this.apiProduct = apiProduct;
		return this;
	}
	
	@Autowired
	private ProductRepository productRepository;
}
