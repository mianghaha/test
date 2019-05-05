package drawlottery;

/**
 * 抽奖奖品实体类
 * @author irving
 * @since 2017年7月23日 下午9:41:33
 * @version MARK 0.0.1
 */
public class Gift {

    private int id;         //奖品Id
    private String name;    //奖品名称
    private double prob;    //获奖概率
    private int amount;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getProb() {
        return prob;
    }
    public void setProb(double prob) {
        this.prob = prob;
    }
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Gift [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", prob=");
		builder.append(prob);
		builder.append(", amount=");
		builder.append(amount);
		builder.append("]");
		return builder.toString();
	}
}