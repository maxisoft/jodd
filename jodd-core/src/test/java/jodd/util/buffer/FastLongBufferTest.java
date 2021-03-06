// Copyright (c) 2003-2014, Jodd Team (jodd.org). All Rights Reserved.

package jodd.util.buffer;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class FastLongBufferTest extends FastBufferTestBase {

	@Test
	public void testAppend() {
		FastLongBuffer buff = new FastLongBuffer(3);

		buff.append(buff);
		buff.append(173);
		buff.append(array(8,98));

		assertArrayEquals(array(173, 8, 98), buff.toArray());

		buff.append(buff);

		assertArrayEquals(array(173, 8, 98, 173, 8, 98), buff.toArray());

		buff.append(array(173, 5, 3), 1, 1);

		assertArrayEquals(array(173, 8, 98, 173, 8, 98, 5), buff.toArray());

		FastLongBuffer buff2 = new FastLongBuffer(3);
		buff2.append(buff);

		assertEquals(7, buff2.toArray().length);
	}

	@Test
	public void testChunks() {
		FastLongBuffer buff = new FastLongBuffer();

		assertEquals(-1, buff.index());
		assertEquals(0, buff.offset());

		buff.append(1);

		assertEquals(0, buff.index());
		assertEquals(1, buff.offset());

		buff.append(2);

		assertEquals(2, buff.offset());

		for (int i = 3; i <= SIZE; i++) {
			buff.append(i);
		}

		assertEquals(0, buff.index());
		assertEquals(SIZE, buff.offset());

		buff.append(SIZE + 1);
		assertEquals(1, buff.index());
		assertEquals(1, buff.offset());

		long[] a = buff.array(0);

		for (int i = 1; i <= SIZE; i++) {
			assertEquals(i, a[i - 1]);
		}
	}

	@Test
	public void testChunksOverflow() {
		FastLongBuffer buff = new FastLongBuffer();

		assertEquals(-1, buff.index());
		assertEquals(0, buff.offset());

		int sum = 0;

		for (int j = 0; j < COUNT; j++) {
			for (int i = 1; i <= SIZE; i++) {
				buff.append(i);
				sum += i;
			}
		}

		assertEquals(15, buff.index());
		assertEquals(1024, buff.offset());

		buff.append(-1);
		sum--;
		assertEquals(16, buff.index());
		assertEquals(1, buff.offset());

		int sum2 = 0;

		for (int i = 0; i < buff.size(); i++) {
			sum2 += buff.get(i);
		}

		assertEquals(sum, sum2);
	}

	@Test
	public void testClear() {
		FastLongBuffer buff = new FastLongBuffer();

		assertTrue(buff.isEmpty());

		buff.append(1);

		assertFalse(buff.isEmpty());

		buff.clear();

		assertTrue(buff.isEmpty());

		try {
			buff.get(0);
			fail();
		} catch (IndexOutOfBoundsException ignore) {
		}

		long[] arr = buff.toArray();

		assertEquals(0, arr.length);
	}

	@Test
	public void testToArray() {
		FastLongBuffer buff = new FastLongBuffer();

		int sum = 0;

		for (int j = 0; j < COUNT; j++) {
			for (int i = 1; i <= SIZE; i++) {
				buff.append(i);
				sum += i;
			}
		}

		buff.append(173);
		sum += 173;

		long[] array = buff.toArray();
		int sum2 = 0;
		for (long l : array) {
			sum2 += l;
		}

		assertEquals(sum, sum2);


		array = buff.toArray(1, buff.size() - 2);
		sum2 = 0;
		for (long l : array) {
			sum2 += l;
		}

		assertEquals(sum - 1 - 173, sum2);
	}

	@Test
	public void testToSubArray() {
		FastLongBuffer buff = new FastLongBuffer();

		int total = SIZE + (SIZE/2);

		for (int i = 1; i <= total; i++) {
			buff.append(i);
		}

		long[] array = buff.toArray(SIZE + 1, total - SIZE  - 1);

		assertEquals(total - SIZE - 1, array.length);
		assertEquals(SIZE + 2, array[0]);
	}


	protected long[] array(long... arr) {
		return arr;
	}

}