package burger;

public class BurgerMenu {
	static int currentBurgerId; // 현재까지 등록된 버거 id 값

	int burgerId; // 버거 id

	String burgerName; // 버거 이름

	int burgerPrice; // 버거 단품 가격
	int setPrice; // 세트 가격

	// 생성자
	public BurgerMenu(int burgerId, String burgerName, int burgerPrice, int setPrice) {
		this.burgerId = burgerId;
		this.burgerName = burgerName;
		this.burgerPrice = burgerPrice;
		this.setPrice = setPrice;
	}
	

	// 가격 정보 반환
	// 리턴 타입 : int 가격
	// 매개변수 : boolean 세트 여부
	public int getPrice(boolean isSet) {
		return isSet ? setPrice : burgerPrice; // 세트여부에 따라 가격 반환
	}

	////// 아래 두개 메소드는 삭제 가능////////
	// 세트 가격 반환
	// 리턴타입 : int 세트가격
	public int getSetPrice() {
		return setPrice;
	}

	// 세트 가격 반환
	// 리턴타입 : int 버거단품가격
	public int getBurgerPrice() {
		return burgerPrice;
	}
}
