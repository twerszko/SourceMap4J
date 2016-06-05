package twerszko.sourcemap.internal;

import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.script.ScriptException;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.Assertions.assertThat;


public class EngineTest {

    private static Engine engine;

    @BeforeClass
    public static void setUpClass() throws Exception {
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
        engine.evalResource("es6-test.js");

        // when
        ScriptObjectMirror rawResult = engine.invokeFunction("testEs6");
        String[] result = rawResult.to(String[].class);

        // then
        assertThat(result).containsOnly("a", "b", "c");
    }
}