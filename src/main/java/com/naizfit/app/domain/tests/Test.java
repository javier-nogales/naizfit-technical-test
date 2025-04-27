package com.naizfit.app.domain.tests;

import java.util.Objects;
import java.util.UUID;

import com.naizfit.app.domain.products.Product;
import com.naizfit.app.domain.testers.Tester;
import com.naizfit.app.domain.tests.vo.Size;
import com.naizfit.app.domain.tests.vo.TestId;

public class Test {
	
	// ───── FIELDS ──────────────────────────────────────────────────────────
	private final TestId id;
	private final Tester tester;
	private final Product product;
	private final Size size;
	
	// ───── CONSTRUCTOR ─────────────────────────────────────────────────────
	private Test(final TestId id,
				 final Tester tester,
				 final Product product,
				 final Size size) {
		
		this.id = Objects.requireNonNull(id, "ID must be not null");
		this.tester = Objects.requireNonNull(tester, "tester must be not null");
		this.product = Objects.requireNonNull(product, "product must be not null");
		this.size = Objects.requireNonNull(size, "size must be not null");
	}
	
	// ───── FACTORIES ───────────────────────────────────────────────────────
	/**
	 * Create a new Test with random UUID.
	 * @param tester
	 * @param product
	 * @param size
	 * @return
	 */
	public static Test create(final Tester tester,
			 				  final Product product,
			 				  final Size size) {
		
		return new Test(TestId.newId(),
						tester, product, size);
	}
	/**
	 * Creates a Test instance from persistence data
	 * BEWARE! --> For repository/infrastructure layer use only
	 * @param id
	 * @param tester
	 * @param product
	 * @param size
	 * @return
	 */
	public static Test reconstitute(final TestId id,
									final Tester tester,
									final Product product,
									final Size size) {
		return new Test(id,
						tester, product, size);
	}
	
	// ───── BUSINESS METHODS ───────────────────────────────────────────────
	

	// ───── GETTERS ────────────────────────────────────────────────────────
	public TestId getId() {
		return id;
	}
	public Tester getTester() {
		return tester;
	}
	public Product getProduct() {
		return product;
	}
	public Size getSize() {
		return size;
	}
	
	// ───── OVERRIDES ──────────────────────────────────────────────────────
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Test)) return false;
        Test that = (Test) o;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        return String.format(
            "Test[id=%s, tester=%s, product=%s, size=%s]",
            id,
            tester.getName(),
            product.getSku(),
            size
        );
    }

	
}
 