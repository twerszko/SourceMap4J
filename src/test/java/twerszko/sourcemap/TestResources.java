package twerszko.sourcemap;


import com.google.common.io.Resources;

import java.io.IOException;
import java.io.InputStream;

public class TestResources {
    public static final String SIMPLE_SOURCE_MAP = "simple-source-map.json";
    public static final String ES6_TEST = "es6-test.js";
    public static final String APP_WITH_MODULE = "modules/app-with-module.js";
    public static final String APP_WITH_MODULE_CALL = "modules/app-with-module-call.js";

    public static InputStream stream(String resourceName) throws IOException {
        return Resources.getResource(resourceName).openStream();
    }
}
