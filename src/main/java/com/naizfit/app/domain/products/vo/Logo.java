package com.naizfit.app.domain.products.vo;

import java.net.URL;
import java.util.Objects;

public record Logo(URL url) {
	
    public Logo {
        Objects.requireNonNull(url, "Logo URL must not be null");
    }
	
	@Override
	public String toString() {
		return url.toString();
	}
}