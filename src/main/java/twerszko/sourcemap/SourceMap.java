package twerszko.sourcemap;


import twerszko.sourcemap.internal.Engine;

import javax.script.ScriptException;
import java.io.InputStream;

public class SourceMap {
    private final Engine engine;

    private SourceMap(Engine engine) {
        this.engine = engine;
    }

    public static SourceMap create() throws ScriptException {
        Engine engine = Engine.create();
        engine.evalResource("source-map.js");
        engine.evalResource("utils.js");
        return new SourceMap(engine);
    }

    public SourceMapConsumer newSourceMapConsumer(InputStream jsonStream) throws ScriptException {
        return SourceMapConsumer.create(engine, jsonStream);
    }
}
