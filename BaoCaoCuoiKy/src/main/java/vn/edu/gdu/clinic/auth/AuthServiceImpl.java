package vn.edu.gdu.clinic.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.gdu.clinic.exception.ConflictException;
import vn.edu.gdu.clinic.exception.ResourceNotFoundException;
import vn.edu.gdu.clinic.patient.Patient;
import vn.edu.gdu.clinic.patient.PatientRepository;
import vn.edu.gdu.clinic.user.*;

import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private static final String PATIENT_ROLE_CODE = "PATIENT";

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public RegisterPatientResponse registerPatient(
            RegisterPatientRequest request
    ) {
        String normalizedEmail = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        String normalizedPhone = request.phone().trim();

        if (userRepository.existsByEmailIgnoreCase(normalizedEmail)) {
            throw new ConflictException("Email đã được sử dụng");
        }

        if (userRepository.existsByPhone(normalizedPhone)) {
            throw new ConflictException("Số điện thoại đã được sử dụng");
        }

        String insuranceNumber = normalizeOptional(
                request.healthInsuranceNumber()
        );

        if (insuranceNumber != null
                && patientRepository.existsByHealthInsuranceNumber(
                insuranceNumber
        )) {
            throw new ConflictException(
                    "Số bảo hiểm y tế đã được sử dụng"
            );
        }

        Role patientRole = roleRepository.findByCode(PATIENT_ROLE_CODE)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy vai trò PATIENT"
                ));

        User user = User.builder()
                .role(patientRole)
                .fullName(request.fullName().trim())
                .email(normalizedEmail)
                .phone(normalizedPhone)
                .passwordHash(passwordEncoder.encode(request.password()))
                .gender(request.gender())
                .dateOfBirth(request.dateOfBirth())
                .address(normalizeOptional(request.address()))
                .status(UserStatus.ACTIVE)
                .build();

        User savedUser = userRepository.save(user);

        Patient patient = Patient.builder()
                .user(savedUser)
                .patientCode(generatePatientCode())
                .healthInsuranceNumber(insuranceNumber)
                .bloodType(normalizeOptional(request.bloodType()))
                .emergencyContactName(
                        normalizeOptional(request.emergencyContactName())
                )
                .emergencyContactPhone(
                        normalizeOptional(request.emergencyContactPhone())
                )
                .medicalHistory(
                        normalizeOptional(request.medicalHistory())
                )
                .build();

        Patient savedPatient = patientRepository.save(patient);

        return new RegisterPatientResponse(
                savedUser.getId(),
                savedPatient.getId(),
                savedPatient.getPatientCode(),
                savedUser.getFullName(),
                savedUser.getEmail(),
                savedUser.getPhone(),
                savedUser.getGender(),
                savedUser.getDateOfBirth(),
                savedUser.getStatus(),
                savedUser.getCreatedAt()
        );
    }

    private String generatePatientCode() {
        String patientCode;

        do {
            patientCode = "BN-"
                    + UUID.randomUUID()
                    .toString()
                    .substring(0, 8)
                    .toUpperCase(Locale.ROOT);
        } while (patientRepository.existsByPatientCode(patientCode));

        return patientCode;
    }

    private String normalizeOptional(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }
}