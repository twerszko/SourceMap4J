package twerszko.sourcemap;

import com.google.common.base.MoreObjects;

import javax.annotation.concurrent.Immutable;
import java.util.Objects;

@Immutable
public class Position {
    public final Long line;
    public final Long column;
    public final String source;
    public final String name;

    private Position(Builder builder) {
        this.line = builder.line;
        this.column = builder.column;
        this.source = builder.source;
        this.name = builder.name;
    }

    public static Builder newPosition() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(line, position.line) &&
                Objects.equals(column, position.column) &&
                Objects.equals(source, position.source) &&
                Objects.equals(name, position.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, column, source, name);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("line", line)
                .add("column", column)
                .add("source", source)
                .add("name", name)
                .toString();
    }

    public static class Builder {
        private Long line;
        private Long column;
        private String source = "";
        private String name = "";

        private Builder() {
        }

        public Builder inLine(Long line) {
            this.line = line;
            return this;
        }

        public Builder inColumn(Long column) {
            this.column = column;
            return this;
        }

        public Builder inSource(String source) {
            this.source = source;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Position build() {
            return new Position(this);
        }
    }
}
