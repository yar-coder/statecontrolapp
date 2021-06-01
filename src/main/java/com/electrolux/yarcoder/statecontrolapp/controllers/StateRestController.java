package com.electrolux.yarcoder.statecontrolapp.controllers;

import com.electrolux.yarcoder.statecontrolapp.models.StateMessage;
import com.electrolux.yarcoder.statecontrolapp.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Controller that uses REST technology for external requests handling.
 *
 * @author Yaroslav Salamaha
 */

@RestController
@RequestMapping("/rest")
public class StateRestController {

    private StateService stateService;

    @Autowired
    public StateRestController(StateService stateService) {
        this.stateService = stateService;
    }

    //Method handles POST-requests
    @PostMapping(value = "")
    public ResponseEntity<?> create(@RequestBody StateMessage stateMessage) {
        if (stateService.create(stateMessage)) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
            return new ResponseEntity(HttpStatus.NOT_MODIFIED);
    }

    //Method handles GET-requests
    @GetMapping(value = "")
    public ResponseEntity<List<StateMessage>> getAll() {
        try {
            List<StateMessage> stateMessages = stateService.getAll();
            return new ResponseEntity(stateMessages, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    //Method handles GET-requests with id parameter
    @GetMapping(value = "/{id}")
    public ResponseEntity<StateMessage> getById(@PathVariable(name = "id") Long id) {
        try {
            StateMessage stateMessage = stateService.get(id);
            return new ResponseEntity(stateMessage, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
