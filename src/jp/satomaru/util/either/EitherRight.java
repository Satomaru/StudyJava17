package jp.satomaru.util.either;

/**
 * ‰E‚ÌŒ^‚ğ•Û‚µ‚Ü‚·B
 *
 * @author Satomaru
 * @param <L> ¶‚ÌŒ^
 * @param <R> ‰E‚ÌŒ^
 */
public record EitherRight<L, R> (R value) implements Either<L, R> {

	@Override
	public boolean isRight() {
		return true;
	}
}
