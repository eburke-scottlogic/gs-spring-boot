package com.example.springboot;

//HelloController integration tests

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
public class ControllerIT {

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
    public void allCreateOrdersShouldReturnLists() throws Exception {
        this.mockMvc.perform(post("/createOrder").content("{\n" +
                        "    \"account\": \"test\",\n" +
                        "    \"price\": 50,\n" +
                        "    \"quantity\": 10,\n" +
                        "    \"action\": \"buy\"\n" +
                        "}")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value("2"));
    }

}
