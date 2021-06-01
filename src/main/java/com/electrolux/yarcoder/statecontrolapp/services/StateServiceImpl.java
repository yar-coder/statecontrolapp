package com.electrolux.yarcoder.statecontrolapp.services;

import com.electrolux.yarcoder.statecontrolapp.models.StateMessage;
import com.electrolux.yarcoder.statecontrolapp.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Service class that realizes {@link StateService} interface with business logic for {@link StateMessage} entity.
 * Wraps the {@link StateRepository} interface.
 * @author Yaroslav Salamaha
 */

@Service
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;

    @Autowired
    public StateServiceImpl (StateRepository sR) {
        this.stateRepository = sR;
    }

    //Method creates a new record in DB
    @Override
    public boolean create(StateMessage stateMessage) {
        StateMessage savedStateMessage = stateRepository.save(stateMessage);
        stateMessage.setId(savedStateMessage.getId());
        if (savedStateMessage != null & stateMessage.equals(savedStateMessage)) {
            return true;
        }
        return false;
    }

    //Method gets all records from DB
    @Override
    public List<StateMessage> getAll() throws NoSuchElementException {
       return stateRepository.findAll();
    }

    //Method gets one record from DB according to id of state message
    @Override
    public StateMessage get(Long id) throws NoSuchElementException {
        return stateRepository.findById(id).get();
    }
}
