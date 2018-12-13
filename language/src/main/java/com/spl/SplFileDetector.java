package com.spl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;

public class SplFileDetector extends FileTypeDetector {

    @Override
    public String probeContentType(Path path) throws IOException {
        if (path.getFileName().toString().endsWith(SplLanguage.EXTENSION)) {
            return SplLanguage.MIME_TYPE;
        }
        return null;
    }
}
