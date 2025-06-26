package burger;

public class OrderManager {
//	매출, 주문서[], String 버거종류[], 최소금액, 고객[], int 현재주문갯수, 최대주문수, 한 주문서 당 최대 주문 개수
	int totalMoney; // 총 매출

	final int MAX_ORDER_COUNT; // 최대 주문 수

	final int MAX_BURGER; // 한 주문서 당 최대 버거 수
	int currentOrderCount; // 현재 누적 주문 수
	PurchaseOrder[] purchaseOrderArr; // 주문서 배열

	BurgerMenu[] burgerMenuArr; // 버거 종류 정보

	final int MIN_ORDER_AMOUNT; // 최소 배달 주문 금액

	int currentCustomerCount; // 현재 고객 수
	Customer[] customerArr; // 고객 정보 배열

	private BurgerMenu intBurgerMenu;

	// 기본생성자 구현
	public OrderManager() {
		// 상수 초기화
		MAX_ORDER_COUNT = 5;
		MIN_ORDER_AMOUNT = 12000;
		MAX_BURGER = 10;

		// 인스턴스 변수 초기화
		currentOrderCount = 0;
		currentCustomerCount = 0;

		// 배열 생성
		purchaseOrderArr = new PurchaseOrder[MAX_ORDER_COUNT];
		customerArr = new Customer[MAX_ORDER_COUNT];

		// 버거 메뉴 종류 초기화
		burgerMenuArr = new BurgerMenu[] { new BurgerMenu(BurgerMenu.currentBurgerId++, "새우버거", 7000, 7500),
				new BurgerMenu(BurgerMenu.currentBurgerId++, "불고기버거", 8500, 9000),
				new BurgerMenu(BurgerMenu.currentBurgerId++, "오징어버거", 7800, 8200),
				new BurgerMenu(BurgerMenu.currentBurgerId++, "소고기버거", 9000, 9500), };
	}

	// 승우 : 주문, 서진 : 판매
	// 남혁 : 환불
	// 보라 : 배달여부, 고객 관련

	
	
///////////////////////////배달//////////////////////////////

	
	
	
// 고객배달(); // 고객 배달 // 최소금액확인 //민경승
// 리턴타입 : boolean 배달 가능 여부
// 매개변수 : int 총구매액
	boolean deliver(int total) {
		if (total < MIN_ORDER_AMOUNT) // 최소 배달 주문 금액 보다 매개 변수로 전달받은 총 구매 액이 작을 때
			return false; // 실패

		return true; // 성공
	}

	
	
	
///////////////////////////고객 관련//////////////////////////////

	
	

	
	// 고객 조회
	// 리턴타입 : int 고객의 배열 인덱스 값
	// 매개변수 : String 전화번호
	int isExistCustomer(String phoneNumber) {
		if (customerArr == null) // 생성자에서 배열 생성해주므로 우선 null 아닐 경우만 예외처리
		{
			// 배열 생성
			customerArr = new Customer[MAX_ORDER_COUNT];
			return -1; // 오류 값 반환
		}

		// 고객 배열만큼 반복
		for (int i = 0; i < customerArr.length; i++) {
			if(customerArr[i] == null) // 고객 정보가 비어있으면 
				continue; // 다음 고객 배열 값 조회
			if (customerArr[i].phoneNumber.equals(phoneNumber)) // 전화번호가 이미 등록되어 있으면
				return i; // 해당 고객의 배열 인덱스 값 반환
		}

		return -1; // 오류 값 반환 - 고객 정보 없음
	}

	// 고객 등록
	// 리턴타입 : boolean 추가성공여부
	// 매개변수 : Customer 고객정보
	boolean addCustomer(Customer customer) {
		if (isExistCustomer(customer.phoneNumber) == -1) // 존재하지 않는 사용자
		{
			// 사용자 추가
			this.customerArr[currentCustomerCount++] = new Customer(customer.name, customer.address,
					customer.phoneNumber, MAX_ORDER_COUNT);
			return true; // 추가 성공 반환
		}

		return false; // 이미 사용자가 존재함, 실패 반환
	}

	
	
	
	
	
///////////////////////////주문, 판매//////////////////////////////

	
	
	
	
	
	// 버거 id를 통해 버거 메뉴 정보를 조회
	// 리턴타입 : 버거 메뉴 정보
	// 매개변수 : 버거 id
	BurgerMenu getBurgerItem(int burgerId) {
		BurgerMenu bm = new BurgerMenu(burgerId, burgerMenuArr[burgerId].burgerName, burgerMenuArr[burgerId].burgerPrice, burgerMenuArr[burgerId].setPrice);
		currentOrderCount++;
		System.out.println(currentOrderCount);
		return bm;
	}

	// 버거 id 배열 반환
	// 리턴타입 : int[] 버거메뉴아이디배열
	// 매개변수 : x
	
//	int[] getBurgerMenuIdArr() {
//		return null;
//	}
	

	// 주문 // 주문서 확인 // 황승우 //주문 가능한지 확인 갯수
	// 리턴타입 : boolean 주문가능한지
	// 매개변수 : x
	boolean canOrder() {
		if(PurchaseOrder.currentOrderNumber+1==MAX_ORDER_COUNT) {
			return true;
		}
		return false;
	}

	// 판매 // 주문서추가 //이서진
	// 리턴타입 : boolean 판매성공여부
	// 매개변수 : String 고객구분값, Burger[] 구매하고자하는 버거 정보 배열
	boolean sell(String phoneNumber, Burger[] burgerList) {
		return false;
	}

	
	
	
	
	
	
///////////////////////////환불//////////////////////////////

	
	
	
	
	
	// 환불 // 주문서 삭제 // 고객에 있는 주문아이디 삭제 //이남혁
	// 리턴타입 : boolean
	// 매개변수 : String 고객구분값, int 주문번호
	boolean refund(String phoneNumber, int orderNumber) {
		return false;
	}

	// 환불 가능한지 여부 반환
	// 리턴타입 : int 0번 : 주문서배열의 인덱스, 1번 : 고객배열의 인덱스
	// 매개변수 : String 고객구분값, int 주문번호
	int[] canRefund(String phoneNumber, int orderNumber) {
		return null;
	}

	// 주문 번호 조회
	// 리턴타입 : int 고객배열인덱스값
	// 매개변수 : String 고객구분값, int 주문번호ing
	int isExistOrderNumber(String phoneNumber, int orderNumber) // 고객에게 주문번호 있는지 확인
	{
		return -1;
	}

	// 주문 번호 조회
	// 리턴타입 : int 주문서 배열 인덱스 값
	// 매개변수 : int 주문번호
	int isExistOrderNumber(int orderNumber) { // 관리 시스템에 주문번호 있는지 확인
		return -1;
	}

	// 주문리스트조회(고객정보)
	// 리턴타입 : PurchaseOrder[] 주문서 배열 반환
	// 매개변수 : String 고객구분값
	PurchaseOrder[] getPurchaseOrder(String phoneNumber) {
		return null;
	}
}
