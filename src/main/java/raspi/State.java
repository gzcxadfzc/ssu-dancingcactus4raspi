package raspi;

import java.util.Arrays;

public enum State {
    PLAY("play", true),
    VOLUME("volume", true),
    RESUME("resume", false),
    PAUSE("pause", false),
    STOP("stop", false),
    EXIT("exit", false),
    UNKNOWN("unknown", false);

    private final String description;
    private final Boolean valueNeeded;

    State(String description, Boolean valueNeeded) {
        this.description = description;
        this.valueNeeded = valueNeeded;
    }

    public static State find(String targetDescription) {
        return Arrays.stream(State.values())
                .filter(state -> state.description.equals(targetDescription))
                .findAny()
                .orElse(UNKNOWN);
    }

    public boolean isValueRequired() {
        return valueNeeded;
    }
}
