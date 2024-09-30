package com.example.naeemnoman.firebasefirestore

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.naeemnoman.firebasefirestore.adapter.DataAdapter
import com.example.naeemnoman.firebasefirestore.databinding.ActivityMainBinding
import com.example.naeemnoman.firebasefirestore.model.Data
import com.example.naeemnoman.firebasefirestore.viewmodel.DataViewModel
import com.google.firebase.Timestamp

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val dataViewModel:DataViewModel by viewModels()
    private lateinit var adapter:DataAdapter
    private fun clearInputFiels() {
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

        dataViewModel.dataList.observe(this, Observer{
            adapter.updateData(it)

        })

        binding.saveBtn.setOnClickListener{
            val stuid = binding.idEtxt.text.toString()
            val name = binding.nameEtxt.text.toString()
            val email = binding.emailEtxt.text.toString()
            val subject = binding.subjectEtxt.text.toString()
            val birthdate = binding.birthDateEtxt.text.toString()


            if (stuid.isNotEmpty() && name.isNotEmpty() && email.isNotEmpty() && subject.isNotEmpty() && birthdate.isNotEmpty()){


                val data = com.example.naeemnoman.firebasefirestore.model.Data(null, stuid, name, email, subject, birthdate, Timestamp.now())
                dataViewModel.addData(data, onSuccess = {
                    clearInputFiels()
                    Toast.makeText(this@MainActivity, "Data Added Successfully", Toast.LENGTH_SHORT)
                }, onFailure = {
                    Toast.makeText(this@MainActivity, "failed to add data", Toast.LENGTH_SHORT)
                }
                )}

            override fun onEditItemClick(data: Data){

                binding.idEtxt.setText(data.stuid)
                binding.nameEtxt.setText(data.name)
                binding.emailEtxt.setText(data.email)
                binding.subjectEtxt.setText(data.subject)
                binding.birthDateEtxt.setText(data.birhtdate)
                binding.saveBtn.setOnClickListener {
                    val updateData = Data(data.id,binding.idEtxt.text.toString(),binding.nameEtxt.text.toString(),
                        binding.emailEtxt.text.toString(),binding.subjectEtxt.text.toString(),binding.birthDateEtxt.text.toString(),


                        dataViewModel.updateData(updateData)
                        clearInput
                }




            }

            }
        }



    }
