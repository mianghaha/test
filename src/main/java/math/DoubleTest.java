package math;

public class DoubleTest {

	public static void main(String[] args) {
		System.out.println(calcWeekPopularityChartScore(1000,2,3));
		System.out.println(calcWeekPopularityChartScore(1001,2,4));
		double base = Long.MAX_VALUE;
		System.out.println(base);
		System.out.println(Long.MAX_VALUE);
	}

    static double calcWeekPopularityChartScore(final int weekPopularity, final long fightingPower, final int roleLevel)
    {
        final double base = Long.MAX_VALUE;
        return -((weekPopularity * base * base) + (roleLevel * base) + fightingPower);
    }
}
