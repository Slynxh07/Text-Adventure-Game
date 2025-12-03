package org.sean;

public enum LockStates {
    LOCKED(true),
    UNLOCKED(false);

    final boolean state;

    LockStates(boolean state) {
        this.state = state;
    }
}
