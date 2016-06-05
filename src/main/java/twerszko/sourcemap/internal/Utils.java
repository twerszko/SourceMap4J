package twerszko.sourcemap.internal;


import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;

import javax.script.ScriptException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {
    public static ScriptException propagate(Exception e) throws ScriptException {
        Throwables.propagateIfInstanceOf(e, ScriptException.class);
        return new ScriptException(e);
    }

    public static String streamToString(InputStream stream) throws ScriptException {
        try {
            return CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
        } catch (Exception e) {
            throw propagate(e);
        } finally {
            Closeables.closeQuietly(stream);
        }
    }
}
