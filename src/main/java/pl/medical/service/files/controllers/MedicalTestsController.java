package pl.medical.service.files.controllers;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.medical.service.files.api.MedicalTestDto;
import pl.medical.service.files.api.mappers.MedicalTestMapper;
import pl.medical.service.files.services.medicaltest.MedicalTestService;

import javax.validation.Valid;

@Api(value = "Badania lekarskie", description = "Operacje dodawania i aktualizowania badań pacjenta")
@CrossOrigin(value = "*", maxAge = 3600, allowCredentials = "true")
@RestController
public class MedicalTestsController {

    private MedicalTestService medicalTestService;
    private MedicalTestMapper medicalTestMapper;

    public MedicalTestsController(MedicalTestService medicalTestService,
                                  MedicalTestMapper medicalTestMapper) {
        this.medicalTestService = medicalTestService;
        this.medicalTestMapper = medicalTestMapper;
    }

    @PostMapping(value = "/medical-test", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> addMedicalTest(@Valid @RequestBody MedicalTestDto dto, Authentication authentication) {
        if (authentication.getPrincipal().toString().equals(dto.getAuthorMail())) {
            String createdId = medicalTestService.addMedicalTestToPatientCard(dto.getUserMail(), medicalTestMapper.mapToMedicalTest(dto));
            if (createdId != null) {
                return ResponseEntity.ok(createdId);
            }
            return ResponseEntity.badRequest().body("Niewłaściwe dane");
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Nie jesteś uprawniony do zmiany danych innych użytkowników");
    }

    @PutMapping(value = "/medical-test", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> updateMedicalTest(@Valid @RequestBody MedicalTestDto dto, Authentication authentication) {
        if (authentication.getPrincipal().toString().equals(dto.getAuthorMail())) {
            boolean updated = medicalTestService.updateMedicalTestToPatientCard(dto.getUserMail(), medicalTestMapper.mapToMedicalTest(dto));
            if (updated) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.badRequest().body("Niewłaściwe dane");
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Nie jesteś uprawniony do zmiany danych innych użytkowników");
    }

}
