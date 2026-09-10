package pyvelum.events;

import jep.python.PyCallable;
import pyvelum.events.PyEvents;

public class PyEvent {
    private final String eventName;
    private final PyEvents events;

    public PyEvent(String string, PyEvents pyEvents) {
        this.eventName = string;
        this.events = pyEvents;
    }

    public void on(PyCallable pyCallable) {
        this.events.register(this.eventName, pyCallable);
    }

    public void set(PyCallable pyCallable) {
        this.events.register(this.eventName, pyCallable);
    }
}

