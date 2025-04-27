package com.naizfit.app.domain.products;

import java.util.Objects;
import java.util.UUID;

import com.naizfit.app.domain.products.vo.Logo;
import com.naizfit.app.domain.shared.vo.Name;

final class Brand {

	// ───── FIELDS ──────────────────────────────────────────────────────────
	private final UUID id;
	private final Name name;
	private final Logo logo;
	
	// ───── CONSTRUCTOR ─────────────────────────────────────────────────────
	private Brand (final UUID id,
				   final Name name,
				   final Logo logo) {
		
		this.id = Objects.requireNonNull(id, "ID must not be null");
		this.name = Objects.requireNonNull(name, "name must not be null");
		this.logo = Objects.requireNonNull(logo, "logo must not be null");
	}
	
	// ───── FACTORIES ───────────────────────────────────────────────────────
	/**
	 * Create a new Brand with random UUID.
	 * @param name
	 * @param logo
	 * @return
	 */
	static Brand create(final Name name, 
						final Logo logo) {
		
		return new Brand(UUID.randomUUID(),
						 name, logo);
	}
	/**
	 * Creates a Brand instance from persistence data
	 * BEWARE! --> For repository/infrastructure layer use only
	 * @param id
	 * @param name
	 * @param logo
	 * @return
	 */
	public static Brand reconstitute(final UUID id,
									 final Name name,
									 final Logo logo) {
		
		return new Brand(id,
						 name, logo);
	}

	// ───── GETTERS ────────────────────────────────────────────────────────
	UUID getId() {
		return id;
	}
	Name getName() {
		return name;
	}
	Logo getLogo() {
		return logo;
	}
	
	// ───── OVERRIDES ──────────────────────────────────────────────────────
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Brand)) return false;
        Brand that = (Brand) o;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    } 
    @Override
    public String toString() {
      return "Brand[id=%s, name=%s,  logo=%s]"
    		  .formatted(id, name, logo);
    }
	
}
