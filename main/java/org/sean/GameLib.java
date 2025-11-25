package org.sean;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface GameLib extends Library {
    GameLib INSTANCE = Native.load("libGameLib.so", GameLib.class);
    boolean runGame();
}
