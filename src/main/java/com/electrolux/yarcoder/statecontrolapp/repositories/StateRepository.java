package com.electrolux.yarcoder.statecontrolapp.repositories;

import com.electrolux.yarcoder.statecontrolapp.models.StateMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface used for typical database operations with {@link StateMessage} entity.
 *
 * @author Yaroslav Salamaha
 */

@Repository
public interface StateRepository extends JpaRepository<StateMessage, Long> {
}
