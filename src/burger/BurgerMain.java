package burger;

import java.util.Scanner;

public class BurgerMain {
	OrderManager orderManager;

	// 메인 클래스로 이동
	boolean isCorrectPhoneNumber(String phoneNumber) {
		// 고객정보입력할때 addCustomer 전에도 한번하기
		return true;
	}

	//로그인
	boolean login(String phoneNumber) {

		return orderManager.isExistCustomer(phoneNumber) == -1 ? false : true;
	}

	// 입력값에 따라 사용자 정보 생성
	Customer createCustomerInfo(Scanner sc, String phoneNumber) {
		// 고객명, 주소, 전화번호
		System.out.print("이름 : ");
		String customerName = sc.nextLine();

		System.out.print("주소 : ");
		String address = sc.nextLine();

		return new Customer(customerName, address, phoneNumber);
	}

	// 입력값 판단하여 boolean 반환
	boolean checkInput(String str, Scanner sc) {
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
	void printMenu() {
		int cnt = orderManager.burgerMenuArr.length;

		System.out.println("[ 메뉴판 ]");
		for (int i = 0; i < cnt; i++) {
			System.out.printf("메뉴번호 %d) %s 단품 가격 : %d, 세트 가격 : %d\n", orderManager.burgerMenuArr[i].burgerId,
					orderManager.burgerMenuArr[i].burgerName, orderManager.burgerMenuArr[i].burgerPrice,
					orderManager.burgerMenuArr[i].setPrice);
		}

		System.out.println("(메뉴 번호를 입력해주세요.)");
		System.out.println("** q : 메뉴 입력 종료");
	}

	// 메뉴 선택
	BurgerMenu inputMenu(Scanner sc) {
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
	void printPurchaseOrder(PurchaseOrder[] purchaseOrderArr) {
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
	int getInputOrderNumber(Scanner sc) {
		String input = sc.nextLine();
		
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) < '0' && input.charAt(i) > '9') {
				return -1;
			}
		}
		
		return Integer.parseInt(input);
	}

	public static void main(String[] args) {
		BurgerMain bm = new BurgerMain();

		Scanner sc = new Scanner(System.in); // 입력클래스 선언 및 생성 대입
		bm.orderManager = new OrderManager(); // 관리시스템 객체 생성 및 변수에 대입

		// 주문, 환불 선택을 위한 안내 문구 출력
		String strSelection = "---선택---\n" + "0. 로그아웃\n"+ "1. 주문\n" + "2. 환불\n" + "(숫자 입력)\n" + "---------";

		final int LOGOUT = 0; // 로그아웃
		final int ORDER = 1; // 주문 시 선택 입력 값
		final int REFUND = 2; // 환불 시 선택 입력 값

		boolean flag = true; // 반복문 반복 여부 체크

		while (flag) { // 추가 주문 불가할 경우 반복문 종료

			System.out.print("고객 ID (전화번호) : "); // 안내 문구 출력
			String phoneNumber = sc.nextLine(); // 전화번호 입력

			// 로그인 가능한지 확인 -> 동일한 phoneNumber가 고객정보에 존재하는지 확인
			if (!bm.login(phoneNumber)) { // 고객 정보가 없을 경우 로그인 실패
				// 안내 문구 출력
				System.out.println("고객 정보 등록이 필요합니다.\n\n전화번호 : " + phoneNumber);
				
				// 고객 등록 -> 고객정보 인스턴스 생성하여 해당 고객 정보 추가 가능한지 확인
				if (bm.orderManager.addCustomer(bm.createCustomerInfo(sc, phoneNumber))) {
					System.out.println("사용자 추가 완료"); // 결과 출력
				}
			}

			System.out.println("로그인 성공\n"); // 결과 출력

			int choice = -1; // 주문, 환불 정보(주문 : 1, 환불 : 2)

			do { // 주문,환불 선택을 위한 반복문 시작
				System.out.println(strSelection); // 주문,환불 선택 출력
				choice = sc.nextInt(); // 주문, 환불 선택 정보 입력

				// 주문,환불 선택을 위한 반복문 끝
			} while (choice != ORDER && choice != REFUND && choice != LOGOUT ); // 주문 또는 환불이 아닐 경우 반복

			sc.nextLine(); // 버퍼 소모
			if(choice == LOGOUT)
				continue;
			
			// 주문 선택 했을 경우
			if (choice == ORDER) { // 주문 시작

				boolean isDelivery = bm.checkInput("배달 선택", sc); // 배달 여부 선택

				bm.printMenu();// 메뉴 출력

				if (isDelivery) // 배달일 경우
				{
					// 배달 최소 금액에 대한 안내 문구 출력
					System.out.println("*** 배달 최소 금액 : " + bm.orderManager.MIN_ORDER_AMOUNT);
				}

				// 안내 문구 출력
				System.out.println("1번 주문 당 최대 " + bm.orderManager.MAX_BURGER + "개 주문 가능");

				// 현재 사용자가 주문서에 추가하고 있는 버거 정보 배열
				Burger[] burgerArr = new Burger[bm.orderManager.MAX_BURGER];
				int totalPrice = 0; // 총 가격
				int burgerCnt = 0; // 선택한 버거 수

				// 1번 주문 당 가능한 만큼 버거 정보 입력
				for (int i = 0; i < burgerArr.length; i++) {
					// 안내 문구 출력
					System.out.print("번호 입력 : ");

					// 메뉴 입력 -> 메뉴 입력 시 주문한 정보 반환, 메뉴 입력 종료할 경우 null 반환
					BurgerMenu selectedBurger = bm.inputMenu(sc);
					if (selectedBurger == null) { // 메뉴 입력 종료했을 경우
						break; // 반복문 탈출
					}

					// 세트 입력 여부 메소드 호출 및 반환값 변수에 대입
					boolean isSet = bm.checkInput("세트 선택", sc);

					// 사용자가 선택한 버거 정보를 이용하여 버거 정보 인스턴스 생성하여 주문서에 넣은 버거 배열 i 번째에 대입
					burgerArr[i] = new Burger(selectedBurger.burgerId, selectedBurger.burgerName,
							isSet ? selectedBurger.setPrice : selectedBurger.burgerPrice, isSet);

					totalPrice += burgerArr[i].price; // 총 가격
					burgerCnt++; // 버거 수 추가

					// 현재 정보 참고 용 출력
					System.out.println("현재 총 가격 : " + totalPrice + ", 버거 수 : " + burgerCnt);
				}

				// 배달이면서 동시에 배달최소금액을 충족하지 못하였을 경우 -> 주문 불가
				if (isDelivery && !bm.orderManager.deliver(totalPrice)) {
					// 안내 문구 출력
					System.out.println("배달 최소 금액 미달 (배달 최소 금액 :" + bm.orderManager.MIN_ORDER_AMOUNT + ")\n");
				} else { // 주문서 생성 및 추가 시작

					// 사용자로부터 주문받은 버거 수 만큼 버거정보 배열 생성
					Burger[] finalBurgerArr = new Burger[burgerCnt];

					// 주문받은 버거 수만큼 반복
					for (int i = 0; i < finalBurgerArr.length; i++) {
						finalBurgerArr[i] = burgerArr[i]; // 주문 받은 버거 정보 대입 (얕은 복사)
					}

					// 고객 정보 없을 시 실패로 반환
					// 주문 메소드 호출
					bm.orderManager.sell(phoneNumber, finalBurgerArr);

					// 결과 출력
					System.out.println("주문 성공\n");
				} // 주문서 생성 및 추가 끝

			} // 주문 끝
			else if (choice == REFUND) {// 환불 시작
				// 환불
				// phoneNumber에 해당하는 고객의 주문서 목록 반환하여 선언한 배열에 대입
				PurchaseOrder[] purchaseOrderArr = bm.orderManager.getPurchaseOrder(phoneNumber);

				if (purchaseOrderArr == null) // 주문서 목록이 비어있을 경우
					System.out.println("주문 정보가 없습니다\n"); // 결과 출력
				else {// 주문서 목록 받아오고 환불 번호 입력 시작

					// 주문서 목록 출력
					bm.printPurchaseOrder(purchaseOrderArr);

					// 안내 문구 출력
					System.out.print("환불하고자 하는 주문 번호 입력 : ");
					// 주문 번호
					int orderNumber = bm.getInputOrderNumber(sc);
					
					if (orderNumber != -1) {
						// 환불 메소드 호출();
						if (bm.orderManager.refund(phoneNumber, orderNumber)) {
							// 환불 성공
							System.out.println("환불 완료");
						} else {
							// 환불 실패
							System.out.println("존재하지 않는 주문 번호입니다.\n");
						}
					}else {
						System.out.println("잘못된 입력으로 환불 취소");
					}
				} // 주문서 목록 받아오고 환불 번호 입력 끝
			} // 환불 끝

			flag = bm.orderManager.canOrder(); // 추가 주문 가능한지 여부 확인
		}

		// 총 매출액 출력
		System.out.println("마감 했습니다. 총 매출 액은 " + bm.orderManager.totalMoney + "원 입니다.");

		// 스캐너 해제
		sc.close();
	}
}
