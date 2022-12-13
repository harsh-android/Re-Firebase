package com.example.refirebase.Model

class UserModel {

    lateinit var key: String
    lateinit var name: String
    lateinit var surname: String
    lateinit var email: String
    lateinit var password: String
    lateinit var profile: String


    constructor() {

    }

    constructor(
        key: String,
        name: String,
        surname: String,
        email: String,
        password: String,
        profile: String
    ) {
        this.key = key
        this.name = name
        this.surname = surname
        this.email = email
        this.password = password
        this.profile = profile
    }
}