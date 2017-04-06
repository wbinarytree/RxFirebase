package phoenixlib.io.lib.database;

/**
 * Created by yaoda on 06/04/17.
 */

public class ValueEvent<T> {
    private EventType type;
    private T value;
    private String key;

    public ValueEvent(EventType type, T value) {
        this.type = type;
        this.value = value;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
