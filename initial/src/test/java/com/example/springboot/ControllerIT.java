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

    @Autowired
    private MockMvc mockMvc;

    private String obtainAccessToken(Login login) throws Exception {
        MvcResult result=this.mockMvc
                .perform(post("/user").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                        "    \"username\": \"user1\",\n" +
                        "    \"password\": \"password1\"\n" +
                        "}"))
                .andExpect(status().isOk())
                .andReturn();

        String actualJson=result.getResponse().getContentAsString();
        return actualJson;
    }

    @Test
    public void allBuysShouldReturnBuyList() throws Exception {
        Login login = new Login("user1", "password1");
        String accessToken = obtainAccessToken(login);
        this.mockMvc.perform(get("/allBuys")
                .header("Authorization",accessToken))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value("0"));
    }


    @Test
    public void allSellsShouldReturnSellList() throws Exception {
        Login login = new Login("user1", "password1");
        String accessToken = obtainAccessToken(login);
        this.mockMvc.perform(get("/allSells")
                .header("Authorization",accessToken))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value("0"));
    }

    @Test
    public void createOrdersShouldReturnListsBuy() throws Exception {
        Login login = new Login("user1", "password1");
        String accessToken = obtainAccessToken(login);
        this.mockMvc.perform(post("/createOrder")
                        .header("Authorization",accessToken)
                        .content("{\n" +
                        "    \"price\": 50,\n" +
                        "    \"quantity\": 10,\n" +
                        "    \"action\": \"buy\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("[0].length()").value("1"));
    }

    @Test
    public void createOrdersShouldReturnListsSell() throws Exception {
        Login login = new Login("user1", "password1");
        String accessToken = obtainAccessToken(login);
        this.mockMvc.perform(post("/createOrder")
                        .header("Authorization",accessToken)
                        .content("{\n" +
                        "    \"price\": 50,\n" +
                        "    \"quantity\": 10,\n" +
                        "    \"action\": \"sell\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("[1].length()").value("1"));
    }


    @Test
    public void validatePrice() throws Exception {
        Login login = new Login("user1", "password1");
        String accessToken = obtainAccessToken(login);
        this.mockMvc.perform(post("/createOrder")
                .header("Authorization",accessToken)
                .content("{\n" +
                "    \"price\": 0,\n" +
                "    \"quantity\": 10,\n" +
                "    \"action\": \"sell\"\n" +
                "}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void validateQuantity() throws Exception {
        Login login = new Login("user1", "password1");
        String accessToken = obtainAccessToken(login);
        this.mockMvc.perform(post("/createOrder")
                .header("Authorization",accessToken)
                .content("{\n" +
                "    \"price\": 50,\n" +
                "    \"quantity\": 0,\n" +
                "    \"action\": \"sell\"\n" +
                "}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void validateAction() throws Exception {
        Login login = new Login("user1", "password1");
        String accessToken = obtainAccessToken(login);
        this.mockMvc.perform(post("/createOrder")
                .header("Authorization",accessToken)
                .content("{\n" +
                "    \"price\": 50,\n" +
                "    \"quantity\": 10,\n" +
                "    \"action\": \"\"\n" +
                "}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    @Test
    public void allTradesShouldReturnTransHist() throws Exception {
        Login login = new Login("user1", "password1");
        String accessToken = obtainAccessToken(login);
        this.mockMvc.perform(get("/allTrades")
                        .header("Authorization",accessToken))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value("0"));
    }

    @Test
    public void aggBuysShouldReturnAggBuy() throws Exception {
        Login login = new Login("user1", "password1");
        String accessToken = obtainAccessToken(login);
        this.mockMvc.perform(get("/aggBuys")
                        .header("Authorization",accessToken))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value("0"));
    }

    @Test
    public void aggSellsShouldReturnAggSell() throws Exception {
        Login login = new Login("user1", "password1");
        String accessToken = obtainAccessToken(login);
        this.mockMvc.perform(get("/aggSells")
                        .header("Authorization",accessToken))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value("0"));
    }

    @Test
    public void privTradesShouldReturnPrivTrades() throws Exception {
        Login login = new Login("user1", "password1");
        String accessToken = obtainAccessToken(login);
        this.mockMvc.perform(get("/privTrades")
                        .header("Authorization",accessToken))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value("0"));
    }

    @Test
    public void privBuyShouldReturnPrivBuy() throws Exception {
        Login login = new Login("user1", "password1");
        String accessToken = obtainAccessToken(login);
        this.mockMvc.perform(get("/privBuy")
                        .header("Authorization",accessToken))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value("0"));
    }

    @Test
    public void privSellShouldReturnPrivSell() throws Exception {
        Login login = new Login("user1", "password1");
        String accessToken = obtainAccessToken(login);
        this.mockMvc.perform(get("/privSell")
                        .header("Authorization",accessToken))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value("0"));
    }

    @Test
    public void allOrdersShouldReturnAllOrders() throws Exception {
        Login login = new Login("user1", "password1");
        String accessToken = obtainAccessToken(login);
        this.mockMvc.perform(get("/allOrders")
                        .header("Authorization",accessToken))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value("0"));
    }

    @Test
    public void allAccountsShouldReturnAllAccounts() throws Exception {
        Login login = new Login("user1", "password1");
        String accessToken = obtainAccessToken(login);
        this.mockMvc.perform(get("/allAccounts")
                        .header("Authorization",accessToken))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value("5"));
    }

    @Test
    public void database() throws Exception {
        Login login = new Login("user1", "password1");
        String accessToken = obtainAccessToken(login);
        this.mockMvc.perform(post("/database")
                .content("{\n" +
                        "    \"username\": \"user6\",\n" +
                        "    \"password\": \"password6\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        this.mockMvc.perform(get("/allAccounts")
                        .header("Authorization",accessToken))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("length()").value("6"));
    }

    @Test
    public void user() throws Exception {
        this.mockMvc.perform(post("/database")
                .content("{\n" +
                        "    \"username\": \"user1\",\n" +
                        "    \"password\": \"password1\"\n" +
                        "}").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }


}
