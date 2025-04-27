package com.naizfit.app.domain.products.vo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

//NOTE: the order is important?
//		we can use a Set o LinkedHashSet.
// for this technical-test I use List and validate duplicates but there are another options.

public record Sizes(List<Size> values) {

	// ───── CONSTRUCTOR ─────────────────────────────────────────────────────
    public Sizes {
        Objects.requireNonNull(values, "Sizes no puede ser null");
        
        var copy = List.copyOf(values); // immutable

        // validate duplicates
        if (new HashSet<>(copy).size() != copy.size()) {
            throw new IllegalArgumentException("Can not be duplicate sizes");
        }

        // set immutable
        values = copy;
    }
    
    // ───── BUSINESS METHODS ───────────────────────────────────────────────
    /**  
     * Retunr true if exists size
     */
    public boolean contains(final Size size) {
    	return values.contains(Objects.requireNonNull(size));
    }

    /**
     * Add new Size and returns new Sizes obj.
     */
    public Sizes add(final Size size) {
    	Objects.requireNonNull(size);
        if (contains(size)) {
        	return this; // or throw exception ?? (according team specifications)
        }
        // return new obj because it is inmutable.
        var newList = new ArrayList<>(values);
        newList.add(size);
        return new Sizes(newList);
    }

    /**
     * Remove size and return new Sizes obj
     */
    public Sizes remove(final Size size) {
        Objects.requireNonNull(size);
        if (!contains(size)) {
            return this; // or throw exception ?? (according team specifications)
        }
        var newList = values.stream()
        					.filter(s -> !s.equals(size))
                            .toList();
        return new Sizes(newList);
    }
    
    // NOTE: Can be necessary more behavior methods like: count, short, filter, etc...
    
    // ───── OVERRIDES ──────────────────────────────────────────────────────
    // use default Record toString()

}
