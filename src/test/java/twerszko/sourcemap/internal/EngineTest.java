package twerszko.sourcemap.internal;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.junit.Before;
import org.junit.Test;

import javax.script.Bindings;
import javax.script.CompiledScript;
import javax.script.ScriptException;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static javax.script.ScriptContext.ENGINE_SCOPE;
import static org.fest.assertions.Assertions.assertThat;
import static twerszko.sourcemap.TestResources.*;


public class EngineTest {

    private Engine engine;

    @Before
    public void setUp() throws Exception {
        engine = Engine.create();
    }

    @Test
    public void should_invoke_function() throws Exception {
        // given
        engine.eval("function giveMeThatString(string){ return string; }");
        // when
        String result = engine.invokeFunction("giveMeThatString", "test");
        // then
        assertThat(result).isEqualTo("test");
    }

    @Test
    public void should_throw_when_evaluating_incorrect_script() throws Exception {
        // when
        catchException(engine).eval("function () {");
        // then
        assertThat((Exception) caughtException()).isInstanceOf(ScriptException.class);
    }

    @Test
    public void should_parse_json() throws Exception {
        // given
        String json = "{\"test\":\"abc\"}";
        // when
        JSObject result = engine.parseJson(json);
        // then
        assertThat(engine.<String>getMember(result, "test")).isEqualTo("abc");
    }

    @Test
    public void should_eval_es6_features() throws Exception {
        // given
        engine.evalResource(ES6_TEST);
        // when
        ScriptObjectMirror rawResult = engine.invokeFunction("testEs6");
        String[] result = rawResult.to(String[].class);
        // then
        assertThat(result).containsOnly("a", "b", "c");
    }

    @Test
    public void should_load_exported_module() throws Exception {
        // given
        engine.evalResource(APP_WITH_MODULE);
        // when
        String result = engine.invokeFunction("callHello", "Bob!");
        // then
        assertThat(result).isEqualTo("Bob!");
    }

    @Test
    public void should_compile_and_invoke_code() throws Exception {
        // given
        CompiledScript compiledScript = engine.compileResource(APP_WITH_MODULE_CALL);
        Bindings bindings = engine.scriptEngine().getBindings(ENGINE_SCOPE);
        bindings.put("name", "Bob!");

        // when
        String result = Engine.eval(compiledScript, bindings);

        // then
        assertThat(result).isEqualTo("Bob!");

    }
}