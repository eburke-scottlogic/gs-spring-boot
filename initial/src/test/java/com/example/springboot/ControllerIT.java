package com.example.springboot;

//HelloController integration tests

import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class ControllerIT {

    //find a way to reset before each test
    //all tests pass when run individually, just not all at once

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void allBuysShouldReturnBuyList() throws Exception {
        this.mockMvc.perform(get("/allBuys")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value("0"));
    }

    @Test
    public void allSellsShouldReturnSellList() throws Exception {
        this.mockMvc.perform(get("/allSells")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value("0"));
    }

    @Test
    public void createOrdersShouldReturnListsBuy() throws Exception {
        this.mockMvc.perform(post("/createOrder").content("{\n" +
                        "    \"account\": \"test\",\n" +
                        "    \"price\": 50,\n" +
                        "    \"quantity\": 10,\n" +
                        "    \"action\": \"buy\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("[0].length()").value("1"));
    }

    @Test
    public void createOrdersShouldReturnListsSell() throws Exception {
        this.mockMvc.perform(post("/createOrder").content("{\n" +
                        "    \"account\": \"test\",\n" +
                        "    \"price\": 50,\n" +
                        "    \"quantity\": 10,\n" +
                        "    \"action\": \"sell\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("[1].length()").value("1"));
    }

    @Test
    public void validateAccount() throws Exception {
        this.mockMvc.perform(post("/createOrder").content("{\n" +
                        "    \"account\": \"\",\n" +
                        "    \"price\": 50,\n" +
                        "    \"quantity\": 10,\n" +
                        "    \"action\": \"sell\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void validatePrice() throws Exception {
        this.mockMvc.perform(post("/createOrder").content("{\n" +
                "    \"account\": \"test\",\n" +
                "    \"price\": 0,\n" +
                "    \"quantity\": 10,\n" +
                "    \"action\": \"sell\"\n" +
                "}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void validateQuantity() throws Exception {
        this.mockMvc.perform(post("/createOrder").content("{\n" +
                "    \"account\": \"test\",\n" +
                "    \"price\": 50,\n" +
                "    \"quantity\": 0,\n" +
                "    \"action\": \"sell\"\n" +
                "}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void validateAction() throws Exception {
        this.mockMvc.perform(post("/createOrder").content("{\n" +
                "    \"account\": \"test\",\n" +
                "    \"price\": 50,\n" +
                "    \"quantity\": 10,\n" +
                "    \"action\": \"\"\n" +
                "}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    //test login api

    //test database api


}
