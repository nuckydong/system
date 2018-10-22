package com.gopher.system.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtils {

	
	public static BigDecimal multiply(Double number1, Double number2) {
		if (number1 == null || number2 == null) {
			return null;
		}
		BigDecimal  b1 = new BigDecimal(number1);
		BigDecimal  b2 = new BigDecimal(number2);
		BigDecimal result = b1.multiply(b2);
		result = result.divide(new BigDecimal(1), 2, RoundingMode.HALF_UP);
		return result;
	}
	
	public static BigDecimal multiply(Float number1, Float number2) {
		if (number1 == null || number2 == null) {
			return null;
		}
		BigDecimal  b1 = new BigDecimal(number1);
		BigDecimal  b2 = new BigDecimal(number2);
		BigDecimal result = b1.multiply(b2);
		result = result.divide(new BigDecimal(1), 2, RoundingMode.HALF_UP);
		return result;
	}
	
	
	public static Float divide(Float number, Float divisor, Integer scale) {
		if (number == null || divisor == null) {
			return null;
		}
		if (divisor == 0 || number == 0) {
			return 0F;
		}
		return divide(number.toString(), divisor.toString(), scale).floatValue();
	}

	public static Double divide(Integer number, Integer divisor, Integer scale) {
		if (number == null || divisor == null) {
			return null;
		}
		if (divisor == 0 || number == 0) {
			return 0d;
		}
		return divide(number + "", divisor + "", scale).doubleValue();
	}

	public static Double divide(Integer number, Long divisor, Integer scale) {
		if (number == null || divisor == null) {
			return null;
		}
		if (divisor == 0 || number == 0) {
			return 0d;
		}
		return divide(number + "", divisor + "", scale).doubleValue();
	}

	public static Double divide(Double number, Double divisor, Integer scale) {
		if (number == null || divisor == null) {
			return null;
		}
		if (divisor == 0 || number == 0) {
			return 0d;
		}
		return divide(number + "", divisor + "", scale).doubleValue();
	}

	public static BigDecimal divide(String number, String divisor, Integer scale) {
		if (number == null || divisor == null) {
			return new BigDecimal("0");
		}
		if (divisor.equals("0") || number.equals("0")) {
			return new BigDecimal("0");
		}
		BigDecimal b1 = new BigDecimal(number);
		BigDecimal b2 = new BigDecimal(divisor);
		return b1.divide(b2, scale, RoundingMode.HALF_UP);
	}
}
