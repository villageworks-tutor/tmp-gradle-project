package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MyAppTest {

	@BeforeEach
	void setUp() throws Exception {
	}
	
	@Test
	void testSucceed() {
		assertEquals(1 + 1, 2);
	}
	
	@Test
	@Disabled // ビルドが失敗することが確認できたら無効化しておく
	void testFailure() {
		fail("まだ実装されていません");
	}

	@ParameterizedTest
	@MethodSource("dataProvider")
	void testCalcTax(int target, int expected) {
		// execute
		int actual = MyApp.calcTax(target);
		// verify
		assertEquals(expected, actual);
	}
	
	static Stream<Arguments> dataProvider() {
		// setup
		List<Integer> target = new ArrayList<Integer>();
		List<Integer> expected = new ArrayList<Integer>();
		
		// Test-01: 100円の商品の税込価格は110円である
		target.add(100);
		expected.add(110);
		
		// Test-02: 360円の商品の税込価格は3960円である
		target.add(360);
		expected.add(396);
		
		// Test-02: 234円の商品の税込価格は257円である
		target.add(234);
		expected.add(257);
		
		// テストパラメータの返却
		return Stream.of(
				Arguments.of(target.get(target.size() - 1), expected.get(expected.size() - 1))
			   );
	}

}
