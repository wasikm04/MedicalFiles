package pl.medical.service.files.services.patientcard;

import org.bson.types.ObjectId;
import pl.medical.service.files.models.PatientCard;
import pl.medical.service.files.models.User;

public interface PatientCardService {
    boolean updateCardInformation(PatientCard card);

    PatientCard getPatientCard(ObjectId id);

    ObjectId addPatientCard(PatientCard card);

    PatientCard getPatientCardByMail(String mail);

    void deleteCard(ObjectId cardId);
}
