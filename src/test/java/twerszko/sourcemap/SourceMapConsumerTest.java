package twerszko.sourcemap;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static twerszko.sourcemap.TestResources.SIMPLE_SOURCE_MAP;
import static twerszko.sourcemap.TestResources.stream;


public class SourceMapConsumerTest {

    private static SourceMap sourceMap;

    @BeforeClass
    public static void setUpClass() throws Exception {
        sourceMap = SourceMap.create();
    }

    @Test
    public void should_find_original_position() throws Exception {
        // given
        SourceMapConsumer consumer = sourceMap.newSourceMapConsumer(stream(SIMPLE_SOURCE_MAP));
        OriginalPosition expectedPosition = OriginalPosition.newPosition()
                .inLine(2L)
                .inColumn(10L)
                .inSource("http://example.com/www/js/two.js")
                .withName("n")
                .build();

        // when
        OriginalPosition position = consumer.originalPositionFor(2, 28);

        // then
        assertThat(position).isEqualTo(expectedPosition);
    }

    @Test
    public void should_get_sources() throws Exception {
        // given
        SourceMapConsumer consumer = sourceMap.newSourceMapConsumer(stream(SIMPLE_SOURCE_MAP));
        // when
        List<String> sources = consumer.sources();
        // then
        assertThat(sources).containsExactly("http://example.com/www/js/one.js", "http://example.com/www/js/two.js");
    }
}