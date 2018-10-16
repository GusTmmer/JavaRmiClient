/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Events;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author a1729756
 */
public class PacoteEvent implements IEvent {
    
    private List<IEvent> events = new ArrayList<>();

    public List<IEvent> getEvents() {
        return events;
    }

    public void addEvent(IEvent event) {
        events.add(event);
    }
}
