package com.tone;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.spi.FileTypeDetector;

public class ToneFileDetector extends FileTypeDetector {

    @Override
    public String probeContentType(Path path) throws IOException {
        if (path.getFileName().toString().endsWith(ToneLanguage.EXTENSION)) {
            return ToneLanguage.MIME_TYPE;
        }
        return null;
    }
}
