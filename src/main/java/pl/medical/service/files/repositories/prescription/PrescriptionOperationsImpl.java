package pl.medical.service.files.repositories.prescription;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;
import pl.medical.service.files.models.PatientCard;
import pl.medical.service.files.models.Prescription;
import pl.medical.service.files.repositories.RepositoryUtils;
import pl.medical.service.files.repositories.patientcard.PatientCardRepository;

import java.util.List;

@Component
public class PrescriptionOperationsImpl implements PrescriptionOperations {

    private MongoOperations mongo;
    private RepositoryUtils repositoryUtils;
    private PatientCardRepository repository;

    public PrescriptionOperationsImpl(MongoOperations mongo,
                                      RepositoryUtils repositoryUtils,
                                      PatientCardRepository repository) {
        this.mongo = mongo;
        this.repositoryUtils = repositoryUtils;
        this.repository = repository;
    }

    @Override
    public List<Prescription> getPrescriptionsByUserId(ObjectId userId) {
        PatientCard card = repository.getBy_id(userId);
        return card.getPrescriptions();
    }

//    @Override
//    public Prescription findPrescriptionByUserMailAndId(String mail, ObjectId id) {
//        PatientCard card = repository.findBy_user_mail(mail);
//        if (card != null) {
//            List<Prescription> pres = card.getPrescriptions();
//            Optional<Prescription> test = pres.stream().filter(t -> t.get_id().equals(id)).findFirst();
//            return test.orElse(null);
//        } else {
//            return null;
//        }
//    }

    @Override
    public boolean savePrescriptionInPatientCard(String mail, Prescription prescription) {
        prescription.set_id(ObjectId.get());
        return repositoryUtils.saveObject("prescriptions", mail, prescription);
    }

    @Override
    public void deletePrescription(String mail, ObjectId id) {
        repositoryUtils.deleteObject("prescriptions", mail, id);
    }

    @Override
    public boolean updatePrescription(String mail, Prescription prescription) {
        return repositoryUtils.updateObject("prescriptions", prescription.get_id(), prescription);
    }
}
