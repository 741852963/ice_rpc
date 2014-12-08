import Demo.*;

class NestedI extends _NestedDisp {
	/**
	 *
	 */
	private static final long serialVersionUID = 2365234470550381004L;

	NestedI(NestedPrx self) {
		_self = self;
	}

	public void nestedCall(int level, NestedPrx proxy, Ice.Current current) {

		System.out.println(level);

		if (--level > 0) {
			proxy.nestedCall(level, _self);
		}
	}

	private NestedPrx _self;
}
