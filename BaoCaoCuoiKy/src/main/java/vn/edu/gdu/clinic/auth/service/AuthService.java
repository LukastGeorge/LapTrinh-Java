package vn.edu.gdu.clinic.auth.service;

import vn.edu.gdu.clinic.auth.dto.request.RegisterPatientRequest;
import vn.edu.gdu.clinic.auth.dto.response.RegisterPatientResponse;

public interface AuthService {

    RegisterPatientResponse registerPatient(RegisterPatientRequest request);
}
