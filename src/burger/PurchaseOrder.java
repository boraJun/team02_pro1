package burger;

//주문서 클래스
public class PurchaseOrder {
//	주문번호, 전화번호, 버거[], 총가격
	static int currentOrderNumber; // 현재 주문 번호
	int orderNumber; // 주문 번호
	String phoneNumber; // 전화 번호(고객 구분 값)
	
	Burger[] burgerList; // 주문한 버거 정보 배열
	int total; // 해당 주문서의 총 가격

	// 생성자
	public PurchaseOrder(String phoneNumber, Burger[] burgerList) {
		PurchaseOrder.currentOrderNumber++; // 다음 주문 번호로 변경
		
		// 주문 번호 대입
		this.orderNumber = PurchaseOrder.currentOrderNumber;
		
		// 전화번호 대입
		this.phoneNumber = phoneNumber;
		
		// 버거 주문 번호 초기화
		initBurgerList(burgerList);
		
		
		// 계산한 총 가격 대입
		this.total = calculateTotal();
	}
	
	// 주문한 버거 배열 배열 초기화
	// 리턴타입 : void
	// 매개변수 : Burger[] 주문받은 버거정보 배열
	void initBurgerList(Burger[] burgerList) {
		if(burgerList == null) { // 주문한 버거 배열 비어있음
			this.burgerList = null; // 주문한 버거 배열 null로 대입
			return; // 끝
		}
		if(burgerList.length == 0) {// 주문한 버거 배열 비어있음
			this.burgerList = null; // 주문한 버거 배열 null로 대입
			return; // 끝
		}
		
		// 버거 배열 생성
		this.burgerList = new Burger[burgerList.length];
		
		// 변수 선언
		Burger burger;
		
		//버거 배열 길이만큼 반복
		for(int i = 0; i < burgerList.length; i++) {
			burger = burgerList[i]; //매개변수로 전달받은 배열 i 번째 대입
			
			//현재 인스턴스 변수 배열에 버거 정보 생성 대입
			this.burgerList[i] = new Burger(burger.burgerId, burger.burgerName, burger.price, burger.isSet);
		}
	}
	
	// 주문서의 총 가격 계산
	// 리턴타입 : int 총가격
	// 매개변수 : x
	int calculateTotal() {
		int cnt = this.burgerList.length; // 버거 배열 길이 대입
		int total = 0; // 반환할 총 가격		
		
		// 버거 배열 길이만큼 반복
		for(int i = 0; i < cnt; i++) {
			total += this.burgerList[i].price; // 총 가격에 버거 가격 추가
		}
		
		return total; // 총 가격 반환
	}
}
