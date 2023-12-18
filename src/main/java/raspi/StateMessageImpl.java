package raspi;

public class StateMessageImpl implements StateMessage {
    private final State state;
    private final String value;

    public StateMessageImpl(State state, String value) {
        this.state = state;
        this.value = value;
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public String getValue() {
        return value;
    }
}
