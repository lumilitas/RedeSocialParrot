package com.example.parrot.modules.search.activity.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.parrot.PrincipalActivity
import com.example.parrot.R
import com.example.parrot.inTransaction
import com.example.parrot.modules.profile.activity.ProfileFragment
import com.example.parrot.modules.search.adapter.ProfileAdapter
import com.example.parrot.modules.search.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.activity_principal.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_search_menu.*
import org.jetbrains.anko.support.v4.startActivity

class SearchFragment: Fragment() {

    lateinit var profileViewModel: ProfileViewModel

    lateinit var profileAdapter: ProfileAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_menu, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        profileViewModel = ViewModelProviders.of(activity!!).get(ProfileViewModel::class.java)

        setupView()
        subscribeUI()

        profileViewModel.getProfiles()
    }

    private fun setupView() {

        profileAdapter = ProfileAdapter(
                {
                    profileViewModel.saveProfile(it)

                }
        )

        profileList.adapter = profileAdapter

        searchField.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                profileViewModel.searchProfile(p0.toString())
            }

        })

    }

    private fun subscribeUI() {

        profileViewModel.profiles.observe(this, Observer {
            profileAdapter?.updateProfileList(it)
        })

    }

}