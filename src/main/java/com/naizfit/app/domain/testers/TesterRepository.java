package com.naizfit.app.domain.testers;

import java.util.List;
import java.util.Optional;

import com.naizfit.app.domain.testers.vo.TesterId;

public interface TesterRepository {

	void save(Tester tester);

	Optional<Tester> findById(TesterId id);

	List<Tester> findAll();

	void delete(TesterId id);
}
