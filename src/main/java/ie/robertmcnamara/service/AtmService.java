package ie.robertmcnamara.service;

import java.util.Map;

/**
 * Simple service that represents a ATM
 */
public interface AtmService {
    Map<Integer, Integer> getNotesAvailable();
    void updateNoteAvailability(int note, int newQuantity);
    int getCurrentBalance();
}
