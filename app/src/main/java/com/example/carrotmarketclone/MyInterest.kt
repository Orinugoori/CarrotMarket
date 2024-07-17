package com.example.carrotmarketclone

data class MyInterest(
    val number : Int,
    var likes : Boolean
)

object MyInterestObject {
    val myInterestList : MutableList<MyInterest> = getMylikeList()

    fun getMylikeList() : MutableList<MyInterest>{
        val myInterestList = mutableListOf<MyInterest>()

        myInterestList.add(MyInterest(1,false))
        myInterestList.add(MyInterest(2,false))
        myInterestList.add(MyInterest(3,false))
        myInterestList.add(MyInterest(4,false))
        myInterestList.add(MyInterest(5,false))
        myInterestList.add(MyInterest(6,false))
        myInterestList.add(MyInterest(7,false))
        myInterestList.add(MyInterest(8,false))
        myInterestList.add(MyInterest(9,false))
        myInterestList.add(MyInterest(10,false))

        return myInterestList
    }

    fun removeInterestItem(position : Int){
//        myInterestList.removeAt(position-1)
    }

}



