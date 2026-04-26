package com.testtask.Wallets.controller;

import com.testtask.Wallets.service.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WalletController.class)
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WalletService service;

    @Test
    void performOperationPositiveTest() throws Exception {
        mockMvc.perform(
                post("/api/v1/wallet")
                        .content("{\"walletId\":\"bc16eaa8-0057-4836-85bb-3076ffb6369b\"," +
                                "\"operationType\":\"DEPOSIT\"," +
                                "\"amount\":1000}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void performOperationInvalidIdTest() throws Exception {
        mockMvc.perform(
                post("/api/v1/wallet")
                        .content("{\"walletId\":\"123\"," +
                                "\"operationType\":\"DEPOSIT\"," +
                                "\"amount\":1000}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void performOperationInvalidOperationTypeTest() throws Exception {
        mockMvc.perform(
                post("/api/v1/wallet")
                        .content("{\"walletId\":\"bc16eaa8-0057-4836-85bb-3076ffb6369b\"," +
                                "\"operationType\":\"UNKNOWN\"," +
                                "\"amount\":1000}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void performOperationInvalidAmountTest() throws Exception {
        mockMvc.perform(
                post("/api/v1/wallet")
                        .content("{\"walletId\":\"bc16eaa8-0057-4836-85bb-3076ffb6369b\"," +
                                "\"operationType\":\"DEPOSIT\"," +
                                "\"amount\":0}")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void getWalletBalanceTest() throws Exception {
        mockMvc.perform(
                get("/api/v1/wallets/{id}", "016a72ca-ad9d-4fca-b55a-8177abf798bf")
        ).andExpect(status().isOk());
    }

    @Test
    void getWalletBalanceInvalidIdTest() throws Exception {
        mockMvc.perform(
                get("/api/v1/wallets/{id}", "123")
        ).andExpect(status().isBadRequest());
    }

    @Test
    void getWalletBalanceEmptyIdTest() throws Exception {
        mockMvc.perform(
                get("/api/v1/wallets")
        ).andExpect(status().isNotFound());
    }

    @Test
    void createWalletTest() throws Exception {
        mockMvc.perform(
                post("/api/v1/create")
        ).andExpect(status().isOk());
    }
}
