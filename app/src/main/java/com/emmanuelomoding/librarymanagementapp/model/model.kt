package com.emmanuelomoding.librarymanagementapp.modelimport

import com.firebase.ui.database.FirebaseRecyclerOptions

class model {
    var name: String? = null
    var city: String? = null
    var profilepic: String? = null
    var phoneNumber: String? = null
    var address: String? = null
    var userId: String? = null
    var pincode: String? = null
    var categoryImage: String? = null
    var category: String? = null
    var bookLocation: String? = null
    var bookName: String? = null
    var booksCount: String? = null
    var imageUrl: String? = null
    var pushKey: String? = null
    var notification: String? = null

    constructor()
    constructor(
        name: String?,
        city: String?,
        profilepic: String?,
        phoneNumber: String?,
        address: String?,
        userId: String?,
        pincode: String?,
        categoryImage: String?,
        category: String?,
        bookLocation: String?,
        bookName: String?,
        booksCount: String?,
        imageUrl: String?,
        pushKey: String?,
        notification: String?
    ) {
        this.name = name
        this.city = city
        this.profilepic = profilepic
        this.phoneNumber = phoneNumber
        this.address = address
        this.userId = userId
        this.pincode = pincode
        this.categoryImage = categoryImage
        this.category = category
        this.bookLocation = bookLocation
        this.bookName = bookName
        this.booksCount = booksCount
        this.imageUrl = imageUrl
        this.pushKey = pushKey
        this.notification = notification
    }

    @JvmName("getName1")
    fun getName(): String? {
        return name
    }

    @JvmName("setName1")
    fun setName(name: String?) {
        this.name = name
    }

    @JvmName("getCity1")
    fun getCity(): String? {
        return city
    }

    @JvmName("setCity1")
    fun setCity(city: String?) {
        this.city = city
    }

    @JvmName("getProfilepic1")
    fun getProfilepic(): String? {
        return profilepic
    }

    @JvmName("setProfile1")
    fun setProfilePic(profilepic: String?) {
        this.profilepic = profilepic
    }

    @JvmName("getPhoneNumber1")
    fun getPhoneNumber(): String? {
        return phoneNumber
    }

    @JvmName("setPhoneNumber1")
    fun setPhoneNumber(phoneNumber: String?) {
        this.phoneNumber = phoneNumber
    }

    @JvmName("getAddress1")
    fun getAddress(): String? {
        return address
    }

    @JvmName("setAddress1")
    fun setAddress(address: String?) {
        this.address = address
    }

    @JvmName("getUserId1")
    fun getUserId(): String? {
        return userId
    }

    @JvmName("setUserId1")
    fun setUserId(userId: String?) {
        this.userId = userId
    }

    @JvmName("getPincode1")
    fun getPincode(): String? {
        return pincode
    }

    @JvmName("setPincode1")
    fun setPincode(pincode: String?) {
        this.pincode = pincode
    }

    @JvmName("getCategoryImage1")
    fun getCategoryImage(): String? {
        return categoryImage
    }

    @JvmName("setCategoryImage1")
    fun setCategoryImage(categoryImage: String?) {
        this.categoryImage = categoryImage
    }

    @JvmName("getCategory1")
    fun getCategory(): String? {
        return category
    }

    @JvmName("setCategory1")
    fun setCategory(category: String?) {
        this.category = category
    }

    @JvmName("getBookLocation1")
    fun getBookLocation(): String? {
        return bookLocation
    }

    @JvmName("setBookLocation1")
    fun setBookLocation(bookLocation: String?) {
        this.bookLocation = bookLocation
    }

    @JvmName("getBookName1")
    fun getBookName(): String? {
        return bookName
    }

    @JvmName("setBookName1")
    fun setBookName(bookName: String?) {
        this.bookName = bookName
    }

    @JvmName("getBooksCount1")
    fun getBooksCount(): String? {
        return booksCount
    }

    @JvmName("setBooksCount1")
    fun setBooksCount(booksCount: String?) {
        this.booksCount = booksCount
    }

    @JvmName("getImageUrl1")
    fun getImageUrl(): String? {
        return imageUrl
    }

    @JvmName("setImageUrl1")
    fun setImageUrl(imageUrl: String?) {
        this.imageUrl = imageUrl
    }

    @JvmName("getPushKey1")
    fun getPushKey(): String? {
        return pushKey
    }

    @JvmName("setPushKey1")
    private fun setPushKey(pushKey: String?) {
        this.pushKey = pushKey
    }

    @JvmName("getNotification1")
    fun getNotification(): String? {
        return notification
    }

    @JvmName("setNotification1")
    fun setNotification(notification: String?) {
        this.notification = notification
    }
}