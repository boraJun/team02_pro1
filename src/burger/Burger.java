package burger;

public class Burger {
	int burgerId;		//버거 id
	String burgerName;	//버거 이름
	int price;			//가격
	
	boolean isSet;		//세트 여부

	//생성자
	public Burger(int burgerId, String burgerName, int price, boolean isSet) {
		this.burgerId = burgerId;
		this.burgerName = burgerName;
		this.price = price;
		this.isSet = isSet;
	}
}