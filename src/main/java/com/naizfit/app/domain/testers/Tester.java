package com.naizfit.app.domain.testers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.naizfit.app.domain.shared.BusinessException;
import com.naizfit.app.domain.shared.vo.Email;
import com.naizfit.app.domain.shared.vo.Name;
import com.naizfit.app.domain.shared.vo.Sex;
import com.naizfit.app.domain.testers.vo.Birthdate;
import com.naizfit.app.domain.testers.vo.Password;
import com.naizfit.app.domain.testers.vo.TesterId;
import com.naizfit.app.domain.testers.vo.TestsDone;

public class Tester {

	// ───── FIELDS ──────────────────────────────────────────────────────────
	private final TesterId id;
	private final Name name;
	private final Email email;
	private final Password password;
	private final Birthdate birthdate;
	private final Sex sex;
	private final TestsDone testsDone;
	private final List<Measure> measures;

	// ───── CONSTRUCTOR ─────────────────────────────────────────────────────
	private Tester(final TesterId id, 
				   final Name name, 
				   final Email email, 
				   final Password password, 
				   final Birthdate birthdate, 
				   final Sex sex,
				   final TestsDone testsDone, 
				   final List<Measure> measures) {
		
		this.id = Objects.requireNonNull(id, "ID is required");
		this.name = Objects.requireNonNull(name, "Name is required");
		this.email = Objects.requireNonNull(email, "Email is required");
		this.password = Objects.requireNonNull(password, "Password is required");
		this.birthdate = Objects.requireNonNull(birthdate, "Birth date is required");
		this.sex = Objects.requireNonNull(sex, "Sex is required");
		this.testsDone = Objects.requireNonNull(testsDone, "Tests done is required");
		this.measures = new ArrayList<>(
								Objects.requireNonNull(measures, "Measures is required"));
	}

	// ───── FACTORIES ───────────────────────────────────────────────────────
	/**
	 * Create a new Tester with random UUID.
	 * @param name
	 * @param email
	 * @param password
	 * @param birthdate
	 * @param sex
	 * @return
	 */
	public static Tester create(final Name name, 
								final Email email, 
								final Password password, 
								final Birthdate birthdate,
								final Sex sex) {
		
		return new Tester(TesterId.newId(), 
						  name, email, password, birthdate, sex, 
						  TestsDone.zero(),
						  Collections.emptyList());
	}
	/**
	 * Hydration from persistence
	 * @param id
	 * @param name
	 * @param email
	 * @param password
	 * @param birthdate
	 * @param sex
	 * @param testsDone
	 * @param measures
	 * @return
	 */
	public static Tester reconstitute(final TesterId id,
									  final Name name,
									  final Email email,
									  final Password password,
									  final Birthdate birthdate,
									  final Sex sex,
									  final TestsDone testsDone,
									  final List<Measure> measures) {
		
		return new Tester(id, 
						  name, email, password, birthdate, sex, 
						  testsDone, 
						  measures);
	}
	
	// ───── BUSINESS METHODS ───────────────────────────────────────────────
	public void addMeasure(final Measure measure) {
		Objects.requireNonNull(measure, "Measure is required");
		if (measures.stream().anyMatch(x -> x.getId().equals(measure.getId()))) {
			throw new BusinessException("Measure already added");
		}
		measures.add(measure);
	}

	// ───── GETTERS ────────────────────────────────────────────────────────
	public TesterId getId() {
		return id;
	}
	public Name getName() {
		return name;
	}
	public Email getEmail() {
		return email;
	}
	public Password getPassword() {
		return password;
	}
	public Birthdate getBirthdate() {
		return birthdate;
	}
	public Sex getSex() {
		return sex;
	}
	public TestsDone getTestsDone() {
		return testsDone;
	}
	public List<Measure> getMeasures() {
		return Collections.unmodifiableList(measures);
	}

	// ───── OVERRIDES ──────────────────────────────────────────────────────
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tester)) return false;
        Tester that = (Tester) o;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    @Override
    public String toString() {
        return "Tester[id=%s, name=%s, email=%s, birthdate=%s, sex=%s, testsDone=%s, measures=%d]".formatted(
            id, name, email, birthdate, sex, testsDone, measures
        );
    }

}
