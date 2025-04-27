package com.naizfit.app.domain.products;

import java.util.Objects;
import java.util.UUID;

import com.naizfit.app.domain.products.vo.Color;
import com.naizfit.app.domain.products.vo.Pictures;
import com.naizfit.app.domain.products.vo.ProductId;
import com.naizfit.app.domain.products.vo.Size;
import com.naizfit.app.domain.products.vo.Sizes;
import com.naizfit.app.domain.products.vo.Sku;

public class Product {
	
	// ───── FIELDS ──────────────────────────────────────────────────────────
	private final ProductId id;
	private final Sku sku;
	private final Sizes sizes;
	private final Pictures pictures;
	private final Brand brand;
	private final Color color;
	
	// ───── CONSTRUCTOR ─────────────────────────────────────────────────────
	private Product (final ProductId id,
					 final Sku sku,
					 final Sizes sizes,
					 final Pictures pictures,
					 final Brand brand,
					 final Color color) {
		
		this.id = Objects.requireNonNull(id, "ID must not be null");
		this.sku = Objects.requireNonNull(sku , "sku must not be null");
		this.sizes = Objects.requireNonNull(sizes, "sizes must not be null");
		this.pictures = Objects.requireNonNull(pictures, "pictures must not be null");
		this.brand = Objects.requireNonNull(brand, "brand must not be null");
		this.color = Objects.requireNonNull(color, "color must not be null");
	}

	// ───── FACTORIES ───────────────────────────────────────────────────────
	/**
	 * Create a new Product with random UUID.
	 * @param sku
	 * @param sizes
	 * @param pictures
	 * @param brand
	 * @param color
	 * @return
	 */
	public static Product create(final Sku sku,
			 					 final Sizes sizes,
			 					 final Pictures pictures,
			 					 final Brand brand,
			 					 final Color color) {
		
		return new Product(ProductId.newId(),
						   sku,
						   sizes,
						   pictures,
						   brand,
						   color);
	}
	/**
	 * Creates a Product instance from persistence data
	 * BEWARE! --> For repository/infrastructure layer use only
	 * @param id
	 * @param sku
	 * @param sizes
	 * @param pictures
	 * @param brand
	 * @param color
	 * @return
	 */
	public static Product reconstitute(final ProductId id,
									   final Sku sku,
								 	   final Sizes sizes,
								 	   final Pictures pictures,
								 	   final Brand brand,
								 	   final Color color) {

		return new Product(id,
						  sku,
						  sizes,
						  pictures,
						  brand,
						  color);
	}
	
	// ───── BUSINESS METHODS ───────────────────────────────────────────────
	public boolean hasSize(Size size) {
		return sizes.contains(size);
	}
	public Product addSize(final Size size) {
        return new Product(id, 
        				   sku, 
        				   sizes.add(size), 
        				   pictures, 
        				   brand, 
        				   color
        );
    }
    public Product removeSize(final Size size) {
        return new Product(id,
        				   sku,
        				   sizes.remove(size),
        				   pictures,
        				   brand,
        				   color
        );
    }

	// ───── GETTERS ────────────────────────────────────────────────────────
	public ProductId getId() {
		return id;
	}
	public Sku getSku() {
		return sku;
	}
	public Sizes getSizes() {
		return sizes;
	}
	public Pictures getPictures() {
		return pictures;
	}
	public Brand getBrand() {
		return brand;
	}
	public Color getColor() {
		return color;
	}
	
	// ───── OVERRIDES ──────────────────────────────────────────────────────
		@Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Product)) return false;
	        Product that = (Product) o;
	        return Objects.equals(id, that.id);
	    }
	    @Override
	    public int hashCode() {
	        return Objects.hash(id);
	    }
	    @Override
	    public String toString() {
	    	// too verbose if use fields pictures and sizes
	        return "Product[id=%s, sku=%s, brand=%s]".formatted(id, sku, brand.getName());
	    }
	
}
