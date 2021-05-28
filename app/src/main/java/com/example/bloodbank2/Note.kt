package com.example.bloodbank2

class Note {
    var nodeName:String?=null
    var blood_group:String?=null
    var phnNumber:String?=null
    var address:String?=null

    constructor(nodeName:String,blood_group:String,phnNumber:String,address:String)
    {
        this.nodeName=nodeName
        this.blood_group=blood_group
        this.phnNumber=phnNumber
        this.address=address
    }

}