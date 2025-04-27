package com.naizfit.app.domain.testers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.naizfit.app.domain.shared.vo.EmailType;
import com.naizfit.app.domain.shared.vo.NameType;
import com.naizfit.app.domain.testers.vo.BirthdateType;
import com.naizfit.app.domain.testers.vo.PasswordType;
import com.naizfit.app.domain.testers.vo.SexType;
import com.naizfit.app.domain.testers.vo.TestsDoneType;

public class Tester {

	// ───── FIELDS ──────────────────────────────────────────────────────────
	private final UUID id;
	private final NameType name;
	private final EmailType email;
	private final PasswordType password;
	private final BirthdateType birthdate;
	private final SexType sex;
	private final TestsDoneType testsDone;
	private final List<Measure> measures;

	// ───── CONSTRUCTOR ─────────────────────────────────────────────────────
	private Tester(final UUID id, 
				   final NameType name, 
				   final EmailType email, 
				   final PasswordType password, 
				   final BirthdateType birthdate, 
				   final SexType sex,
				   final TestsDoneType testsDone, 
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
	public static Tester create(final NameType name, 
								final EmailType email, 
								final PasswordType password, 
								final BirthdateType birthdate,
								final SexType sex) {
		
		return new Tester(UUID.randomUUID(), 
						  name, email, password, birthdate, sex, 
						  TestsDoneType.zero(),
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
	public static Tester reconstitute(final UUID id,
									  final NameType name,
									  final EmailType email,
									  final PasswordType password,
									  final BirthdateType birthdate,
									  final SexType sex,
									  final TestsDoneType testsDone,
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
	public UUID getId() {
		return id;
	}
	public NameType getName() {
		return name;
	}
	public EmailType getEmail() {
		return email;
	}
	public PasswordType getPassword() {
		return password;
	}
	public BirthdateType getBirthdate() {
		return birthdate;
	}
	public SexType getSex() {
		return sex;
	}
	public TestsDoneType getTestsDone() {
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
        return "Tester[id=%s, name=%s, email=%s, birthdate=%s, sex=%s, testsDone=%s, measuresCount=%d]".formatted(
            id,
            name,
            email,
            birthdate,
            sex,
            testsDone,
            measures.size()
        );
    }

}
