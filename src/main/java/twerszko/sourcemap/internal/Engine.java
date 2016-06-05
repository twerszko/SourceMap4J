package twerszko.sourcemap.internal;

import com.google.common.io.Resources;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.*;
import java.io.InputStreamReader;

import static twerszko.sourcemap.internal.Utils.propagate;

public class Engine {
    private final ScriptEngine scriptEngine;
    private final Invocable invocable;
    private final Compilable compilable;

    private Engine(ScriptEngine scriptEngine) {
        this.scriptEngine = scriptEngine;
        this.invocable = (Invocable) scriptEngine;
        this.compilable = (Compilable) scriptEngine;
    }

    public static Engine create() throws ScriptException {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("nashorn");
        evalResource(engine, "nashorn-polyfill.js");
        return new Engine(engine);
    }

    @SuppressWarnings("unchecked")
    public <T> T invokeFunction(String functionName, Object... arguments) throws ScriptException {
        try {
            return (T) invocable.invokeFunction(functionName, arguments);
        } catch (Exception e) {
            throw propagate(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T invokeMethod(JSObject target, String methodName, Object... arguments) throws ScriptException {
        try {
            return (T) invocable.invokeMethod(target, methodName, arguments);
        } catch (Exception e) {
            throw propagate(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T newObject(JSObject target, Object... arguments) throws ScriptException {
        try {
            return (T) target.newObject(arguments);
        } catch (Exception e) {
            throw propagate(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getMember(JSObject target, String memberName) throws ScriptException {
        try {
            return (T) target.getMember(memberName);
        } catch (Exception e) {
            throw propagate(e);
        }
    }

    public ScriptObjectMirror parseJson(String json) throws ScriptException {
        return invokeMethod(eval("JSON"), "parse", json);
    }

    public <T> T evalResource(String resourceName) throws ScriptException {
        return evalResource(this.scriptEngine, resourceName);
    }

    @SuppressWarnings("unchecked")
    public <T> T eval(String script) throws ScriptException {
        try {
            return (T) scriptEngine.eval(script);
        } catch (Exception e) {
            throw propagate(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T> T evalResource(ScriptEngine scriptEngine, String resourceName) throws ScriptException {
        try {
            return (T) scriptEngine.eval(new InputStreamReader(Resources.getResource(resourceName).openStream()));
        } catch (Exception e) {
            throw propagate(e);
        }
    }
}
