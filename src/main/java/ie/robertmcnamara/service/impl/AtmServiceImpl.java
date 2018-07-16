package ie.robertmcnamara.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import ie.robertmcnamara.service.AtmService;

public class AtmServiceImpl implements AtmService {
    
    Map<Integer, Integer> notesAvailable;
    
    public AtmServiceImpl() {
        notesAvailable = new LinkedHashMap<>();
        notesAvailable.put(50, 20);
        notesAvailable.put(20, 30);
        notesAvailable.put(10, 30);
        notesAvailable.put(5, 20);
    }

    @Override
    public Map<Integer, Integer> getNotesAvailable() {
        return notesAvailable;
    }
    
    @Override
    public void updateNoteAvailability(int note, int newQuantity) {
        int oldQuantity = notesAvailable.get(note);
        notesAvailable.put(note, oldQuantity - newQuantity); 
    }

    @Override
    public int getCurrentBalance() {
        int balance = 0;
        
        for (Map.Entry<Integer, Integer> entry : notesAvailable.entrySet()) {
            balance += entry.getKey() * entry.getValue();
        }
        
        return balance;
    }
    
}
