package com.naizfit.app.domain.testers;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import com.naizfit.app.domain.shared.vo.CreationDate;
import com.naizfit.app.domain.testers.vo.HeightType;
import com.naizfit.app.domain.testers.vo.WeightType;

// Recommendation: for a weight/height record that is only added and queried, 
//	I would model it as VO without Id. If the need to edit it arises,  
//	we migrate it to Entity with Id.
final class Measure {

	// ───── FIELDS ──────────────────────────────────────────────────────────
	private final UUID id;
	private final CreationDate creationDate;
    private final HeightType height;
    private final WeightType weight;
    
    // ───── CONSTRUCTOR ─────────────────────────────────────────────────────
	private Measure(final UUID id,
					final CreationDate creationDate, 
				    final HeightType height, 
				    final WeightType weight) {
        
        this.id = Objects.requireNonNull(id, "Id is required");
		this.creationDate = Objects.requireNonNull(creationDate, "Creation date is required");
		this.height = Objects.requireNonNull(height, "Height is required");
		this.weight = Objects.requireNonNull(weight, "Weight is required");
	}
	
	// ───── FACTORIES ───────────────────────────────────────────────────────
	/**
	 * Create a new measurement with today's date and random UUID.
	 * @param height
	 * @param weight
	 * @return
	 */
	static Measure createNow(final HeightType height, 
			   				 		final WeightType weight) {
		
		return new Measure(UUID.randomUUID(),
						   new CreationDate(LocalDate.now()),
						   height, weight);
	}
	/**
	 * Creates a Measure instance from persistence data
	 * BEWARE! --> For repository/infrastructure layer use only
	 * @param id
	 * @param creationDate
	 * @param height
	 * @param weight
	 * @return
	 */
	public static Measure reconstitute(final UUID id,
			  						   final CreationDate creationDate,
			  						   final HeightType height, 
			  						   final WeightType weight) {
		
		return new Measure(id,
						   creationDate,
						   height, weight);
	}

	// ───── GETTERS ────────────────────────────────────────────────────────
	UUID getId() {
		return id;
	}
	CreationDate getCreationDate() {
		return creationDate;
	}
	HeightType getHeight() {
		return height;
	}
	WeightType getWeight() {
		return weight;
	}
	
	// ───── OVERRIDES ──────────────────────────────────────────────────────
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Measure)) return false;
        Measure that = (Measure) o;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    } 
    @Override
    public String toString() {
      return "Measure[id=%s, creationDate=%s,  weight=%s kg, height=%s cm]"
    		  .formatted(id, creationDate, weight, height);
    }
	
}
