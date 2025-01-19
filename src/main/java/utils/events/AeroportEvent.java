package utils.events;

import domain.Entity;

public class AeroportEvent<E extends Entity> implements IEvent {
    private EEventType type;
    private E data, oldData;

    public AeroportEvent(EEventType type, E data) {
        this.type = type;
        this.data = data;
    }

    public AeroportEvent(EEventType type, E data, E oldData) {
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }

    public EEventType getType() {
        return type;
    }

    public E getData() {
        return data;
    }

    public E getOldData() {
        return oldData;
    }
}
