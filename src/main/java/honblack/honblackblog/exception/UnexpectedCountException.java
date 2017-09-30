package honblack.honblackblog.exception;

public class UnexpectedCountException extends RuntimeException {

    public UnexpectedCountException(final int expected, final int actual) {
        super("期待する件数になりませんでした. [expected: " + expected + ", actual: " + actual + "]");
    }
}
