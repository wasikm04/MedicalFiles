package pl.medical.service.files.controllers;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.medical.service.files.api.TreatmentDto;
import pl.medical.service.files.api.mappers.TreatmentMapper;
import pl.medical.service.files.services.treatment.TreatmentService;

import javax.validation.Valid;

@Api(value = "Historia choroby", description = "Operacje dodawania i aktualizowania karty przebiegu choroby pacjenta")
@CrossOrigin(value = "*", maxAge = 3600, allowCredentials = "true")
@RestController
public class TreatmentsController {

    private TreatmentService treatmentService;
    private TreatmentMapper treatmentMapper;


    public TreatmentsController(TreatmentService treatmentService,
                                TreatmentMapper treatmentMapper) {
        this.treatmentService = treatmentService;
        this.treatmentMapper = treatmentMapper;
    }

    @PostMapping(value = "/treatment", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> addTreatment(@Valid @RequestBody TreatmentDto dto, Authentication authentication) {
            boolean created = treatmentService.addTreatmentToPatientCard(dto.getUserMail(), treatmentMapper.mapToTreatment(dto));
            if (created) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.badRequest().body("Niewłaściwe dane");
    }

    @PutMapping(value = "/treatment", produces = "application/json")
    public @ResponseBody
    ResponseEntity<?> updateTreatment(@Valid @RequestBody TreatmentDto dto, Authentication authentication) {
            boolean updated = treatmentService.updateTreatmentToPatientCard(dto.getUserMail(), treatmentMapper.mapToTreatment(dto));
            if (updated) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.badRequest().body("Niewłaściwe dane");
    }
}
