package twerszko.sourcemap;

import com.google.common.collect.Lists;
import com.google.common.io.Closeables;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import twerszko.sourcemap.internal.Engine;
import twerszko.sourcemap.internal.Utils;

import javax.script.ScriptException;
import java.io.InputStream;
import java.util.List;


public class SourceMapConsumer {
    private final Engine engine;
    private final JSObject delegate;

    private SourceMapConsumer(Engine engine, JSObject delegate) {
        this.engine = engine;
        this.delegate = delegate;
    }

    public static SourceMapConsumer create(Engine engine, InputStream jsonStream) throws ScriptException {
        try {
            JSObject json = engine.parseJson(Utils.streamToString(jsonStream));
            JSObject nativeConsumer = engine.newObject(engine.eval("sourceMap.SourceMapConsumer"), json);
            return new SourceMapConsumer(engine, nativeConsumer);
        } finally {
            Closeables.closeQuietly(jsonStream);
        }
    }

    public List<String> sources() throws ScriptException {
        ScriptObjectMirror sources = engine.getMember(delegate, "sources");
        return Lists.newArrayList(sources.to(String[].class));
    }

    public OriginalPosition originalPositionFor(int line, int column) throws ScriptException, NoSuchMethodException {
        String json = "{\"line\":" + line + ", \"column\":" + column + "}";
        JSObject argument = engine.parseJson(json);
        JSObject result = engine.invokeMethod(delegate, "originalPositionFor", argument);

        return OriginalPosition.newPosition()
                .inLine(toLong(engine.getMember(result, "line")))
                .inColumn(toLong(engine.getMember(result, "column")))
                .inSource(engine.getMember(result, "source"))
                .withName(engine.getMember(result, "name"))
                .build();
    }

    private Long toLong(Double doubleValue) {
        return doubleValue == null ? null : doubleValue.longValue();
    }

}
