package jp.satomaru.util.component;

import jp.satomaru.util.function.ThrowableBiFunction;

@FunctionalInterface
public interface Dispatcher<M, R> extends ThrowableBiFunction<M, ElementSet, R, ComponentException> {
}
