package com.electrolux.yarcoder.statecontrolapp.controllers;

import com.electrolux.yarcoder.statecontrolapp.models.StateMessage;
import com.electrolux.yarcoder.statecontrolapp.services.StateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;
import java.util.NoSuchElementException;

import static com.electrolux.yarcoder.statecontrolapp.models.ApplianceType.DISHWASHER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class with unit tests of {@link StateRestController} class.
 *
 * @author Yaroslav Salamaha
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(StateRestController.class)
public class StateRestControllerTest {

    // prepare data
    static final StateMessage stateMessage = new StateMessage("rinse start", DISHWASHER, "AJ123456");
    static final StateMessage stateMessage2 = new StateMessage("rinse end", DISHWASHER, "TJ123555");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StateService stateService;

    private MvcResult postRequest(StateMessage stateMessage) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/rest")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(stateMessage))).andReturn();
    }

    @Test
    void testCreate() throws Exception {

        // describe mock's behaviour
        when(stateService.create(stateMessage)).thenReturn(true);
        when(stateService.create(stateMessage2)).thenReturn(false);

        // execute 1 - expecting successful result
        MvcResult result1 = postRequest(stateMessage);
        // execute 2 - expecting failure result
        MvcResult result2 = postRequest(stateMessage2);

        // verify HTTP CREATED response status
        int status1 = result1.getResponse().getStatus();
        assertEquals(HttpStatus.CREATED.value(), status1, "Incorrect Response Status");

        // verify HTTP NOT_MODIFIED response status
        int status2 = result2.getResponse().getStatus();
        assertEquals(HttpStatus.NOT_MODIFIED.value(), status2, "Incorrect Response Status");

        // verify that service method was called at least once
        verify(stateService, atLeastOnce()).create(any(StateMessage.class));

    }

    @Test
    void testGetAll() throws Exception {
        // Prepare data
        List<StateMessage> listFromDb = List.of(stateMessage, stateMessage2);

        // describe mock's behaviour
        when(stateService.getAll()).thenReturn(listFromDb);

        // Sending test GET request to our REST app
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/rest")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Parsing of JSON to a list of State Messages
        StateMessage[] arrayFromJson = new ObjectMapper()
                .readValue(result.getResponse().getContentAsString(),StateMessage[].class);
        List<StateMessage> listFromJson = List.of(arrayFromJson[0],arrayFromJson[1]);

        // verify that lists of state masseges from db and and from json response are equal
        assertEquals(listFromJson,listFromDb);

        // verify HTTP OK response status
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status, "Incorrect Response Status");

        // Verify the response body is not null
        assertNotNull(listFromJson);
    }

    // GetAll() test with empty answer from DB
    @Test
    void testGetAllEmpty() throws Exception {
        // describe mock's behaviour
        when(stateService.getAll()).thenThrow(new NoSuchElementException());
        // execute
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/rest")).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.NOT_FOUND.value(), status, "Incorrect Response Status");

    }

    @Test
    void testGetById() throws Exception {
        // describe mock's behaviour
        when(stateService.get(1L)).thenReturn(stateMessage);

        // execute. Sending test GET request to our REST app
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/rest/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        // Parsing of JSON to a State Message
        StateMessage messageFromJson = new ObjectMapper()
                .readValue(result.getResponse().getContentAsString(),StateMessage.class);

        // verify that state masseges from db and from json response are equal
        assertEquals(messageFromJson,stateMessage);

        // verify HTTP OK response status
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status, "Incorrect Response Status");

        // Verify the response body is not null
        assertNotNull(messageFromJson);
    }

    // GetById() test with empty answer from DB
    @Test
    void testGetByIdEmpty() throws Exception {
        // describe mock's behaviour
        when(stateService.get(anyLong())).thenThrow(new NoSuchElementException());
        // execute
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/rest/3")).andReturn();
        int status = result.getResponse().getStatus();
        assertEquals(HttpStatus.NOT_FOUND.value(), status, "Incorrect Response Status");

    }
}