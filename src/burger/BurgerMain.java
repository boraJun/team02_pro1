package burger;

import java.util.Scanner;

public class BurgerMain {
	// 버거 주문 관리 시스템
	static OrderManager orderManager;

	// 초기 선택 값
	enum InputType {
		LOGOUT(0), // 로그아웃
		ORDER(1), // 주문 시 선택 입력 값
		REFUND(2); // 환불 시 선택 입력 값

		InputType(int type) {
			this.type = type;
		}

		int type;
	}

	// 고객 정보 조회
	// 리턴타입 : boolean
	// 매개변수 : String
	static boolean isExistCustomer(String phoneNumber) {
		return orderManager.isExistCustomer(phoneNumber) == -1 ? false : true;
	}

	// 입력값에 따라 사용자 정보 생성
	// 리턴타입 : Customer 생성한고객정보
	// 매개변수 : 입력클래스, String
	static Customer createCustomerInfo(Scanner sc, String phoneNumber) {
		// 고객명, 주소, 전화번호
		System.out.print("이름 : ");
		String customerName = sc.nextLine();

		System.out.print("주소 : ");
		String address = sc.nextLine();

		//객체 생성 및 반환
		return new Customer(customerName, address, phoneNumber);
	}

	// 입력값 판단하여 boolean 반환
	// 리턴타입 : boolean 입력값판단
	// 매개변수 : String 안내문구, 입력클래스
	static boolean checkInput(String str, Scanner sc) {
		while (true) {
			System.out.println(str + "(y/n) : ");
			String input = sc.nextLine();

			if (input.equals("y") || input.equals("Y"))
				return true;
			if (input.equals("n") || input.equals("N"))
				return false;

			System.out.println("잘못된 입력입니다. 다시 입력해주세요");
		}
	}

	// 메뉴판 출력
	// 리턴타입 : x, 매개변수 : x
	static void printMenu() {
		int cnt = orderManager.burgerMenuArr.length;

		System.out.println("[ 메뉴판 ]");
		
		// 버거 메뉴 출력
		for (int i = 0; i < cnt; i++) {
			System.out.printf("메뉴번호 %d) %s 단품 가격 : %d, 세트 가격 : %d\n", orderManager.burgerMenuArr[i].burgerId,
					orderManager.burgerMenuArr[i].burgerName, orderManager.burgerMenuArr[i].burgerPrice,
					orderManager.burgerMenuArr[i].setPrice);
		}

		System.out.println("(메뉴 번호를 입력해주세요.)");
		System.out.println("** q : 메뉴 입력 종료");
	}

	// 메뉴 선택
	// 리턴타입 : BurgerMenu 선택한버거메뉴정보
	// 매개변수 : 입력클래스
	static BurgerMenu inputMenu(Scanner sc) {
		BurgerMenu burgerMenu = null;

		while (true) {
			String input = sc.nextLine();

			if (input.equals("q") || input.equals("Q")) {
				System.out.println("메뉴 입력을 종료합니다.");
				break;
			}

			int burgerId = Integer.parseInt(input); // 숫자인지 판단 필요
			burgerMenu = orderManager.getBurgerItem(burgerId);

			if (burgerMenu == null) {
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
				System.out.print("번호 입력 : ");
			} else {
				break;
			}
		}

		return burgerMenu;
	}

	// 주문서 리스트 출력
	// 리턴타입 : void
	// 매개변수 : PurchaseOrder[] 주문서배열
	static void printPurchaseOrder(PurchaseOrder[] purchaseOrderArr) {
		for (int i = 0; i < purchaseOrderArr.length; i++) {
			if (purchaseOrderArr[i] == null)
				continue;
			
			System.out.println("주문번호 : " + purchaseOrderArr[i].orderNumber);

			if (purchaseOrderArr[i].burgerList == null) {
				System.out.println("주문 정보 없음");
				continue;
			}

			System.out.println("[ 주문 정보 ]");
			for (int j = 0; j < purchaseOrderArr[i].burgerList.length; j++) {
				System.out.printf("%s : %d원 세트 여부 : %s\n", purchaseOrderArr[i].burgerList[j].burgerName,
						purchaseOrderArr[i].burgerList[j].price, purchaseOrderArr[i].burgerList[j].isSet);
			}

			System.out.println();
		}
	}

	// 주문 번호 입력
	// 리턴타입 : int 주문번호
	// 매개변수 : 입력클래스
	static int getInputOrderNumber(Scanner sc) {
		String input = sc.nextLine();

		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) < '0' || input.charAt(i) > '9') {
				return -1;
			}
		}

		return Integer.parseInt(input);
	}

	// 로그인
	// 리턴타입 : String 전화번호
	// 매개변수 : 입력클래스
	static String login(Scanner sc) {
		System.out.print("고객 ID (전화번호) : "); // 안내 문구 출력
		String phoneNumber = sc.nextLine(); // 전화번호 입력

		// 로그인 가능한지 확인 -> 동일한 phoneNumber가 고객정보에 존재하는지 확인
		if (!isExistCustomer(phoneNumber)) { // 고객 정보가 없을 경우 로그인 실패
			// 안내 문구 출력
			System.out.println("고객 정보 등록이 필요합니다.\n\n전화번호 : " + phoneNumber);

			// 고객 등록 -> 고객정보 인스턴스 생성하여 해당 고객 정보 추가 가능한지 확인
			if (orderManager.addCustomer(createCustomerInfo(sc, phoneNumber))) {
				System.out.println("사용자 추가 완료"); // 결과 출력
			}
		}

		return phoneNumber;
	}

	// 주문,환불,로그아웃 선택 메소드
	// 리턴타입 : int 입력선택값
	// 매개변수 : 입력클래스
	static int inputChoice(Scanner sc) {
		// 주문, 환불 선택을 위한 안내 문구 출력
		String strSelection = "---선택---\n" + "0. 로그아웃\n" + "1. 주문\n" + "2. 환불\n" + "(숫자 입력)\n" + "---------";
		int choice = -1; // 주문, 환불 정보(주문 : 1, 환불 : 2)

		do { // 주문,환불 선택을 위한 반복문 시작
			System.out.println(strSelection); // 주문,환불 선택 출력
			choice = sc.nextInt(); // 주문, 환불 선택 정보 입력
			sc.nextLine(); // 버퍼 소모
			// 주문,환불 선택을 위한 반복문 끝
		} while (choice != InputType.ORDER.type && choice != InputType.REFUND.type && choice != InputType.LOGOUT.type);
		// 주문

		return choice;
	}

	// 버거 주문 입력 메소드
	// 리턴타입 : Burger[] 입력받은버거정보배열
	// 매개변수 : 입력클래스
	static Burger[] inputBurgers(Scanner sc) {
		// 현재 사용자가 주문서에 추가하고 있는 버거 정보 배열
		Burger[] burgerArr = new Burger[orderManager.MAX_BURGER];
		int burgerCnt = 0;

		// 1번 주문 당 가능한 만큼 버거 정보 입력
		for (int i = 0; i < burgerArr.length; i++) {
			// 안내 문구 출력
			System.out.print("번호 입력 : ");

			// 메뉴 입력 -> 메뉴 입력 시 주문한 정보 반환, 메뉴 입력 종료할 경우 null 반환
			BurgerMenu selectedBurger = inputMenu(sc);
			if (selectedBurger == null) { // 메뉴 입력 종료했을 경우
				break; // 반복문 탈출
			}

			// 세트 입력 여부 메소드 호출 및 반환값 변수에 대입
			boolean isSet = checkInput("세트 선택", sc);

			// 사용자가 선택한 버거 정보를 이용하여 버거 정보 인스턴스 생성하여 주문서에 넣은 버거 배열 i 번째에 대입
			burgerArr[i] = new Burger(selectedBurger.burgerId, selectedBurger.burgerName,
					isSet ? selectedBurger.setPrice : selectedBurger.burgerPrice, isSet);
			burgerCnt++;
		}

		return initBurgerArr(burgerArr, burgerCnt);
	}

	// 버거배열 새로 생성하여 반환
	// 리턴타입 : Burger[] 버거배열
	// 매개변수 : 버거배열, int 배열길이
	static Burger[] initBurgerArr(Burger[] burgerArr, int burgerCnt) {
		if (burgerCnt == 0)
			return null;
		
		// 사용자로부터 주문받은 버거 수 만큼 버거정보 배열 생성
		Burger[] finalBurgerArr = new Burger[burgerCnt];

		// 주문받은 버거 수만큼 반복
		for (int i = 0; i < finalBurgerArr.length; i++) {
			finalBurgerArr[i] = burgerArr[i]; // 주문 받은 버거 정보 대입 (얕은 복사)
		}

		return finalBurgerArr;
	}

	//주문서에 담길 총 금액 구하는 메소드
	// 리턴타입 : int 총금액
	// 매개변수 : Burger[] 금액을 계산하기 위한 버거 배열
	static int getTotalPrice(Burger[] burgerArr) {
		if (burgerArr == null)
			return 0;

		int totalPrice = 0;

		for (int i = 0; i < burgerArr.length; i++) {
			if (burgerArr[i] == null)
				continue;
			totalPrice += burgerArr[i].price;
		}

		return totalPrice;
	}
	
	// 환불 성공 여부 출력
	// 리턴타입 : void
	// 매개변수 : boolean 환불성공여부
	static void printSuccessRefund(boolean isSuccess) {
		if (isSuccess) { // 환불 성공
			System.out.println("환불 완료");
		} else { // 환불 실패
			System.out.println("존재하지 않는 주문 번호입니다.\n");
		}
	}

	// 환불
	// 리턴타입 : void
	// 매개변수 : String 전화번호, 입력클래스
	static void refund(String phoneNumber, Scanner sc) {
		// 환불
		// phoneNumber에 해당하는 고객의 주문서 목록 반환하여 선언한 배열에 대입
		PurchaseOrder[] purchaseOrderArr = orderManager.getPurchaseOrder(phoneNumber);

		if (purchaseOrderArr == null) {
			// 주문서 목록이 비어있을 경우
			System.out.println("주문 정보가 없습니다\n");
		} else {// 주문서 목록 받아오고 환불 번호 입력 시작

			// 주문서 목록 출력
			printPurchaseOrder(purchaseOrderArr);

			System.out.print("환불하고자 하는 주문 번호 입력 : ");
			// 주문 번호
			int orderNumber = getInputOrderNumber(sc);

			if (orderNumber != -1) {
				// 환불 메소드 호출
				printSuccessRefund(orderManager.refund(phoneNumber, orderNumber));
			} else {
				System.out.println("잘못된 입력으로 환불 취소");
			}
		} // 주문서 목록 받아오고 환불 번호 입력 끝
	}

	// 주문
	// 리턴타입 : void
	// 매개변수 : String 전화번호, 입력클래스
	static void order(String phoneNumber, Scanner sc) {
		// 주문 시작
		boolean isDelivery = checkInput("배달 선택", sc); // 배달 여부 선택

		printMenu();// 메뉴 출력
		// 배달일 경우
		if (isDelivery) {
			System.out.println("*** 배달 최소 금액 : " + orderManager.MIN_ORDER_AMOUNT); // 배달 최소 금액에 대한 안내 문구 출력
		}

		System.out.println("1번 주문 당 최대 " + orderManager.MAX_BURGER + "개 주문 가능");
		Burger[] burgerArr = inputBurgers(sc); // 주문 받은 버거리스트 반환하는 메소드 호출

		if (burgerArr != null) {
			int totalPrice = getTotalPrice(burgerArr); // 총 가격 구하는 메소드 호출 및 변수에 값 대입

			// 배달이면서 동시에 배달최소금액을 충족하지 못하였을 경우 -> 주문 불가
			if (isDelivery && !orderManager.deliver(totalPrice)) {
				System.out.println("배달 최소 금액 미달 (배달 최소 금액 :" + orderManager.MIN_ORDER_AMOUNT + ")\n");
			} else {
				// 주문서 생성 및 추가
				// 주문 메소드 호출 // 고객 정보 없을 시 실패로 반환
				orderManager.sell(phoneNumber, burgerArr);
				System.out.println("주문 성공\n");
			}
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); // 입력클래스 선언 및 생성 대입
		orderManager = new OrderManager(); // 관리시스템 객체 생성 및 변수에 대입

		boolean flag = true; // 반복문 반복 여부 체크

		while (flag) { // 추가 주문 불가할 경우 반복문 종료
			String phoneNumber = login(sc);
			System.out.println("로그인 성공\n");

			int choice = inputChoice(sc); // 주문, 환불 선택

			if (choice == InputType.LOGOUT.type) // 로그아웃
				continue; // 반복문 시작점으로 이동

			// 주문 선택 했을 경우
			if (choice == InputType.ORDER.type) {
				order(phoneNumber, sc);
			} // 주문 끝
			else if (choice == InputType.REFUND.type) {
				// 환불
				refund(phoneNumber, sc);
			}

			flag = orderManager.canOrder(); // 추가 주문 가능한지 여부 확인
		}

		// 총 매출액 출력
		System.out.println("마감 했습니다. 총 매출 액은 " + orderManager.totalMoney + "원 입니다.");

		// 스캐너 해제
		sc.close();
	}
}
