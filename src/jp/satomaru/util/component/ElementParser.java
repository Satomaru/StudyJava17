package jp.satomaru.util.component;

import jp.satomaru.util.function.ThrowableFunction;

@FunctionalInterface
public interface ElementParser<V> extends ThrowableFunction<String, V, ComponentException> {

}
