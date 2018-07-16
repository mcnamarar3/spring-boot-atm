package ie.robertmcnamara.service;

import java.util.Map;

import ie.robertmcnamara.service.AtmService;
import ie.robertmcnamara.service.impl.AtmServiceImpl;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AtmServiceTest {
     
    private AtmService atmService = new AtmServiceImpl();
    
    @Test
    public void checkDenominations() {
        Map<Integer, Integer> notes = atmService.getNotesAvailable();
        // 50 euro expect 20 notes
        assertEquals(20, (int) notes.get(50));
        // 20 euro expect 30 notes
        assertEquals(30, (int) notes.get(20));
        // 10 euro expect 30 notes
        assertEquals(30, (int) notes.get(10));
        // 5 euro expect 20 notes
        assertEquals(20, (int) notes.get(5));
    }
    
    @Test
    public void checkBalance() {
        assertEquals(2000, atmService.getCurrentBalance()); 
    }
}
