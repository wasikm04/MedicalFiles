package pl.medical.service.files.repositories.patientcard;

import org.bson.types.ObjectId;
import pl.medical.service.files.models.PatientCard;
import pl.medical.service.files.models.User;

public interface PatientCardOperations {

    boolean updateCardWithoutArrays(PatientCard card);
    ObjectId savePatientCard(PatientCard card);
    void createPatientCard(User user);
}
