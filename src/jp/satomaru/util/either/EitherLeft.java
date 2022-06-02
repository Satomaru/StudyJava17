package jp.satomaru.util.either;

/**
 * ¶‚ÌŒ^‚ğ•Û‚µ‚Ü‚·B
 *
 * @author Satomaru
 * @param <L> ¶‚ÌŒ^
 * @param <R> ‰E‚ÌŒ^
 */
public record EitherLeft<L, R> (L value) implements Either<L, R> {

	@Override
	public boolean isRight() {
		return false;
	}
}
