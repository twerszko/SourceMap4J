package twerszko.sourcemap.internal;

import org.junit.Before;
import org.junit.Test;

import javax.script.ScriptException;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.Assertions.assertThat;


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
}