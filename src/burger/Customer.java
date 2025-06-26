package burger;

public class Customer {
	// 고객명, 주소, 전화번호, 주문번호[]
	String name; // 고객명
	String address; // 주소
	String phoneNumber; // 전화번호

	int[] orderNumberArr; // 주문 번호 배열OrderNumber

	// 생성자
	public Customer(String name, String address, String phoneNumber) {
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;

		orderNumberArr = null;
	}

	// 생성자
	public Customer(String name, String address, String phoneNumber, int orderMaxCount) {
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;

		// orderNumberArr 초기화
		initOrderNumberArr(orderMaxCount);
	}


	// orderNumberArr 초기화
	// 리턴타입 : void
	// 매개변수 : int 주주문서배열크기
	void initOrderNumberArr(int orderMaxCount) {
		orderNumberArr = new int[orderMaxCount]; // 배열 생성

		// 배열 크기 만큼 반복
		for (int i = 0; i < orderMaxCount; i++) {
			orderNumberArr[i] = -1; // -1로 초기화, 비어있는 값으로 판단
		}
	}

	// 주문 번호 조회
	// 리턴타입 : int 주문번호배열의 인덱스 값
	// 매개변수 : int 주문번호
	int isExistOrderNumber(int orderNumber) {
		if (orderNumberArr == null) // 주문번호 배열이 비어있을경우
			return -1; // 실패 반환
		if (orderNumberArr.length == 0) // 주문번호 배열이 비어있을경우 //안해도 됨
			return -1; // 실패 반환

		// 주문번호 배열의 크기만큼 반복
		for (int i = 0; i < orderNumberArr.length; i++) {
			if (orderNumberArr[i] == orderNumber) // 주문 번호 일치하는 경우
				return i; // 주문번호 배열 인덱스 반환
		}

		return -1; // 실패 반환
	}

	// 주문번호 추가
	// 리턴타입 : boolean 주문추가가능여부
	// 매개변수 : int 주문번호
	boolean addOrder(int orderNumber) {
		// 주문 번호 추가
		int idx = addOrderNumberArrIdx();

		// 주문 번호 배열에 비어있는 값이 없거나 주문 번호 배열이 초기화 되지 않음
		if (idx == -1) {
			return false; // 주문 번호 추가 실패 반환
		}

		orderNumberArr[idx] = orderNumber; // 주문 번호 대입
		return true; // 주문 번호 추가 성공 반환
	}

	// 환불
	// 리턴타입 : boolean 환불성공여부
	// 매개변수 : int 주문번호
	boolean refundOrder(int orderNumber) {
		int idx = isExistOrderNumber(orderNumber); // 주문 번호 조회

		if (idx == -1) // 조회 실패
			return false; // 환불 실패 반환

		orderNumberArr[idx] = -1; // 주문 번호에 비어있는값(-1) 대입
		return true; // 환불 성공 반환
	}

	// 추가할 수 있는 주문 번호 배열 인덱스 반환
	// 리턴타입 : int 비어 있는 주문 번호 배열 인덱스
	// 매개변수 : x
	int addOrderNumberArrIdx() {
		if (orderNumberArr == null) // 주문 번호 배열 비어있을 경우
			return -1; // 실패 반환
		if (orderNumberArr.length == 0) // 주문 번호 배열 비어있을 경우
			return -1; // 실패 반환

		// 주문 번호 배열의 길이만큼 반복
		for (int i = 0; i < orderNumberArr.length; i++) {
			if (orderNumberArr[i] == -1) // 주문 번호 비어있는 위치 확인
				return i; // 비어 있는 주문 번호 배열 인덱스 반환
		}

		return -1; // 비어있는거 없음
	}
}
