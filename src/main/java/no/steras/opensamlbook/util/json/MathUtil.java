package no.steras.opensamlbook.util.json;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MathUtil {
	private static final int DEF_DIV_SCALE = 10;

	private MathUtil() {
	}

	/**
	 * �ṩ��ȷ�ļӷ����㡣
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * @return }�����ĺ�
	 */
	public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
		return b1.add(b2);
	}

	/**
	 * �ṩ��ȷ�ļ����㡣
	 * 
	 * @param v1 ������
	 * @param v2 ����
	 * 
	 * @return }�����Ĳ�
	 * 
	 */

	public static BigDecimal sub(BigDecimal b1, BigDecimal b2) {
		return b1.subtract(b2);
	}

	/**
	 * 
	 * �ṩ��ȷ�ĳ˷����㡣
	 * 
	 * @param v1 ������
	 * 
	 * @param v2 ����
	 * 
	 * @return }�����Ļ�
	 * 
	 */

	public static BigDecimal mul(BigDecimal b1, BigDecimal b2) {
		return b1.multiply(b2);
	}

	/**
	 * 
	 * �ṩ����ԣ���ȷ�ĳ����㣬�����������ʱ����ȷ��
	 * 
	 * С����Ժ�10λ���Ժ�������������롣
	 * 
	 * @param v1 ������
	 * 
	 * @param v2 ����
	 * 
	 * @return }��������
	 * 
	 */

	public static BigDecimal div(BigDecimal b1, BigDecimal b2) {
		return div(b1, b2, DEF_DIV_SCALE);
	}

	/**
	 * 
	 * �ṩ����ԣ���ȷ�ĳ����㡣�����������ʱ����scale����ָ
	 * 
	 * �����ȣ��Ժ�������������롣
	 * 
	 * @param b1 ������
	 * 
	 * @param b2 ����
	 * 
	 * @param scale ��ʾ��ʾ��Ҫ��ȷ��С����Ժ�λ��
	 * 
	 * @return }��������
	 * 
	 */

	public static BigDecimal div(BigDecimal b1, BigDecimal b2, int scale) {

		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);

	}

	/**
	 * 
	 * �ṩ��ȷ��С��λ�������봦�?
	 * 
	 * @param v ��Ҫ�������������
	 * 
	 * @param scale С������λ
	 * 
	 * @return ���������Ľ��
	 * 
	 */

	public static BigDecimal round(BigDecimal b, int scale) {

		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP);

	}

	/**
	 * �Է��ü������е���ݽ����������룬������ȷ���������
	 * 
	 * @param b
	 * @param scale
	 * @return
	 */
	public static BigDecimal roundFeeResult(BigDecimal b, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}

		if (b.doubleValue() == 0) {
			return MathUtil.round(b, scale);
		}

		BigDecimal temp = b;
		temp = temp.multiply(new BigDecimal(100));
		temp = MathUtil.round(temp, scale);
		temp = temp.multiply(new BigDecimal(0.01));
		temp = MathUtil.round(temp, scale);
		return temp;
	}

	/**
	 * Des:格式化小数点<br>
	 * Logic:<br>
	 * 
	 * @param obj 只处理Float Double两种类型
	 * @return <br>
	 *         Kuhn Chen crated the method at 2010-6-20
	 */

	public static Double formatNumber(Object obj) {
		try {
			if (obj != null) {
				if (obj.getClass().equals(Float.class) || obj.getClass().equals(Double.class)) {
					double pDouble = 0D;
					if (obj.getClass().equals(Float.class)) {
						pDouble = ((Float) obj).doubleValue();
					}
					if (obj.getClass().equals(Double.class)) {
						pDouble = ((Double) obj).doubleValue();
					}
					BigDecimal bd = new BigDecimal(pDouble);
					BigDecimal bd1 = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
					pDouble = bd1.doubleValue();
					return new Double(pDouble);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * 获得百分比
	 * 
	 * @param p1 分子
	 * @param p2 分母
	 * @return<br>
	 *         Arlon create this method at 2010-6-18
	 */
	public static String getPercentage(double p1, double p2) {
		String str;
		if (p1 == 0)
			return "0.00%";
		if (p2 == 0)
			return "0.00%";
		double p3 = p1 / p2;
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2);
		str = nf.format(p3);
		return str;
	}

	/**
	 * 获得百分比的分子（80%--->80）
	 * 
	 * @param p1
	 * @param p2
	 * @return <br>
	 *         Arlon create this method at 2010-6-29
	 */
	public static Float getPercentageRate(double p1, double p2) {
		if (p1 == 0)
			return 0.00F;
		if (p2 == 0)
			return 0.00F;
		double p3 = p1 / p2;
		NumberFormat decimalFormat = DecimalFormat.getInstance();
		String pattern = "##########";
		((DecimalFormat) decimalFormat).applyPattern(pattern);
		decimalFormat.setMinimumFractionDigits(2);
		decimalFormat.setMaximumFractionDigits(2);
		String d = decimalFormat.format(p3 * 100);
		return Float.valueOf(d);
	}
}
