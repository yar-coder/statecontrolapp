package com.electrolux.yarcoder.statecontrolapp.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;
import java.util.Date;

/**
 * Java Entity Bean object that represents a state message from an appliance
 *
 * @author Yaroslav Salamaha
 */

@Entity
@Table(name = "States")
//Lombok annotations below
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor

public class StateMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    Long id;

    //Date and time of message
    @Column (name = "datetime")
    final Date datetime = new Date();

    //Appliance state
    @Column (name = "state")
    String state;

    //Appliance Type
    @Enumerated(EnumType.STRING)
    @Column (name = "appliance_type")
    ApplianceType applianceType;

    //Appliance serial number
    @Column (name = "serial_number")
    String serialNumber;
}
