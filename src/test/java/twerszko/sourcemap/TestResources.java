package twerszko.sourcemap;


import com.google.common.io.Resources;

import java.io.IOException;
import java.io.InputStream;

public class TestResources {
    public static final String SIMPLE_SOURCE_MAP = "simple-source-map.json";

    public static InputStream stream(String resourceName) throws IOException {
        return Resources.getResource(resourceName).openStream();
    }
}
