package com.naizfit.app.domain.testers;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

import com.naizfit.app.domain.shared.vo.CreationDate;
import com.naizfit.app.domain.testers.vo.Height;
import com.naizfit.app.domain.testers.vo.MeasureId;
import com.naizfit.app.domain.testers.vo.Weight;

// Recommendation: for a weight/height record that is only added and queried, 
//	I would model it as VO without Id. If the need to edit it arises,  
//	we migrate it to Entity with Id.
final class Measure {

	// ───── FIELDS ──────────────────────────────────────────────────────────
	private final MeasureId id;
	private final CreationDate creationDate;
    private final Height height;
    private final Weight weight;
    
    // ───── CONSTRUCTOR ─────────────────────────────────────────────────────
	private Measure(final MeasureId id,
					final CreationDate creationDate, 
				    final Height height, 
				    final Weight weight) {
        
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
	static Measure createNow(final Height height, 
			   				 final Weight weight) {
		
		return new Measure(MeasureId.newId(),
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
	public static Measure reconstitute(final MeasureId id,
			  						   final CreationDate creationDate,
			  						   final Height height, 
			  						   final Weight weight) {
		
		return new Measure(id,
						   creationDate,
						   height, weight);
	}

	// ───── GETTERS ────────────────────────────────────────────────────────
	MeasureId getId() {
		return id;
	}
	CreationDate getCreationDate() {
		return creationDate;
	}
	Height getHeight() {
		return height;
	}
	Weight getWeight() {
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
