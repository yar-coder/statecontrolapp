package com.electrolux.yarcoder.statecontrolapp.services;

import com.electrolux.yarcoder.statecontrolapp.models.StateMessage;
import java.util.List;

/**
 * Service interface for class {@link StateMessage}.
 *
 * @author Yaroslav Salamaha
 */

public interface StateService {

    void create(StateMessage stateMessage);

    StateMessage get(Long id);

    List<StateMessage> getAll();

}
