package org.sean;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class GameLibLoader {

    private static GameLib instance;

    public static GameLib get() {
        if (instance != null)
            return instance;

        String os = System.getProperty("os.name").toLowerCase();
        String libPath;

        if (os.contains("win")) {
            libPath = NativeLoader.extractLibrary(
                    "/com/sun/jna/windows/GameLib.dll", "GameLib", ".dll");
        } else {
            libPath = NativeLoader.extractLibrary(
                    "/com/sun/jna/linux-x86-64/libGameLib.so", "libGameLib", ".so");
        }

        instance = Native.load(libPath, GameLib.class);
        return instance;
    }
}
