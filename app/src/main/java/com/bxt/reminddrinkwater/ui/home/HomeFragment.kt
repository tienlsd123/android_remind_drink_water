package com.bxt.reminddrinkwater.ui.home

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import com.bxt.reminddrinkwater.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.buffer
import okio.sink
import okio.source
import java.io.File

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    private val pickupImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        actionContentResult(it ?: return@registerForActivityResult)
    }

    private fun actionContentResult(uri: Uri) = lifecycleScope.launch {
        val path = cacheFileToInternalStorage(context ?: return@launch, uri) ?: return@launch
        viewModel.saveImagePathToDataStore(path)
        binding.imvAvatar.load(path)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextCountDown()
        setupLoadAvatar()
    }

    private fun setupTextCountDown() {
        lifecycleScope.launch {
            viewModel.countDownTimeFlow.flowWithLifecycle(lifecycle).collectLatest {
                binding.tvCountDown.text = it
            }
        }
    }

    private fun setupLoadAvatar() {
        lifecycleScope.launch {
            viewModel.avatar.collectLatest {
                binding.imvAvatar.load(it)
            }
        }
        binding.imvAvatar.setOnClickListener {
            pickupImage.launch("image/*")
        }
    }

    private suspend fun cacheFileToInternalStorage(context: Context, contentUri: Uri): String? = withContext(Dispatchers.IO) {
        return@withContext try {
            val contentResolver = context.contentResolver
            contentResolver.query(contentUri, null, null, null, null)?.use { cursor ->
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                cursor.moveToFirst()
                val name = cursor.getString(nameIndex)
                val size = cursor.getLong(sizeIndex)
                cursor.close()
                if (size >= MAX_SIZE_CACHE_IN_BYTE) return@withContext null  /* file cache small less 1MB */
                contentResolver.openInputStream(contentUri)?.use { stream ->
                    val folder = File(context.cacheDir, DIR_IMAGE_CACHE)
                    if (!folder.exists()) folder.mkdirs()   /* create folder subtitles_cache */
                    val file = File(folder, name)
                    file.deleteOnExit()
                    val source = stream.source().buffer()
                    file.sink().buffer().also {
                        it.writeAll(source)
                        it.close()
                    }
                    stream.close()
                    file.absolutePath
                }
            }
        } catch (ex: Exception) {
            null
        }
    }

    companion object {
        private const val MAX_SIZE_CACHE_IN_BYTE = 1048576
        private const val DIR_IMAGE_CACHE = "/image_cache/"

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
