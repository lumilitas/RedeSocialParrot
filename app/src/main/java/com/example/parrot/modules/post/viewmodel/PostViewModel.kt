package com.example.parrot.modules.post.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.parrot.core.network.BaseNetwork
import com.example.parrot.core.network.BaseNetwork.RequestStatus.FINISHED
import com.example.parrot.core.network.BaseNetwork.RequestStatus.STARTED
import com.example.parrot.core.viewmodel.BaseViewModel
import com.example.parrot.livedata.SingleLiveEvent
import com.example.parrot.modules.post.business.PostBusiness
import com.example.parrot.modules.post.business.PostBusiness.doPost
import com.example.parrot.modules.post.model.Post

class PostViewModel : BaseViewModel() {

    private val _post = MutableLiveData<List<Post>>()
    val post: LiveData<List<Post>> = _post


    var onPostSucessful = SingleLiveEvent<Void>()
    val onPostRequestStatus = MutableLiveData<BaseNetwork.RequestStatus>()

    fun checkData(mensagem: String?) {

        if (!PostBusiness.isTextPostValid(mensagem)) {
            onError.value = "É necessário digitar alguma coisa!"
            return
        }

    }

    fun doPost(mensagem: String?) {

        onPostRequestStatus.value = STARTED

        doPost(mensagem!!,
                onSuccess = {
                    _post.value = PostBusiness.getPostsDB()
                    onPostSucessful.call()
                    onPostRequestStatus.value = FINISHED
                },
                onError = {
                    onError.value = it
                    onPostRequestStatus.value = FINISHED
                })

    }

    fun getPosts() {
        PostBusiness.getPosts(
            onSuccess = {
                _post.value = it
            },
            onError = {
                print("Erro")
            }
        )
    }

}