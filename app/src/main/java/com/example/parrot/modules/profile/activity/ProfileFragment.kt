package com.example.parrot.modules.profile.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.parrot.R
import com.example.parrot.modules.authentication.model.User
import com.example.parrot.modules.search.activity.ProfileResult
import com.example.parrot.modules.search.database.ProfileDatabase
import com.example.parrot.modules.search.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile_menu.*

class ProfileFragment: Fragment() {

    lateinit var profileViewModel: ProfileViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile_menu, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        profileViewModel = ViewModelProviders.of(activity!!).get(ProfileViewModel::class.java)

        subscribeUI()
    }

    fun setupView(profile: User) {

        userNick.text = profile.username
        followers.text = profile.amigos?.size.toString()

    }

//    fun updateItemView(profile: User) {
//
//        userNick.text = profile.username.toString()
//        followers.text = profile.amigos?.size.toString()
//
//    }

    fun subscribeUI() {

        with(profileViewModel) {
            profile.observe(this@ProfileFragment, Observer { user ->

                user?.let {
                    setupView(it)
                }

            })
        }

    }

}