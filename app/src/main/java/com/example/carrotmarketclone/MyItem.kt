package com.example.carrotmarketclone

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyItem(
    val number: Int,
    val photo : Int,
    val name : String,
    val desc : String,
    val seller : String,
    val price : Int,
    val address : String,
    var likes : Int,
    val chat : Int,
) : Parcelable

val dataList : MutableList<MyItem> = getReviewList()


fun getReviewList() : MutableList<MyItem> {

    val dataList = mutableListOf<MyItem>()

    dataList.add(
        MyItem(
            1,
            R.drawable.sample1,
            "산지 한달된 선풍기 팝니다",
            "이사가서 필요가 없어졌어요 급하게 내놓습니다",
            "대현동",
            1000,
            "서울 서대문구 창천동",
            13,
            25

        )
    )
    dataList.add(
        MyItem(
            2,
            R.drawable.sample2,
            "김치 냉장고",
            "이사로인해 내놔요",
            "안마담",
            20000,
            "인천 계양구 귤현동",
            8,
            28

        )
    )
    dataList.add(
        MyItem(
            3,
            R.drawable.sample3,
            "샤넬 카드지갑",
            "고퀄 지갑이구요 \n 사용감이 있어서 싸게 내어둡니다",
            "코코유",
            10000,
            "수성구 범어동",
            23,
            5

        )
    )
    dataList.add(
        MyItem(
            4,
            R.drawable.sample4,
            "금고",
            "금고 \n떼서 가져가야함 \n대우월드마크센텀\n미국이주관계로 싸게팝니다",
            "Nicole",
            10000,
            "해운대구 우제 2동",
            14,
            17

        )
    )
    dataList.add(
        MyItem(
            5,
            R.drawable.sample5,
            "갤럭시Z플립3 팝니다",
            "갤럭시Z플립3 그린 팝니다\n항시 케이스 씌워서 썼고 필름 한장 챙겨드립니다\n화면에 살짝 스크래치 난거 말고는 크게 이상은 없습니다!",
            "절명",
            150000,
            "연제구 연산제 8동",
            22,
            16

        )
    )
    dataList.add(
        MyItem(
            6,
            R.drawable.sample6,
            "프라다 복조리백",
            "까임 오염 없고 상태 깨끗합니다\n정품 여부 모름",
            "미니멀하게",
            50000,
            "수원시 영통구 원천동",
            25,
            16

        )
    )
    dataList.add(
        MyItem(
            7,
            R.drawable.sample7,
            "울산 동해오션뷰 60평 복층 펜트하우스 1일 숙박권 펜션 힐링 숙소 별장",
            "울산 동해바다뷰 60평 복층 펜트하우스 1일 숙박권\n(에어컨이 없기에 낮은 가격으로 변경했으며 8월 초 가장 더운날 다녀가신 분 경우 시원했다고 잘 지내다 가셨습니다)\\n1. 인원: 6명 기준입니다. 1인 10,000원 추가요금\\n2. 장소: 북구 블루마시티, 32-33층\\n3. 취사도구, 침구류, 세면도구, 드라이기 2개, 선풍기 4대 구비\\n4. 예약방법: 예약금 50,000원 하시면 저희는 명함을 드리며 입실 오전 잔금 입금하시면 저희는 동.호수를 알려드리며 고객님은 예약자분 신분증 앞면 주민번호 뒷자리 가리시거나 지우시고 문자로 보내주시면 저희는 카드키를 우편함에 놓아 둡니다.\\n5. 33층 옥상 야외 테라스 있음, 가스버너 있음\\n6. 고기 굽기 가능\\n7. 입실 오후 3시, 오전 11시 퇴실, 정리, 정돈 , 밸브 잠금 부탁드립니다.\\n8. 층간소음 주의 부탁드립니다.\\n9. 방3개, 화장실3개, 비데 3개\\n10. 저희 집안이 쓰는 별장입니다.",
            "굿리치",
            150000,
            "남구 옥동",
            142,
            54

        )
    )
    dataList.add(
        MyItem(
            8,
            R.drawable.sample8,
            "샤넬 탑핸들 가방",
            "샤넬 트랜디 CC 탑핸들 스몰 램스킨 블랙 금장 플랩백 \n 색상 : 블랙 \n사이즈 : 25.5cm * 17.5cm * 8cm\n구성 : 본품더스트\n급하게 돈이 필요해서 팝니다 ㅠㅠ",
            "난쉽",
            180000,
            "동래구 온천제 2동",
            31,
            7

        )
    )
    dataList.add(
        MyItem(
            9,
            R.drawable.sample9,
            "4행정 엔진 분무기 판매합니다.",
            "3년전에 사서 한번 사용하고 그대로 둔 상태입니다. 요즘 사용은 안해봤습니다. 그래서 저렴하게 내 놓습니다. 중고라 반품은 어렵습니다.",
            "알뜰한",
            30000,
            "원주시 명륜 2동",
            7,
            28

        )
    )
    dataList.add(
        MyItem(
            10,
            R.drawable.sample10,
            "셀린느 버킷 가방",
            "22년 신세계 대전 구매 입니당\n셀린느 버킷백\n구매해서 몇번사용했어요\n까짐 스크래치 없습니다.\n타지역에서 보내는거라 택배로 진행합니당!",
            "똑태현",
            190000,
            "중구 동화동",
            40,
            6

        )
    )

    return dataList

}

fun addLikes(number : Int){
    if (number in 1..dataList.size) {
        val item = dataList[number-1]
        val updatedItem = item.copy(likes = item.likes + 1)
        dataList[number -1] = updatedItem

        val myInterestItem = MyInterestObject.myInterestList[number-1]
        val updatedmyInterestItem = myInterestItem.copy(likes = true)
        MyInterestObject.myInterestList[number-1] = updatedmyInterestItem
    }
}

fun removeLikes(number: Int){
    if (number in 1 .. dataList.size){
        val item = dataList[number-1]
        val updatedItem = item.copy(likes = item.likes - 1)
        dataList[number-1] = updatedItem

        val myInterestItem = MyInterestObject.myInterestList[number-1]
        val updatedmyInterestItem = myInterestItem.copy(likes = false)
        MyInterestObject.myInterestList[number-1] = updatedmyInterestItem
    }
}
