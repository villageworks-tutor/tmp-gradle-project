package com.example;

public class MyApp {

	/**
	 * ビルド確認用プログラム
	 * @param args
	 */
	public static void main(String[] args) {
		// テストケース確認
		showTaxedPrice();
		// 「Hello, Gradle!」表示
		System.out.println("Hello, Gradle!");
	}

	/**
	 * 動作確認用Javaプログラム：商品の本体価格から税込価格を計算して併記表示する
	 */
	private static void showTaxedPrice() {
		int price = 2300;
		int taxedPrice = calcTax(2300);
		System.out.println("price:\t\t\t" + price + " yen");
		System.out.println("taxed price:\t" + taxedPrice + " yen");
	}

	/**
	 * 本体価格から税込価格を計算する
	 * @param target 本体価格
	 * @return 税込み価格
	 */
	public static int calcTax(int target) {
		double TAX_RATE = 0.10;
		double tax = target * TAX_RATE;
		return (int) (target + tax);
	}

}
