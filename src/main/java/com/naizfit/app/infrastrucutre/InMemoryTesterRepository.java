package com.naizfit.app.infrastrucutre;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.google.common.base.Optional;
import com.naizfit.app.domain.testers.Tester;
import com.naizfit.app.domain.testers.TesterRepository;
import com.naizfit.app.domain.testers.vo.TesterId;

public class InMemoryTesterRepository implements TesterRepository {
	
	// BEWARE! concurrency danger -> use ConcurrentHashMap
	private final Map<UUID, Tester> store = new ConcurrentHashMap<>();

	@Override
	public void save(Tester tester) {
		store.put(tester.getId().value(), tester);
		
	}

	@Override
	public Optional<Tester> findById(TesterId id) {
		return Optional.fromNullable(store.get(id.value()));
	}

	@Override
	public List<Tester> findAll() {
		return new ArrayList<>(store.values());
	}

	@Override
	public void delete(TesterId id) {
		store.remove(id.value());
	}

}
