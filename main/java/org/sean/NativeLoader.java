package org.sean;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;

public final class NativeLoader {

    private static String extractedPath;

    public static synchronized String extractLibrary(String resourcePath, String prefix, String suffix) {
        if (extractedPath != null) {
            return extractedPath;
        }

        try (InputStream in = NativeLoader.class.getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new RuntimeException("Native resource not found: " + resourcePath);
            }

            Path tmp = Files.createTempFile(prefix, suffix);
            tmp.toFile().deleteOnExit();
            Files.copy(in, tmp, StandardCopyOption.REPLACE_EXISTING);

            extractedPath = tmp.toAbsolutePath().toString();
            return extractedPath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to extract native library " + resourcePath, e);
        }
    }
}