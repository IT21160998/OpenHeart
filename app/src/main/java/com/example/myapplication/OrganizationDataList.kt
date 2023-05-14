package com.example.myapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.models.OrganizationModel
import com.example.myapplication.R
import com.example.myapplication.fetching
import com.google.firebase.database.FirebaseDatabase

class OrganizationDataList : AppCompatActivity() {

    private lateinit var tvorganization1Id: TextView
    private lateinit var tvorganizationName: TextView
    private lateinit var tvorganizationId: TextView
    private lateinit var tvorganizationEmail: TextView
    private lateinit var updatebtn: Button
    private lateinit var deletebtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organization_data_list)

        initView()
        setValuesToViews()
        //update and delete button
        updatebtn.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("organization1Id").toString(),
                intent.getStringExtra("tvorganizationName").toString()
            )
        }
        deletebtn.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("organization1Id").toString()
            )
        }
    }
    //initView of bill data
    private fun initView() {
        tvorganization1Id = findViewById(R.id.tvtvorganization1Id)
        tvorganizationName=findViewById(R.id.tvOrganizationName)
        tvorganizationId=findViewById(R.id.tvOrganizationId)
        tvorganizationEmail = findViewById(R.id.tvOrganizationEmail)

        updatebtn = findViewById(R.id.updatebtn)
        deletebtn = findViewById(R.id.deletebtn)
    }

    //To view data
    private fun setValuesToViews(){

        tvorganization1Id.text = intent.getStringExtra("organization1Id")
        tvorganizationName.text = intent.getStringExtra("organizationName")
        tvorganizationId.text = intent.getStringExtra("organizationId")
        tvorganizationEmail.text = intent.getStringExtra("organizationEmail")


    }
    //delete record
    private fun deleteRecord(
        orggId : String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Organization").child(orggId)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "OrganizationBill details deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, fetching::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error->
            Toast.makeText(this,"Deleting error ${error.message}",Toast.LENGTH_LONG).show()
        }
    }
    //update record
    private fun openUpdateDialog(
        orggId : String,
        orgName:String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_organization,null)

        mDialog.setView(mDialogView)
        val etorganizationName = mDialogView.findViewById<EditText>(R.id.etOrganizationName)
        val etorganizationId = mDialogView.findViewById<EditText>(R.id.etOrganizationId)
        val etorganizationEmail = mDialogView.findViewById<EditText>(R.id.etOrganizationEmail)
        val updateBtn = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etorganizationName.setText(intent.getStringExtra("organizationName").toString())
        etorganizationId.setText(intent.getStringExtra("organizationId").toString())
        etorganizationEmail.setText(intent.getStringExtra("organizationEmail").toString())

        mDialog.setTitle("Updating $orgName Record")

        val alertDialog=mDialog.create()
        alertDialog.show()

        updateBtn.setOnClickListener{
            updateorganizationData(
                orggId ,
                etorganizationName.text.toString(),
                etorganizationId.text.toString(),
                etorganizationEmail.text.toString()
            )

            Toast.makeText(applicationContext,"Bill Data Updated", Toast.LENGTH_LONG)

            //setting updated data
            tvorganizationName.text =  etorganizationName.text.toString()
            tvorganizationId.text = etorganizationId.text.toString()
            tvorganizationEmail.text = etorganizationEmail.text.toString()

            alertDialog.dismiss()
        }
    }
    //update bill data
    private fun updateorganizationData(
        id: String,
        Name: String,
        Id:String,
        Email:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Organization").child(id)
        val billInfo = OrganizationModel(id,Name,Id,Email)
        dbRef.setValue(billInfo)
    }
}