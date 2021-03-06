package pl.medical.service.files.repositories.patientcard;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import pl.medical.service.files.models.Exceptions.ResourceNotFoundException;
import pl.medical.service.files.models.PatientCard;
import pl.medical.service.files.models.User;

import java.util.ArrayList;
import java.util.Optional;


@Component
public class PatientCardOperationsImpl implements PatientCardOperations {

    private MongoOperations mongo;

    public PatientCardOperationsImpl(MongoOperations mongo) {
        this.mongo = mongo;
    }

    @Override
    public boolean updateCardWithoutArrays(PatientCard updatedCard) {
        Query query = new Query();
        PatientCard card = mongo.findOne(query.addCriteria(Criteria.where("_id").is(updatedCard.get_id())), PatientCard.class);
        if (card != null) {
            updatedCard.setMedicalTests(Optional.ofNullable(card.getMedicalTests()).orElseGet(ArrayList::new));
            updatedCard.setPrescriptions(Optional.ofNullable(card.getPrescriptions()).orElseGet(ArrayList::new));
            updatedCard.setReferrals(Optional.ofNullable(card.getReferrals()).orElseGet(ArrayList::new));
            updatedCard.setTreatments(Optional.ofNullable(card.getTreatments()).orElseGet(ArrayList::new));
            return mongo.save(updatedCard) != null;//mapowanie
        } else {
            throw new ResourceNotFoundException("Brak karty pacjenta o podanym id " + updatedCard.getUserId());
        }
    }

    @Override
    public ObjectId savePatientCard(PatientCard card) {
        return mongo.save(card).getUserId();
    }

    @Override
    public void createPatientCard(User user) {
        mongo.save(PatientCard
                .builder()
                .userMail(user.getEmail())
                .userId(user.get_id())
                .build()
        );
    }
}

