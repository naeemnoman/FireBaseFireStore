package com.example.naeemnoman.firebasefirestore

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naeemnoman.firebasefirestore.adapter.DataAdapter
import com.example.naeemnoman.firebasefirestore.databinding.ActivityMainBinding
import com.example.naeemnoman.firebasefirestore.model.Data
import com.example.naeemnoman.firebasefirestore.viewmodel.DataViewModel
import com.google.firebase.Timestamp

class MainActivity : AppCompatActivity(), DataAdapter.ItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private val dataViewModel: DataViewModel by viewModels()
    private lateinit var adapter: DataAdapter

    private fun clearInputField() {
        binding.idEtxt.text?.clear()
        binding.nameEtxt.text?.clear()
        binding.emailEtxt.text?.clear()
        binding.subjectEtxt.text?.clear()
        binding.birthDateEtxt.text?.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        adapter = DataAdapter(listOf(), this)
        binding.recyclerView.adapter = adapter

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        dataViewModel.dataList.observe(this, Observer {
            adapter.updateData(it)
        })

        binding.saveBtn.setOnClickListener {
            val stuid = binding.idEtxt.text.toString()
            val name = binding.nameEtxt.text.toString()
            val email = binding.emailEtxt.text.toString()
            val subject = binding.subjectEtxt.text.toString()
            val birthdate = binding.birthDateEtxt.text.toString()

            if (stuid.isNotEmpty() && name.isNotEmpty() && email.isNotEmpty() && subject.isNotEmpty() && birthdate.isNotEmpty()) {
                val data = Data(null, stuid, name, email, subject, birthdate, Timestamp.now())
                dataViewModel.addData(data, onSuccess = {
                    clearInputField()
                    Toast.makeText(this@MainActivity, "Data Added Successfully", Toast.LENGTH_SHORT)
                        .show()
                }, onFailure = {
                    Toast.makeText(this@MainActivity, "Failded to add data", Toast.LENGTH_SHORT)
                        .show()
                }
                )
            }


        }


    }
    override fun onEditItemClick(data: Data) {

        binding.idEtxt.setText(data.stuid)
        binding.nameEtxt.setText(data.name)
        binding.emailEtxt.setText(data.email)
        binding.subjectEtxt.setText(data.subject)
        binding.birthDateEtxt.setText(data.birhtdate)

        binding.saveBtn.setOnClickListener {
            val updateData = Data(
                data.id, binding.idEtxt.text.toString(),
                binding.nameEtxt.text.toString(), binding.emailEtxt.text.toString(),
                binding.subjectEtxt.text.toString(), binding.birthDateEtxt.text.toString(),
                Timestamp.now()
            )
            dataViewModel.updateData(updateData)
            clearInputField()
            Toast.makeText(
                this@MainActivity,
                "Data Updated Successfully",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    override fun onDeleteItemClick(data: Data) {
        AlertDialog.Builder(this).apply {
            setTitle("Delete Data")
            setMessage("Are you sure you want to delete this Data")
            setPositiveButton("Yes") { _, _ ->
                dataViewModel.deleteData(data,
                    onSuccess = {
                        Toast.makeText(this@MainActivity, "Data Deleted", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = {
                        Toast.makeText(this@MainActivity, "Failed to delete data", Toast.LENGTH_SHORT).show()
                    })
            }
            setNegativeButton("No", null)
        }.show()

    }

}

