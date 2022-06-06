package jp.satomaru.util;

import java.util.HashMap;
import java.util.Map;

import jp.satomaru.util.function.RetArg2;
import jp.satomaru.util.function.RetArg3;

/**
 * �w�肳�ꂽ�R�}���h�ɑΉ�����֐���I������A�f�B�X�p�b�`���[���쐬���܂��B
 *
 * @author Satomaru
 * @param <M> ���s����֐������I�u�W�F�N�g
 * @param <A> �֐��̈���
 * @param <C> �R�}���h
 * @param <R> ����
 */
public final class Dispatcher<M, A, C, R> {

	/**
	 * �R�}���h�ƁA����ɑΉ�����֐����w�肵�āA�f�B�X�p�b�`���[�̍쐬���J�n���܂��B
	 *
	 * @param <M>       ���s����֐������I�u�W�F�N�g
	 * @param <A>       �֐��̈���
	 * @param <C>       �R�}���h
	 * @param <R>       ����
	 * @param command   �R�}���h
	 * @param processor ���s����֐�
	 * @return �f�B�X�p�b�`���[���쐬����I�u�W�F�N�g
	 */
	public static final <M, A, C, R> Dispatcher<M, A, C, R> begin(C command, RetArg2<M, A, R> processor) {
		var dispatcher = new Dispatcher<M, A, C, R>();
		dispatcher.append(command, processor);
		return dispatcher;
	}

	/** �֐��}�b�v�B */
	private final HashMap<C, RetArg2<M, A, R>> processors = new HashMap<>();

	private Dispatcher() {
	}

	/**
	 * �R�}���h�ƁA����ɑΉ�����֐���ǉ����܂��B
	 *
	 * @param command   �R�}���h
	 * @param processor ���s����֐�
	 * @return ���̃I�u�W�F�N�g���g
	 */
	public Dispatcher<M, A, C, R> append(C command, RetArg2<M, A, R> processor) {
		processors.put(command, processor);
		return this;
	}

	/**
	 * �f�B�X�p�b�`���[�̍쐬���I�����܂��B
	 *
	 * @return �f�B�X�p�b�`���[
	 */
	public RetArg3<M, A, C, R> end() {
		Map<C, RetArg2<M, A, R>> fixed = Map.copyOf(processors);

		return (model, argument, command) -> {
			if (command == null) {
				throw new NullPointerException("command");
			}

			if (!fixed.containsKey(command)) {
				throw new IllegalArgumentException(String.format("command: %s", command));
			}

			return fixed.get(command).execute(model, argument);
		};
	}
}
