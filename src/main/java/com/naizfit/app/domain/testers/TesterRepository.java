package com.naizfit.app.domain.testers;

import java.util.List;

import com.google.common.base.Optional;
import com.naizfit.app.domain.testers.vo.TesterId;

public interface TesterRepository {

	void save(Tester tester);

	Optional<Tester> findById(TesterId id);

	List<Tester> findAll();

	void delete(TesterId id);
}
