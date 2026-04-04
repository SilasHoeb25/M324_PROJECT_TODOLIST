package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DemoApplication demoApplication;

    // Vor jedem Test die Aufgabenliste zurücksetzen
    @BeforeEach
    void setUp() {
        demoApplication.getTasks().clear();
    }

    // Test: Smoke-Test - Spring Kontext startet korrekt
    @Test
    void contextLoads() {
        assertTrue(true, "alles gut");
    }

    // Test 8: Doppelter Eintrag wird abgelehnt
    @Test
    void doppelterEintragWirdAbgelehnt() throws Exception {
        String aufgabe = "{\"taskdescription\":\"Einkaufen\"}";

        // Erste Anfrage: Aufgabe hinzufügen
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aufgabe))
                .andExpect(status().isOk());

        // Zweite Anfrage: dieselbe Aufgabe nochmals hinzufügen
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aufgabe))
                .andExpect(status().isOk());

        // Liste muss trotzdem nur 1 Eintrag haben
        assertEquals(1, demoApplication.getTasks().size());
    }

    // Test 4: Element wird korrekt aus der Liste entfernt
    @Test
    void elementWirdKorrektEntfernt() throws Exception {
        String aufgabe = "{\"taskdescription\":\"Sport machen\"}";

        // Aufgabe zuerst hinzufügen
        mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aufgabe))
                .andExpect(status().isOk());

        // Aufgabe löschen
        mockMvc.perform(post("/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(aufgabe))
                .andExpect(status().isOk());

        // Liste muss leer sein
        assertEquals(0, demoApplication.getTasks().size());
    }
}