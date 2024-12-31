package com.law.lawlens09;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.law.lawlens09.databinding.FragmentLibraryBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LibraryFragment : Fragment() {

    private lateinit var binding: FragmentLibraryBinding
    private lateinit var adapter: MeaningAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchBtn.setOnClickListener {
            val word = binding.searchInput.text.toString()
            getMeaning(word)
        }

        adapter = MeaningAdapter(emptyList())
        binding.meaningRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.meaningRecyclerView.adapter = adapter
    }

    private fun getMeaning(word: String) {
        setInProgress(true)
        GlobalScope.launch {
            try {
                val response = RetrofitInstance.dictionaryApi.getMeaning(word)
                requireActivity().runOnUiThread {
                    setInProgress(false)
                    response.body()?.firstOrNull()?.let { setUI(it) }
                        ?: showError("No meaning found for '$word'")
                }
            } catch (e: IOException) {
                showError("Network error occurred")
            } catch (e: HttpException) {
                showError("HTTP error occurred")
            } catch (e: Exception) {
                showError("Something went wrong")
            }
        }
    }

    private fun setUI(response: WordResult) {
        binding.wordTextview.text = response.word
        binding.phoneticTextview.text = response.phonetic
        adapter.updateNewData(response.meanings)
    }

    private fun setInProgress(inProgress: Boolean) {
        binding.searchBtn.visibility = if (inProgress) View.INVISIBLE else View.VISIBLE
        binding.progressBar.visibility = if (inProgress) View.VISIBLE else View.INVISIBLE
    }

    private fun showError(message: String) {
        requireActivity().runOnUiThread { Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show() }
    }
}
