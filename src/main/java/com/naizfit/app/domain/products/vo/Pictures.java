package com.naizfit.app.domain.products.vo;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

//NOTE: the order is important?
//		we can use a Set o LinkedHashSet.
// for this technical-test I use List and validate duplicates but there are another options.

public record Pictures(List<URL> urls) {
	
	// ───── CONSTRUCTOR ─────────────────────────────────────────────────────
    public Pictures {
        Objects.requireNonNull(urls, "Pictures must not be null");
        
        var copy = List.copyOf(urls); // immutable
 
        // validate duplicates
        if (new HashSet<>(copy).size() != copy.size()) {
            throw new IllegalArgumentException("Can not be duplicate picture urls");
        }

        // set immutable
        urls = copy;
    }
    
 // ───── BUSINESS METHODS ───────────────────────────────────────────────
    /**  
     * Retunr true if exists size
     */
    public boolean contains(final URL url) {
    	return urls.contains(Objects.requireNonNull(url));
    }

    /**
     * Add new Size and returns new Sizes obj.
     */
    public Pictures add(final URL url) {
    	Objects.requireNonNull(url);
        if (contains(url)) {
        	return this; // or throw exception ?? (according team specifications)
        }
        // return new obj because it is inmutable.
        var newList = new ArrayList<>(urls);
        newList.add(url);
        return new Pictures(newList);
    }

    /**
     * Remove size and return new Sizes obj
     */
    public Pictures remove(final URL url) {
        Objects.requireNonNull(url);
        if (!contains(url)) {
            return this; // or throw exception ?? (according team specifications)
        }
        var newList = urls.stream()
        				  .filter(s -> !s.equals(url))
                          .toList();
        return new Pictures(newList);
    }
    
    // NOTE: Can be necessary more behavior methods like: count, short, filter, etc...
    
    // ───── OVERRIDES ──────────────────────────────────────────────────────
    // use default Record toString()
}
