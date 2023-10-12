package com.bxt.reminddrinkwater.ui.home

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import coil.load
import com.bxt.reminddrinkwater.R
import com.bxt.reminddrinkwater.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoadAvatar()
        setupCardContact()
    }

    private fun setupCardContact() {
        binding.btnInstagram.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/tiensbui/")).also {
                it.setPackage("com.instagram.android")
                try {
                    startActivity(it)
                } catch (ex: ActivityNotFoundException) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/tiensbui/")))
                }
            }
        }

        binding.btnMess.setOnClickListener {
            val uri = Uri.parse("fb-messenger://user/$FACEBOOK_ID")
            val toMessenger = Intent(Intent.ACTION_VIEW, uri)
            try {
                startActivity(toMessenger)
            } catch (ex: ActivityNotFoundException) {
                Toast.makeText(context, "Please Install Facebook Messenger", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnCall.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$PHONE_NUMBER")
            startActivity(intent)
        }
    }

    private fun setupLoadAvatar() {
        binding.imvAvatar.load(R.drawable.avatar_demo)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
        const val FACEBOOK_ID = 100011190370160
        const val PHONE_NUMBER = "0886265313"
    }
}
